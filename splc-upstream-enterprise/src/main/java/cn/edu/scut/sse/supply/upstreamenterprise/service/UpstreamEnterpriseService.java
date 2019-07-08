package cn.edu.scut.sse.supply.upstreamenterprise.service;

import cn.edu.scut.sse.supply.general.dao.EnterpriseDAO;
import cn.edu.scut.sse.supply.general.dao.KeystoreDAO;
import cn.edu.scut.sse.supply.general.entity.pojo.Enterprise;
import cn.edu.scut.sse.supply.general.entity.vo.*;
import cn.edu.scut.sse.supply.upstreamenterprise.dao.UpstreamEnterpriseCargoDAO;
import cn.edu.scut.sse.supply.upstreamenterprise.dao.UpstreamEnterpriseContractDAO;
import cn.edu.scut.sse.supply.upstreamenterprise.dao.UpstreamEnterpriseTokenDAO;
import cn.edu.scut.sse.supply.upstreamenterprise.dao.UpstreamEnterpriseUserDAO;
import cn.edu.scut.sse.supply.upstreamenterprise.entity.pojo.UpstreamEnterpriseCargoReceive;
import cn.edu.scut.sse.supply.upstreamenterprise.entity.pojo.UpstreamEnterpriseContract;
import cn.edu.scut.sse.supply.upstreamenterprise.entity.pojo.UpstreamEnterpriseUser;
import cn.edu.scut.sse.supply.upstreamenterprise.entity.vo.CargoResponseVO;
import cn.edu.scut.sse.supply.upstreamenterprise.entity.vo.DetailCargoVO;
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
 * @author Yukino Yukinoshita
 */

@Service
public class UpstreamEnterpriseService {

    private static final Logger logger = LoggerFactory.getLogger(UpstreamEnterpriseService.class);
    
    private static final int ENTERPRISE_CODE = 4002;
    private static final String PRIVATE_KEY_PATH = "../webapps/upstream-enterprise/WEB-INF/classes/private_key_" + ENTERPRISE_CODE;
    private UpstreamEnterpriseUserDAO upstreamEnterpriseUserDAO;
    private UpstreamEnterpriseCargoDAO upstreamEnterpriseCargoDAO;
    private UpstreamEnterpriseTokenDAO upstreamEnterpriseTokenDAO;
    private UpstreamEnterpriseContractDAO upstreamEnterpriseContractDAO;
    private EnterpriseDAO enterpriseDAO;
    private KeystoreDAO keystoreDAO;

    @Autowired
    public UpstreamEnterpriseService(UpstreamEnterpriseUserDAO upstreamEnterpriseUserDAO,
                                     UpstreamEnterpriseCargoDAO upstreamEnterpriseCargoDAO,
                                     UpstreamEnterpriseTokenDAO upstreamEnterpriseTokenDAO,
                                     UpstreamEnterpriseContractDAO upstreamEnterpriseContractDAO,
                                     EnterpriseDAO enterpriseDAO,
                                     KeystoreDAO keystoreDAO) {
        this.upstreamEnterpriseUserDAO = upstreamEnterpriseUserDAO;
        this.upstreamEnterpriseCargoDAO = upstreamEnterpriseCargoDAO;
        this.upstreamEnterpriseTokenDAO = upstreamEnterpriseTokenDAO;
        this.upstreamEnterpriseContractDAO = upstreamEnterpriseContractDAO;
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
        UpstreamEnterpriseUser user = upstreamEnterpriseUserDAO.getUserByName(username);
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
        if (upstreamEnterpriseUserDAO.getUserByName(username) != null) {
            result.setCode(-2);
            result.setMsg("用户已存在");
            return result;
        }
        UpstreamEnterpriseUser user = new UpstreamEnterpriseUser();
        user.setUsername(username);
        user.setPassword(password);
        String newToken = HashUtil.findToken(user);
        while (upstreamEnterpriseUserDAO.getUserByToken(newToken) != null) {
            newToken = HashUtil.findToken(user);
        }
        user.setToken(newToken);
        upstreamEnterpriseUserDAO.saveUser(user);

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
        UpstreamEnterpriseUser user = upstreamEnterpriseUserDAO.getUserByToken(token);
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
        while (upstreamEnterpriseUserDAO.getUserByToken(newToken) != null) {
            newToken = HashUtil.findToken(user);
        }
        user.setToken(newToken);
        upstreamEnterpriseUserDAO.updateUser(user);
        result.setCode(0);
        result.setMsg("修改密码成功");
        result.setData(user);
        return result;
    }

    public ResponseResult contractUpload(String token, byte[] bytes) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        String hash = HashUtil.keccak256(bytes);
        List<UpstreamEnterpriseContract> recycleContracts = upstreamEnterpriseContractDAO.listRecycleContract();
        UpstreamEnterpriseContract contract;
        if (recycleContracts == null || recycleContracts.size() == 0) {
            contract = new UpstreamEnterpriseContract();
            contract.setHash(hash);
            contract.setSponsor(ENTERPRISE_CODE);
            contract.setReceiver(0);
            upstreamEnterpriseContractDAO.saveContract(contract);
        } else {
            contract = recycleContracts.get(0);
            contract.setHash(hash);
            upstreamEnterpriseContractDAO.updateContract(contract);
        }

        ContractUploadResultVO vo = new ContractUploadResultVO();
        vo.setFid(contract.getFid()).setHash(hash);
        return new ResponseResult().setCode(0).setMsg("获取hash成功").setData(vo);
    }

    public ResponseResult contractLaunch(String token, int fid, String hash, int receiver) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (hash == null) {
            return new ResponseResult().setCode(-1).setMsg("hash为空");
        }
        UpstreamEnterpriseContract contract = upstreamEnterpriseContractDAO.getContract(fid);
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
        upstreamEnterpriseContractDAO.updateContract(contract);

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
            return upstreamEnterpriseContractDAO.saveContractToFisco(contract, signature);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult receiveContract(String token, int fid) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO detailContract;
        try {
            detailContract = upstreamEnterpriseContractDAO.getContractFromFisco(fid);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (Integer.parseInt(detailContract.getSponsor()) != ENTERPRISE_CODE && Integer.parseInt(detailContract.getReceiver()) != ENTERPRISE_CODE) {
            return new ResponseResult().setCode(-9).setMsg("非法请求");
        }
        UpstreamEnterpriseContract contract = upstreamEnterpriseContractDAO.getContract(detailContract.getContractId());
        if (contract == null) {
            contract = new UpstreamEnterpriseContract();
            contract.setFid(detailContract.getContractId());
            contract.setHash(detailContract.getHash());
            contract.setSponsor(Integer.parseInt(detailContract.getSponsor()));
            contract.setReceiver(Integer.parseInt(detailContract.getReceiver()));
            contract.setStartDate(Timestamp.valueOf(detailContract.getStartDate()));
            upstreamEnterpriseContractDAO.saveContract(contract);
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
        String signature = SignVerifyUtil.sign(privateKey, detailContract.getHash());

        try {
            return upstreamEnterpriseContractDAO.receiveContractToFisco(fid, ENTERPRISE_CODE, signature);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult listContract(String token) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<ContractVO> contracts = upstreamEnterpriseContractDAO.listEnableContract().stream()
                .map(upstreamEnterpriseContract -> {
                    ContractVO vo = new ContractVO();
                    vo.setFid(upstreamEnterpriseContract.getFid());
                    vo.setHash(upstreamEnterpriseContract.getHash());
                    String sponsor = EnterpriseUtil.getEnterpriseNameByCode(upstreamEnterpriseContract.getSponsor());
                    if (sponsor == null) {
                        sponsor = enterpriseDAO.getEnterpriseByCode(upstreamEnterpriseContract.getSponsor()).getName();
                        EnterpriseUtil.putCodeName(upstreamEnterpriseContract.getSponsor(), sponsor);
                    }
                    vo.setSponsor(sponsor);
                    String receiver = EnterpriseUtil.getEnterpriseNameByCode(upstreamEnterpriseContract.getReceiver());
                    if (receiver == null) {
                        receiver = enterpriseDAO.getEnterpriseByCode(upstreamEnterpriseContract.getReceiver()).getName();
                        EnterpriseUtil.putCodeName(upstreamEnterpriseContract.getReceiver(), receiver);
                    }
                    vo.setReceiver(receiver);
                    if (upstreamEnterpriseContract.getStartDate() == null) {
                        vo.setStartDate(0L);
                    } else {
                        vo.setStartDate(upstreamEnterpriseContract.getStartDate().getTime());
                    }
                    return vo;
                }).collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(contracts);
    }

    public ResponseResult getContract(String token, int fid) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO vo;
        try {
            vo = upstreamEnterpriseContractDAO.getContractFromFisco(fid);
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

    public ResponseResult updateContract(String token, int fid, String status) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return upstreamEnterpriseContractDAO.updateContractStatusToFisco(ENTERPRISE_CODE, fid, status);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部错误");
        }
    }

    public ResponseResult getEnterpriseCredit(String token) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return upstreamEnterpriseTokenDAO.getEnterpriseCredit(ENTERPRISE_CODE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult getEnterpriseToken(String token) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return upstreamEnterpriseTokenDAO.getEnterpriseToken(ENTERPRISE_CODE);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult payEnterpriseToken(String token, int code, BigInteger val, Integer type, Integer id) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (val.compareTo(BigInteger.valueOf(0)) < 0) {
            return new ResponseResult().setCode(-5).setMsg("金额不可为负值");
        }
        ResponseResult result;
        try {
            result = upstreamEnterpriseTokenDAO.payEnterpriseToken(ENTERPRISE_CODE, code, val);
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
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<TransactionRecordVO> list = enterpriseDAO.listTransactionRecord(ENTERPRISE_CODE);
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(list);
    }

    public ResponseResult getSignatureOfText(String token, String text) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
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

    public ResponseResult saveCargo(String token, String content, int consignor, Integer contractId, Integer expressId, Integer insuranceId) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        UpstreamEnterpriseCargoReceive cargoReceive = new UpstreamEnterpriseCargoReceive();
        cargoReceive.setContent(content);
        cargoReceive.setConsignor(consignor);
        cargoReceive.setTime(new Timestamp(System.currentTimeMillis()));
        upstreamEnterpriseCargoDAO.saveCargo(cargoReceive);
        try {
            return upstreamEnterpriseCargoDAO.saveCargoToFisco(cargoReceive.getId(), content, consignor, contractId, insuranceId, expressId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("服务器内部状态错误");
        }
    }

    public ResponseResult listCargo(String token) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(upstreamEnterpriseCargoDAO.listCargo());
    }

    public ResponseResult getCargo(String token, int id) {
        if (upstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailCargoVO cargoVO;
        try {
            cargoVO = upstreamEnterpriseCargoDAO.getCargoFromFisco(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseResult().setCode(-11).setMsg("服务器内部状态错误");
        }
        CargoResponseVO vo = CargoResponseVO.from(cargoVO);
        String consignor = EnterpriseUtil.getEnterpriseNameByCode(cargoVO.getConsignor());
        if (consignor == null) {
            consignor = enterpriseDAO.getEnterpriseByCode(cargoVO.getConsignor()).getName();
            EnterpriseUtil.putCodeName(cargoVO.getConsignor(), consignor);
        }
        vo.setConsignor(consignor);
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(vo);
    }

    private boolean checkLegalEnterpriseType(int type) {
        List<Integer> codeList = enterpriseDAO.listEnterprise().stream()
                .map(Enterprise::getCode).collect(Collectors.toList());
        return codeList.contains(type);
    }

}
