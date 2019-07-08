package cn.edu.scut.sse.supply.bank.service;

import cn.edu.scut.sse.supply.bank.dao.BankApplicationDAO;
import cn.edu.scut.sse.supply.bank.dao.BankContractDAO;
import cn.edu.scut.sse.supply.bank.dao.BankTokenDAO;
import cn.edu.scut.sse.supply.bank.dao.BankUserDAO;
import cn.edu.scut.sse.supply.bank.entity.pojo.BankApplication;
import cn.edu.scut.sse.supply.bank.entity.pojo.BankContract;
import cn.edu.scut.sse.supply.bank.entity.pojo.BankUser;
import cn.edu.scut.sse.supply.bank.entity.vo.*;
import cn.edu.scut.sse.supply.general.dao.EnterpriseDAO;
import cn.edu.scut.sse.supply.general.dao.KeystoreDAO;
import cn.edu.scut.sse.supply.general.entity.pojo.Enterprise;
import cn.edu.scut.sse.supply.general.entity.vo.*;
import cn.edu.scut.sse.supply.util.EnterpriseUtil;
import cn.edu.scut.sse.supply.util.HashUtil;
import cn.edu.scut.sse.supply.util.SignVerifyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 银行Service类进行业务处理
 * <p>
 * Service类向下调用DAO层对本地数据库和区块链数据进行读写，对数据进行业务处理
 * 后向上返回。不同业务的具体操作见对应方法。
 *
 * @author Yukino Yukinoshita
 */

@Service
public class BankService {

    private static final Logger logger = LoggerFactory.getLogger(BankService.class);
    
    private static final int ENTERPRISE_CODE = 1001;
    private static final String PRIVATE_KEY_PATH = "../webapps/bank/WEB-INF/classes/private_key_" + ENTERPRISE_CODE;
    private BankUserDAO bankUserDAO;
    private BankApplicationDAO bankApplicationDAO;
    private BankTokenDAO bankTokenDAO;
    private BankContractDAO bankContractDAO;
    private EnterpriseDAO enterpriseDAO;
    private KeystoreDAO keystoreDAO;

    @Autowired
    public BankService(BankUserDAO bankUserDAO,
                       BankApplicationDAO bankApplicationDAO,
                       BankTokenDAO bankTokenDAO,
                       BankContractDAO bankContractDAO,
                       EnterpriseDAO enterpriseDAO,
                       KeystoreDAO keystoreDAO) {
        this.bankUserDAO = bankUserDAO;
        this.bankApplicationDAO = bankApplicationDAO;
        this.bankTokenDAO = bankTokenDAO;
        this.bankContractDAO = bankContractDAO;
        this.enterpriseDAO = enterpriseDAO;
        this.keystoreDAO = keystoreDAO;
    }

    public ResponseResult login(String username, String password) {
        ResponseResult result = new ResponseResult();
        if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
            result.setCode(-1);
            result.setMsg("用户名或密码为空");
            return result;
        }
        BankUser user = bankUserDAO.getUserByName(username);
        if (user == null) {
            result.setCode(-1);
            result.setMsg("用户不存在或密码错误");
            return result;
        }
        if (!user.getPassword().equals(password)) {
            result.setCode(-1);
            result.setMsg("密码错误");
            return result;
        }
        result.setCode(0);
        result.setMsg("登录成功");
        result.setData(user);
        return result;
    }

    public ResponseResult register(String username, String password) {
        ResponseResult result = new ResponseResult();
        if (username == null || "".equals(username.trim()) || password == null || "".equals(password.trim())) {
            result.setCode(-1);
            result.setMsg("用户名或密码为空");
            return result;
        }
        if (bankUserDAO.getUserByName(username) != null) {
            result.setCode(-2);
            result.setMsg("用户已存在");
            return result;
        }
        BankUser user = new BankUser();
        user.setUsername(username);
        user.setPassword(password);
        String newToken = HashUtil.findToken(user);
        while (bankUserDAO.getUserByToken(newToken) != null) {
            newToken = HashUtil.findToken(user);
        }
        user.setToken(newToken);
        bankUserDAO.saveUser(user);

        result.setCode(0);
        result.setMsg("注册成功");
        result.setData(user);
        return result;
    }

    public ResponseResult changePassword(String token, String oldPassword, String newPassword) {
        ResponseResult result = new ResponseResult();
        if (token == null || "".equals(token.trim())
                || oldPassword == null || "".equals(oldPassword.trim())
                || newPassword == null || "".equals(newPassword.trim())) {
            result.setCode(-1);
            result.setMsg("密码为空");
            return result;
        }
        BankUser user = bankUserDAO.getUserByToken(token);
        if (user == null) {
            result.setCode(-3);
            result.setMsg("用户不存在");
            return result;
        }
        if (!user.getPassword().equals(oldPassword)) {
            result.setCode(-1);
            result.setMsg("用户旧密码错误");
            return result;
        }
        user.setPassword(newPassword);
        String newToken = HashUtil.findToken(user);
        while (bankUserDAO.getUserByToken(newToken) != null) {
            newToken = HashUtil.findToken(user);
        }
        user.setToken(newToken);
        bankUserDAO.updateUser(user);
        result.setCode(0);
        result.setMsg("修改密码成功");
        result.setData(user);
        return result;
    }

    /**
     * 上传合同文本获得文本Hash
     * <p>
     * 1.检查用户凭证Token
     * 2.通过{@link HashUtil#keccak256(byte[])}计算出合同文本的keccak256 Hash
     * 3.对上传文本预先存库以获取合同ID，具体有2个步骤
     * 3.1.读取本地数据库，检查是否有已经预先存库但是没有发起的草稿合同
     * 3.2.若无草稿合同，存库，获取合同ID；若有草稿合同，直接修稿草稿合同
     * 4.返回合同ID和合同文本的Hash
     *
     * @param token 银行用户凭证Token
     * @param bytes 合同文本
     * @return 返回执行结果，正常返回时包括合同ID和合同文本Hash
     */
    public ResponseResult contractUpload(String token, byte[] bytes) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        String hash = HashUtil.keccak256(bytes);
        List<BankContract> recycleContracts = bankContractDAO.listRecycleContract();
        BankContract contract;
        if (recycleContracts == null || recycleContracts.size() == 0) {
            contract = new BankContract();
            contract.setHash(hash);
            contract.setSponsor(ENTERPRISE_CODE);
            contract.setReceiver(0);
            bankContractDAO.saveContract(contract);
        } else {
            contract = recycleContracts.get(0);
            contract.setHash(hash);
            bankContractDAO.updateContract(contract);
        }

        ContractUploadResultVO vo = new ContractUploadResultVO();
        vo.setFid(contract.getFid()).setHash(hash);
        return new ResponseResult().setCode(0).setMsg("获取hash成功").setData(vo);
    }

    /**
     * 发起合同
     * <p>
     * 1.检查用户凭证Token
     * 2.检查传入Hash是否为空
     * 3.根据传入的合同ID，读取本地数据库获得已经预先存库的合同
     * 4.检查对应合同ID的合同是否已经预先存库，若合同为空表明合同ID错误
     * 5.检查Hash与预先存库合同Hash是否一致
     * 6.检查合同ID对应的合同是否已经发起
     * 7.检查传入的接受方企业代码是否合法
     * 8.发起合同，在本地存库
     * 9.读取企业私钥，调用{@link SignVerifyUtil#sign(String, String)}对合同文本Hash进行签名
     * 10.合同上链
     *
     * @param token 银行用户凭证Token
     * @param fid 合同ID
     * @param hash 合同文本Hash
     * @param receiver 合同接受方企业代码
     * @return 返回执行结果
     */
    public ResponseResult contractLaunch(String token, int fid, String hash, int receiver) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (hash == null) {
            return new ResponseResult().setCode(-1).setMsg("hash为空");
        }
        BankContract contract = bankContractDAO.getContract(fid);
        if (contract == null) {
            return new ResponseResult().setCode(-9).setMsg("合同编号错误");
        }
        if (!hash.equals(contract.getHash())) {
            return new ResponseResult().setCode(-9).setMsg("hash非法");
        }
        if (contract.getReceiver() != 0) {
            return new ResponseResult().setCode(-9).setMsg("合同ID已存在，请重新上传合同");
        }
        if (!checkLegalEnterpriseType(receiver)) {
            return new ResponseResult().setCode(-4).setMsg("不合法的企业代码");
        }
        contract.setReceiver(receiver);
        contract.setStartDate(new Timestamp(System.currentTimeMillis()));
        bankContractDAO.updateContract(contract);

        String privateKey;
        try {
            privateKey = keystoreDAO.getPrivateKeyFromStorage(PRIVATE_KEY_PATH);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (privateKey == null || "".equals(privateKey)) {
            return new ResponseResult().setCode(-11).setMsg("内部状态错误，未取得密钥");
        }
        String signature = SignVerifyUtil.sign(privateKey, contract.getHash());
        try {
            return bankContractDAO.saveContractToFisco(contract, signature);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    /**
     * 接受合同
     * <p>
     * 1.检查用户凭证Token
     * 2.根据传入的合同ID，读取区块链上合同数据
     * 3.判断该合同是否与企业相关，企业既不是发起方也不是接受方时无法接受该合同
     * 4.根据合同ID读取本地数据库
     * 5.本地数据库没有该合同备份时，将该合同存库
     * 6.读取企业私钥，调用{@link SignVerifyUtil#sign(String, String)}对合同文本Hash进行签名
     * 7.接受合同，签名上链
     *
     * @param token 银行用户凭证Token
     * @param fid 合同ID
     * @return 返回执行结果
     */
    public ResponseResult receiveContract(String token, int fid) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO detailContract;
        try {
            detailContract = bankContractDAO.getContractFromFisco(fid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (Integer.parseInt(detailContract.getSponsor()) != ENTERPRISE_CODE && Integer.parseInt(detailContract.getReceiver()) != ENTERPRISE_CODE) {
            return new ResponseResult().setCode(-9).setMsg("非法请求");
        }
        BankContract contract = bankContractDAO.getContract(detailContract.getContractId());
        if (contract == null) {
            contract = new BankContract();
            contract.setFid(detailContract.getContractId());
            contract.setHash(detailContract.getHash());
            contract.setSponsor(Integer.parseInt(detailContract.getSponsor()));
            contract.setReceiver(Integer.parseInt(detailContract.getReceiver()));
            contract.setStartDate(Timestamp.valueOf(detailContract.getStartDate()));
            bankContractDAO.saveContract(contract);
        }
        // 以下是签名上链
        String privateKey;
        try {
            privateKey = keystoreDAO.getPrivateKeyFromStorage(PRIVATE_KEY_PATH);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (privateKey == null || "".equals(privateKey)) {
            return new ResponseResult().setCode(-11).setMsg("内部状态错误，未取得密钥");
        }
        String signature = SignVerifyUtil.sign(privateKey, detailContract.getHash());

        try {
            return bankContractDAO.receiveContractToFisco(fid, ENTERPRISE_CODE, signature);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult listContract(String token) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<ContractVO> contracts = bankContractDAO.listEnableContract().stream()
                .map(bankContract -> {
                    ContractVO vo = new ContractVO();
                    vo.setFid(bankContract.getFid());
                    vo.setHash(bankContract.getHash());
                    String sponsor = EnterpriseUtil.getEnterpriseNameByCode(bankContract.getSponsor());
                    if (sponsor == null) {
                        sponsor = enterpriseDAO.getEnterpriseByCode(bankContract.getSponsor()).getName();
                        EnterpriseUtil.putCodeName(bankContract.getSponsor(), sponsor);
                    }
                    vo.setSponsor(sponsor);
                    String receiver = EnterpriseUtil.getEnterpriseNameByCode(bankContract.getReceiver());
                    if (receiver == null) {
                        receiver = enterpriseDAO.getEnterpriseByCode(bankContract.getReceiver()).getName();
                        EnterpriseUtil.putCodeName(bankContract.getReceiver(), receiver);
                    }
                    vo.setReceiver(receiver);
                    if (bankContract.getStartDate() == null) {
                        vo.setStartDate(0L);
                    } else {
                        vo.setStartDate(bankContract.getStartDate().getTime());
                    }
                    return vo;
                }).collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(contracts);
    }

    /**
     * 获取合同详细信息
     * <p>
     * 1.检查用户凭证Token
     * 2.从区块链中根据合同ID读取合同数据
     * 3.对发起方和接受方的签名调用{@link SignVerifyUtil#verify(String, String, String)}进行验证
     * 4.将发起方和接受方的企业代码转换成企业名展示
     * 5.返回详细信息
     *
     * @param token 银行用户凭证Token
     * @param fid 合同ID
     * @return 合同详细信息
     */
    public ResponseResult getContract(String token, int fid) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO vo;
        try {
            vo = bankContractDAO.getContractFromFisco(fid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        int sponsorCode = Integer.parseInt(vo.getSponsor());
        int receiverCode = Integer.parseInt(vo.getReceiver());
        if (vo.getSponsorSignature() != null && !"".equals(vo.getSponsorSignature())) {
            try {
                boolean verify = SignVerifyUtil.verify(keystoreDAO.getKeystore(sponsorCode).getPublicKey(), vo.getHash(), vo.getSponsorSignature());
                if (verify) {
                    vo.setSponsorVerify(1);
                } else {
                    vo.setSponsorVerify(-1);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                vo.setSponsorVerify(0);
            }
        } else {
            vo.setSponsorVerify(0);
        }
        if (vo.getReceiverSignature() != null && !"".equals(vo.getReceiverSignature())) {
            try {
                boolean verify = SignVerifyUtil.verify(keystoreDAO.getKeystore(receiverCode).getPublicKey(), vo.getHash(), vo.getReceiverSignature());
                if (verify) {
                    vo.setReceiverVerify(1);
                } else {
                    vo.setReceiverVerify(-1);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                vo.setReceiverVerify(0);
            }
        } else {
            vo.setReceiverVerify(0);
        }
        String sponsorName = EnterpriseUtil.getEnterpriseNameByCode(sponsorCode);
        String receiverName = EnterpriseUtil.getEnterpriseNameByCode(receiverCode);
        if (sponsorName == null) {
            sponsorName = enterpriseDAO.getEnterpriseByCode(sponsorCode).getName();
            EnterpriseUtil.putCodeName(sponsorCode, sponsorName);
        }
        if (receiverName == null) {
            receiverName = enterpriseDAO.getEnterpriseByCode(receiverCode).getName();
            EnterpriseUtil.putCodeName(receiverCode, receiverName);
        }
        vo.setSponsor(sponsorName);
        vo.setReceiver(receiverName);
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(vo);
    }

    /**
     * 更新合同状态
     * <p>
     * 1.检查用户凭证Token
     * 2.调用DAO层对区块链合同状态更新（该更新需要合同对方重新签名确认）
     *
     * @param token 银行用户凭证Token
     * @param fid 合同ID
     * @param status 新的合同状态
     * @return 返回执行结果
     */
    public ResponseResult updateContract(String token, int fid, String status) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return bankContractDAO.updateContractStatusToFisco(ENTERPRISE_CODE, fid, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部错误");
        }
    }

    /**
     * 银行授信（设置企业信用额度）
     * <p>
     * 1.检查用户凭证Token
     * 2.调用DAO层设置区块链中企业信用额度
     *
     * @param token 银行用户凭证Token
     * @param code 企业代码
     * @param credit 信用额度
     * @return 返回执行结果
     */
    public ResponseResult setEnterpriseCredit(String token, int code, BigInteger credit) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return bankTokenDAO.setEnterpriseCredit(code, credit);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult getEnterpriseCredit(String token, int code) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            BigInteger val = bankTokenDAO.getEnterpriseCredit(code);
            if (val == null) {
                return new ResponseResult().setCode(-11).setMsg("内部状态错误");
            } else {
                return new ResponseResult().setCode(0).setMsg("查询成功").setData(val);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    /**
     * 列出企业信用额度
     * <p>
     * 对每一个企业调用DAO层获取企业信用额度接口获取信用额度后，以列表展示
     *
     * @param token 银行用户凭证Token
     * @return 返回企业信用额度列表
     */
    public ResponseResult listEnterpriseCredit(String token) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<EnterpriseCreditVO> enterpriseCreditVoList = enterpriseDAO.listEnterprise().stream()
                .map(enterprise -> {
                    EnterpriseCreditVO vo = new EnterpriseCreditVO();
                    vo.setCode(enterprise.getCode());
                    vo.setName(enterprise.getName());
                    try {
                        vo.setCredit(bankTokenDAO.getEnterpriseCredit(enterprise.getCode()));
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(enterpriseCreditVoList);
    }

    /**
     * 增加企业Token
     * <p>
     * 1.检查用户凭证Token
     * 2.检查Token负值
     * 3.调用DAO层增加企业Token
     * 4.对增加企业Token记录存本地数据库
     *
     * @param token 银行用户凭证Token
     * @param code 企业代码
     * @param val 增加Token值
     * @param type 可选，绑定交易类型
     * @param id 可选，绑定对应交易类型的ID
     * @return 返回执行结果
     */
    public ResponseResult addEnterpriseToken(String token, int code, BigInteger val, Integer type, Integer id) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (val.compareTo(BigInteger.valueOf(0)) < 0) {
            return new ResponseResult().setCode(-5).setMsg("金额不可为负值");
        }
        ResponseResult result;
        try {
            result = bankTokenDAO.addEnterpriseToken(code, val);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (result.getCode() != 0) {
            return result;
        }
        String transactionHash = (String) result.getData();
        if (type != null && id != null) {
            enterpriseDAO.saveTokenTransaction(transactionHash, ENTERPRISE_CODE, code, val, type, id);
        } else {
            enterpriseDAO.saveTokenTransaction(transactionHash, ENTERPRISE_CODE, code, val);
        }
        return result;
    }

    /**
     * 减少企业Token
     *
     * @param token 银行用户凭证Token
     * @param code 企业代码
     * @param val 减少Token值
     * @param type 可选，绑定交易类型
     * @param id 可选，绑定对应交易类型的ID
     * @return 返回执行结果
     * @see BankService#addEnterpriseToken(String, int, BigInteger, Integer, Integer)
     */
    public ResponseResult subEnterpriseToken(String token, int code, BigInteger val, Integer type, Integer id) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (val.compareTo(BigInteger.valueOf(0)) < 0) {
            return new ResponseResult().setCode(-5).setMsg("金额不可为负值");
        }
        ResponseResult result;
        try {
            result = bankTokenDAO.subEnterpriseToken(code, val);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (result.getCode() != 0) {
            return result;
        }
        String transactionHash = (String) result.getData();
        if (type != null && id != null) {
            enterpriseDAO.saveTokenTransaction(transactionHash, code, ENTERPRISE_CODE, val, type, id);
        } else {
            enterpriseDAO.saveTokenTransaction(transactionHash, code, ENTERPRISE_CODE, val);
        }
        return result;
    }

    public ResponseResult getEnterpriseToken(String token, int code) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            BigInteger val = bankTokenDAO.getEnterpriseToken(code);
            if (val == null) {
                return new ResponseResult().setCode(-11).setMsg("内部状态错误");
            } else {
                return new ResponseResult().setCode(0).setMsg("查询成功").setData(val);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    /**
     * 列出企业持有的Token
     *
     * @param token 银行用户凭证Token
     * @return 返回企业持有Token列表
     * @see BankService#listEnterpriseCredit(String)
     */
    public ResponseResult listEnterpriseToken(String token) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<EnterpriseTokenVO> enterpriseTokenVoList = enterpriseDAO.listEnterprise().stream()
                .map(enterprise -> {
                    EnterpriseTokenVO vo = new EnterpriseTokenVO();
                    vo.setCode(enterprise.getCode());
                    vo.setName(enterprise.getName());
                    try {
                        vo.setToken(bankTokenDAO.getEnterpriseToken(enterprise.getCode()));
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(enterpriseTokenVoList);
    }

    /**
     * 支付Token
     * <p>
     * 1.检查用户凭证Token
     * 2.检查Token负值
     * 3.调用DAO层支付Token
     * 4.对支付交易存本地数据库
     * 5.返回执行结果
     *
     * @param token 银行用户凭证Token
     * @param code 企业代码
     * @param val 支付的Token值
     * @param type 可选，绑定交易类型
     * @param id 可选，绑定对应交易类型的ID
     * @return 返回执行结果
     */
    public ResponseResult payEnterpriseToken(String token, int code, BigInteger val, Integer type, Integer id) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (val.compareTo(BigInteger.valueOf(0)) < 0) {
            return new ResponseResult().setCode(-5).setMsg("金额不可为负值");
        }
        ResponseResult result;
        try {
            result = bankTokenDAO.payEnterpriseToken(ENTERPRISE_CODE, code, val);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (result.getCode() != 0) {
            return result;
        }
        String transactionHash = (String) result.getData();
        if (type != null && id != null) {
            enterpriseDAO.saveTokenTransaction(transactionHash, ENTERPRISE_CODE, code, val, type, id);
        } else {
            enterpriseDAO.saveTokenTransaction(transactionHash, ENTERPRISE_CODE, code, val);
        }
        return new ResponseResult().setCode(0).setMsg("支付成功").setData(transactionHash);
    }

    public ResponseResult listTokenTransaction(String token) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<TransactionRecordVO> list = enterpriseDAO.listTransactionRecord(ENTERPRISE_CODE);
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(list);
    }

    /**
     * 对文本Hash签名
     * <p>
     * 1.检查用户凭证Token
     * 2.读取私钥
     * 3.调用{@link SignVerifyUtil#sign(String, String)}进行签名
     * 4.返回签名结果
     *
     * @param token 银行用户凭证Token
     * @param text 待签名文本
     * @return 返回文本的签名
     */
    public ResponseResult getSignatureOfText(String token, String text) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        String privateKey;
        try {
            privateKey = keystoreDAO.getPrivateKeyFromStorage(PRIVATE_KEY_PATH);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (privateKey == null || "".equals(privateKey)) {
            return new ResponseResult().setCode(-11).setMsg("内部状态错误，未取得密钥");
        }
        String signature = SignVerifyUtil.sign(privateKey, HashUtil.keccak256(text.getBytes()));
        return new ResponseResult().setCode(0).setMsg("签名成功").setData(signature);
    }

    /**
     * 向银行发起申请（兑付、赎回、贷款）
     * <p>
     * 1.检查传入的企业代码是否合法
     * 2.将申请存银行本地数据库，并获取申请号
     * 3.将申请存区块链
     * 4.返回执行结果和申请号
     *
     * @param content 申请的内容概要
     * @param type 申请类型， 2 - 兑付， 3 - 赎回， 4 - 贷款
     * @param code 企业代码
     * @param signature 企业对申请的内容概要和申请类型的签名
     * @return 返回执行结果和申请号
     */
    public ResponseResult createBankApplication(String content, int type, int code, String signature) {
        if (!checkLegalEnterpriseType(code)) {
            return new ResponseResult().setCode(-4).setMsg("不合法的企业代码");
        }
        BankApplication application = new BankApplication();
        application.setContent(content);
        application.setSponsor(code);
        application.setReceiver(ENTERPRISE_CODE);
        application.setType(type);
        application.setStartDate(new Timestamp(System.currentTimeMillis()));
        int fid = bankApplicationDAO.saveBankApplication(application);
        try {
            return bankApplicationDAO.saveBankApplicationToFisco(fid, content, code, ENTERPRISE_CODE, signature, type).setData(fid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
    }

    /**
     * 银行接受申请
     * <p>
     * 1.检查用户凭证Token
     * 2.从银行本地数据库读取申请
     * 3.读取私钥
     * 4.对申请内容和申请类型进行签名
     * 5.将签名传入区块链，接受申请
     * 6.返回执行结果
     *
     * @param token 银行用户凭证Token
     * @param fid 申请号
     * @return 返回执行结果
     */
    public ResponseResult receiveBankApplication(String token, int fid) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        BankApplication application = bankApplicationDAO.getBankApplication(fid);
        String privateKey;
        try {
            privateKey = keystoreDAO.getPrivateKeyFromStorage(PRIVATE_KEY_PATH);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误，未获得密钥");
        }
        String content = application.getContent().concat(String.valueOf(application.getType()));
        String signature = SignVerifyUtil.sign(privateKey, HashUtil.keccak256(content.getBytes()));
        try {
            return bankApplicationDAO.receiveBankApplicationToFisco(fid, signature);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
    }

    /**
     * 修改申请状态
     *
     * @param token 银行用户凭证Token
     * @param fid 申请号
     * @param status 新的状态
     * @return 返回执行结果
     * @see BankService#updateContract(String, int, String)
     * 与修改合同状态不同，银行修改申请状态不需要双方重新签名确认
     */
    public ResponseResult updateBankApplicationStatus(String token, int fid, String status) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return bankApplicationDAO.updateBankApplicationStatusToFisco(fid, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
    }

    public ResponseResult getBankApplicationDetail(int fid) {
        DetailBankApplicationVO vo;
        try {
            vo = bankApplicationDAO.getBankApplicationFromFisco(fid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
        String content = vo.getContent().concat(String.valueOf(vo.getApplicationType()));
        String sponsorPublicKey = keystoreDAO.getKeystore(vo.getSponsor()).getPublicKey();
        String receiverPublicKey = keystoreDAO.getKeystore(vo.getReceiver()).getPublicKey();
        if (vo.getSponsorSignature() == null || "".equals(vo.getSponsorSignature())) {
            vo.setSponsorVerify(0);
        } else {
            try {
                boolean sponsorVerify = SignVerifyUtil.verify(sponsorPublicKey, HashUtil.keccak256(content.getBytes()), vo.getSponsorSignature());
                if (sponsorVerify) {
                    vo.setSponsorVerify(1);
                } else {
                    vo.setSponsorVerify(-1);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                vo.setSponsorVerify(0);
            }
        }
        if (vo.getReceiverSignature() == null || "".equals(vo.getReceiverSignature())) {
            vo.setReceiverVerify(0);
        } else {
            try {
                boolean receiverVerify = SignVerifyUtil.verify(receiverPublicKey, HashUtil.keccak256(content.getBytes()), vo.getReceiverSignature());
                if (receiverVerify) {
                    vo.setReceiverVerify(1);
                } else {
                    vo.setReceiverVerify(-1);
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                vo.setReceiverVerify(0);
            }
        }
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(vo);
    }

    /**
     * 列出申请，对外开放的接口
     *
     * @param code 企业代码
     * @return 返回企业列表
     */
    public ResponseResult listApplication(int code) {
        if (!checkLegalEnterpriseType(code)) {
            return new ResponseResult().setCode(-4).setMsg("不合法的企业代码");
        }
        List<BankApplicationVO> vos = bankApplicationDAO.listBankApplication(code).stream()
                .parallel()
                .map(bankApplication -> {
                    BankApplicationVO bankApplicationVO = BankApplicationVO.from(bankApplication);
                    BankApplicationStatusVO statusVO;
                    try {
                        statusVO = bankApplicationDAO.getBankApplicationStatus(bankApplicationVO.getFid());
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        bankApplicationVO.setStatus("未知状态");
                        return bankApplicationVO;
                    }
                    if (statusVO.getReceiverSignature() == null || "".equals(statusVO.getReceiverSignature())) {
                        bankApplicationVO.setStatus("未接收");
                    } else {
                        bankApplicationVO.setStatus(statusVO.getStatus());
                    }
                    return bankApplicationVO;
                })
                .collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(vos);
    }

    /**
     * 列出所有申请，银行接口
     *
     * @param token 银行用户凭证Token
     * @return 返回所有申请的列表
     */
    public ResponseResult listApplication(String token) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<BankApplicationVO> vos = bankApplicationDAO.listBankApplication().stream()
                .parallel()
                .map(bankApplication -> {
                    BankApplicationVO bankApplicationVO = BankApplicationVO.from(bankApplication);
                    BankApplicationStatusVO statusVO;
                    try {
                        statusVO = bankApplicationDAO.getBankApplicationStatus(bankApplicationVO.getFid());
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        bankApplicationVO.setStatus("未知状态");
                        return bankApplicationVO;
                    }
                    if (statusVO.getReceiverSignature() == null || "".equals(statusVO.getReceiverSignature())) {
                        bankApplicationVO.setStatus("未接收");
                    } else {
                        bankApplicationVO.setStatus(statusVO.getStatus());
                    }
                    return bankApplicationVO;
                })
                .collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(vos);
    }

    private boolean checkLegalEnterpriseType(int type) {
        List<Integer> codeList = enterpriseDAO.listEnterprise().stream()
                .map(Enterprise::getCode).collect(Collectors.toList());
        return codeList.contains(type);
    }

}
