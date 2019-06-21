package cn.edu.scut.sse.supply.insurance.service;

import cn.edu.scut.sse.supply.general.dao.EnterpriseDAO;
import cn.edu.scut.sse.supply.general.dao.KeystoreDAO;
import cn.edu.scut.sse.supply.general.entity.pojo.Enterprise;
import cn.edu.scut.sse.supply.general.entity.vo.*;
import cn.edu.scut.sse.supply.insurance.dao.InsuranceContractDAO;
import cn.edu.scut.sse.supply.insurance.dao.InsuranceTokenDAO;
import cn.edu.scut.sse.supply.insurance.dao.InsuranceUserDAO;
import cn.edu.scut.sse.supply.insurance.entity.pojo.InsuranceContract;
import cn.edu.scut.sse.supply.insurance.entity.pojo.InsuranceUser;
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
public class InsuranceService {

    private static final int ENTERPRISE_CODE = 3001;
    private static final String PRIVATE_KEY_PATH = "../webapps/api/WEB-INF/classes/private_key_" + ENTERPRISE_CODE;
    private InsuranceUserDAO insuranceUserDAO;
    private InsuranceTokenDAO insuranceTokenDAO;
    private InsuranceContractDAO insuranceContractDAO;
    private EnterpriseDAO enterpriseDAO;
    private KeystoreDAO keystoreDAO;

    @Autowired
    public InsuranceService(InsuranceUserDAO insuranceUserDAO,
                            InsuranceTokenDAO insuranceTokenDAO,
                            InsuranceContractDAO insuranceContractDAO,
                            EnterpriseDAO enterpriseDAO,
                            KeystoreDAO keystoreDAO) {
        this.insuranceUserDAO = insuranceUserDAO;
        this.insuranceTokenDAO = insuranceTokenDAO;
        this.insuranceContractDAO = insuranceContractDAO;
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
        InsuranceUser user = insuranceUserDAO.getUserByName(username);
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
        if (insuranceUserDAO.getUserByName(username) != null) {
            result.setCode(-2);
            result.setMsg("用户已存在");
            return result;
        }
        InsuranceUser user = new InsuranceUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setToken(HashUtil.findToken(user));
        insuranceUserDAO.saveUser(user);

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
        InsuranceUser user = insuranceUserDAO.getUserByToken(token);
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
        insuranceUserDAO.updateUser(user);
        result.setCode(0);
        result.setMsg("修改密码成功");
        result.setData(user);
        return result;
    }

    public ResponseResult contractUpload(String token, byte[] bytes) {
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        String hash = HashUtil.keccak256(bytes);
        List<InsuranceContract> recycleContracts = insuranceContractDAO.listRecycleContract();
        InsuranceContract contract;
        if (recycleContracts == null || recycleContracts.size() == 0) {
            contract = new InsuranceContract();
            contract.setHash(hash);
            contract.setSponsor(ENTERPRISE_CODE);
            contract.setReceiver(0);
            insuranceContractDAO.saveContract(contract);
        } else {
            contract = recycleContracts.get(0);
            contract.setHash(hash);
            insuranceContractDAO.updateContract(contract);
        }

        ContractUploadResultVO vo = new ContractUploadResultVO();
        vo.setFid(contract.getFid()).setHash(hash);
        return new ResponseResult().setCode(0).setMsg("获取hash成功").setData(vo);
    }

    public ResponseResult contractLaunch(String token, int fid, String hash, int receiver) {
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (hash == null) {
            return new ResponseResult().setCode(-1).setMsg("hash为空");
        }
        InsuranceContract contract = insuranceContractDAO.getContract(fid);
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
        insuranceContractDAO.updateContract(contract);

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
            return insuranceContractDAO.saveContractToFisco(contract, signature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult receiveContract(String token, int fid) {
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO detailContract;
        try {
            detailContract = insuranceContractDAO.getContractFromFisco(fid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (Integer.parseInt(detailContract.getSponsor()) != ENTERPRISE_CODE && Integer.parseInt(detailContract.getReceiver()) != ENTERPRISE_CODE) {
            return new ResponseResult().setCode(-9).setMsg("非法请求");
        }
        InsuranceContract contract = insuranceContractDAO.getContract(detailContract.getContractId());
        if (contract == null) {
            contract = new InsuranceContract();
            contract.setHash(detailContract.getHash());
            contract.setSponsor(Integer.parseInt(detailContract.getSponsor()));
            contract.setReceiver(Integer.parseInt(detailContract.getReceiver()));
            contract.setStartDate(Timestamp.valueOf(detailContract.getStartDate()));
            insuranceContractDAO.saveContract(contract);
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
        String signature = SignVerifyUtil.sign(privateKey, detailContract.getHash());

        try {
            return insuranceContractDAO.receiveContractToFisco(fid, ENTERPRISE_CODE, signature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult listContract(String token) {
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        List<ContractVO> contracts = insuranceContractDAO.listEnableContract().stream()
                .map(insuranceContract -> {
                    ContractVO vo = new ContractVO();
                    vo.setFid(insuranceContract.getFid());
                    vo.setHash(insuranceContract.getHash());
                    String sponsor = EnterpriseUtil.getEnterpriseNameByCode(insuranceContract.getSponsor());
                    if (sponsor == null) {
                        sponsor = enterpriseDAO.getEnterpriseByCode(insuranceContract.getSponsor()).getName();
                        EnterpriseUtil.putCodeName(insuranceContract.getSponsor(), sponsor);
                    }
                    vo.setSponsor(sponsor);
                    String receiver = EnterpriseUtil.getEnterpriseNameByCode(insuranceContract.getReceiver());
                    if (receiver == null) {
                        receiver = enterpriseDAO.getEnterpriseByCode(insuranceContract.getReceiver()).getName();
                        EnterpriseUtil.putCodeName(insuranceContract.getReceiver(), receiver);
                    }
                    vo.setReceiver(receiver);
                    if (insuranceContract.getStartDate() == null) {
                        vo.setStartDate("未知日期");
                    } else {
                        vo.setStartDate(format.format(insuranceContract.getStartDate()));
                    }
                    return vo;
                }).collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(contracts);
    }

    public ResponseResult getContract(String token, int fid) {
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO vo;
        try {
            vo = insuranceContractDAO.getContractFromFisco(fid);
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
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return insuranceContractDAO.updateContractStatusToFisco(ENTERPRISE_CODE, fid, status);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部错误");
        }
    }

    public ResponseResult getEnterpriseCredit(String token) {
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return insuranceTokenDAO.getEnterpriseCredit(ENTERPRISE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult getEnterpriseToken(String token) {
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return insuranceTokenDAO.getEnterpriseToken(ENTERPRISE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult payEnterpriseToken(String token, int code, BigInteger val, Integer type, Integer id) {
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        ResponseResult result;
        try {
            result = insuranceTokenDAO.payEnterpriseToken(ENTERPRISE_CODE, code, val);
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
        if (insuranceUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<TransactionRecordVO> list = enterpriseDAO.listTransactionRecord(ENTERPRISE_CODE);
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(list);
    }

    public ResponseResult getSignatureOfText(String token, String text) {
        if (insuranceUserDAO.getUserByToken(token) == null) {
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

    private boolean checkLegalEnterpriseType(int type) {
        List<Integer> codeList = enterpriseDAO.listEnterprise().stream()
                .map(Enterprise::getCode).collect(Collectors.toList());
        return codeList.contains(type);
    }

}
