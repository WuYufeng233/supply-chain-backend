package cn.edu.scut.sse.supply.bank.service;

import cn.edu.scut.sse.supply.bank.dao.BankApplicationDAO;
import cn.edu.scut.sse.supply.bank.dao.BankContractDAO;
import cn.edu.scut.sse.supply.bank.dao.BankTokenDAO;
import cn.edu.scut.sse.supply.bank.dao.BankUserDAO;
import cn.edu.scut.sse.supply.bank.entity.pojo.BankApplication;
import cn.edu.scut.sse.supply.bank.entity.pojo.BankContract;
import cn.edu.scut.sse.supply.bank.entity.pojo.BankUser;
import cn.edu.scut.sse.supply.bank.entity.vo.DetailBankApplicationVO;
import cn.edu.scut.sse.supply.bank.entity.vo.EnterpriseCreditVO;
import cn.edu.scut.sse.supply.bank.entity.vo.EnterpriseTokenVO;
import cn.edu.scut.sse.supply.general.dao.EnterpriseDAO;
import cn.edu.scut.sse.supply.general.dao.KeystoreDAO;
import cn.edu.scut.sse.supply.general.entity.pojo.Enterprise;
import cn.edu.scut.sse.supply.general.entity.vo.*;
import cn.edu.scut.sse.supply.util.EnterpriseUtil;
import cn.edu.scut.sse.supply.util.HashUtil;
import cn.edu.scut.sse.supply.util.SignVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author Yukino Yukinoshita
 */

@Service
public class BankService {

    private static final int ENTERPRISE_CODE = 1001;
    private static final String PRIVATE_KEY_PATH = "../webapps/api/WEB-INF/classes/private_key_" + ENTERPRISE_CODE;
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
        user.setToken(HashUtil.findToken(user));
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
        user.setToken(HashUtil.findToken(user));
        bankUserDAO.updateUser(user);
        result.setCode(0);
        result.setMsg("修改密码成功");
        result.setData(user);
        return result;
    }

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
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (privateKey == null || "".equals(privateKey)) {
            return new ResponseResult().setCode(-11).setMsg("内部状态错误，未取得密钥");
        }
        String signature = SignVerifyUtil.sign(privateKey, contract.getHash());
        try {
            return bankContractDAO.saveContractToFisco(contract, signature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult receiveContract(String token, int fid) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO detailContract;
        try {
            detailContract = bankContractDAO.getContractFromFisco(fid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (Integer.parseInt(detailContract.getSponsor()) != ENTERPRISE_CODE && Integer.parseInt(detailContract.getReceiver()) != ENTERPRISE_CODE) {
            return new ResponseResult().setCode(-9).setMsg("非法请求");
        }
        BankContract contract = bankContractDAO.getContract(detailContract.getContractId());
        if (contract == null) {
            contract = new BankContract();
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
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (privateKey == null || "".equals(privateKey)) {
            return new ResponseResult().setCode(-11).setMsg("内部状态错误，未取得密钥");
        }
        String signature = SignVerifyUtil.sign(privateKey, detailContract.getHash());

        try {
            return bankContractDAO.receiveContractToFisco(fid, ENTERPRISE_CODE, signature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult listContract(String token) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
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
                        vo.setStartDate("未知日期");
                    } else {
                        vo.setStartDate(format.format(bankContract.getStartDate()));
                    }
                    return vo;
                }).collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(contracts);
    }

    public ResponseResult getContract(String token, int fid) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO vo;
        try {
            vo = bankContractDAO.getContractFromFisco(fid);
        } catch (Exception e) {
            e.printStackTrace();
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
                e.printStackTrace();
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
                e.printStackTrace();
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

    public ResponseResult updateContract(String token, int fid, String status) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return bankContractDAO.updateContractStatusToFisco(ENTERPRISE_CODE, fid, status);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部错误");
        }
    }

    public ResponseResult setEnterpriseCredit(String token, int code, BigInteger credit) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return bankTokenDAO.setEnterpriseCredit(code, credit);
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

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
                        e.printStackTrace();
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(enterpriseCreditVoList);
    }

    public ResponseResult addEnterpriseToken(String token, int code, BigInteger val) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (val.compareTo(BigInteger.valueOf(0)) < 0) {
            return new ResponseResult().setCode(-5).setMsg("金额不可为负值");
        }
        try {
            return bankTokenDAO.addEnterpriseToken(code, val);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult subEnterpriseToken(String token, int code, BigInteger val) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (val.compareTo(BigInteger.valueOf(0)) < 0) {
            return new ResponseResult().setCode(-5).setMsg("金额不可为负值");
        }
        try {
            return bankTokenDAO.subEnterpriseToken(code, val);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
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
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

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
                        e.printStackTrace();
                    }
                    return vo;
                })
                .collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(enterpriseTokenVoList);
    }

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
            e.printStackTrace();
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

    public ResponseResult getSignatureOfText(String token, String text) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        String privateKey;
        try {
            privateKey = keystoreDAO.getPrivateKeyFromStorage(PRIVATE_KEY_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (privateKey == null || "".equals(privateKey)) {
            return new ResponseResult().setCode(-11).setMsg("内部状态错误，未取得密钥");
        }
        String signature = SignVerifyUtil.sign(privateKey, text);
        return new ResponseResult().setCode(0).setMsg("签名成功").setData(signature);
    }

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
            return bankApplicationDAO.saveBankApplicationToFisco(fid, content, code, ENTERPRISE_CODE, signature, type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
    }

    public ResponseResult receiveBankApplication(String token, int fid) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        BankApplication application = bankApplicationDAO.getBankApplication(fid);
        String privateKey;
        try {
            privateKey = keystoreDAO.getPrivateKeyFromStorage(PRIVATE_KEY_PATH);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误，未获得密钥");
        }
        String content = application.getContent().concat(String.valueOf(application.getType()));
        String signature = SignVerifyUtil.sign(privateKey, content);
        try {
            return bankApplicationDAO.receiveBankApplicationToFisco(fid, signature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
    }

    public ResponseResult updateBankApplicationStatus(String token, int fid, String status) {
        if (bankUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return bankApplicationDAO.updateBankApplicationStatusToFisco(fid, status);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
    }

    public ResponseResult getBankApplicationDetail(int fid) {
        DetailBankApplicationVO vo;
        try {
            vo = bankApplicationDAO.getBankApplicationFromFisco(fid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("服务器内部错误");
        }
        String content = vo.getContent().concat(String.valueOf(vo.getApplicationType()));
        String sponsorPublicKey = keystoreDAO.getKeystore(vo.getSponsor()).getPublicKey();
        String receiverPublicKey = keystoreDAO.getKeystore(vo.getReceiver()).getPublicKey();
        try {
            boolean sponsorVerify = SignVerifyUtil.verify(sponsorPublicKey, content, vo.getSponsorSignature());
            if (sponsorVerify) {
                vo.setSponsorVerify(1);
            } else {
                vo.setSponsorVerify(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            vo.setSponsorVerify(0);
        }
        try {
            boolean receiverVerify = SignVerifyUtil.verify(receiverPublicKey, content, vo.getReceiverSignature());
            if (receiverVerify) {
                vo.setReceiverVerify(1);
            } else {
                vo.setReceiverVerify(-1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            vo.setReceiverVerify(0);
        }
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(vo);
    }

    public ResponseResult listApplication(int code) {
        if (!checkLegalEnterpriseType(code)) {
            return new ResponseResult().setCode(-4).setMsg("不合法的企业代码");
        }
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(bankApplicationDAO.listBankApplication(code));
    }

    private boolean checkLegalEnterpriseType(int type) {
        List<Integer> codeList = enterpriseDAO.listEnterprise().stream()
                .map(Enterprise::getCode).collect(Collectors.toList());
        return codeList.contains(type);
    }

}
