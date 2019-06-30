package cn.edu.scut.sse.supply.downstreamenterprise.service;

import cn.edu.scut.sse.supply.downstreamenterprise.dao.DownstreamEnterpriseCargoDAO;
import cn.edu.scut.sse.supply.downstreamenterprise.dao.DownstreamEnterpriseContractDAO;
import cn.edu.scut.sse.supply.downstreamenterprise.dao.DownstreamEnterpriseTokenDAO;
import cn.edu.scut.sse.supply.downstreamenterprise.dao.DownstreamEnterpriseUserDAO;
import cn.edu.scut.sse.supply.downstreamenterprise.entity.pojo.DownstreamEnterpriseCargoReceive;
import cn.edu.scut.sse.supply.downstreamenterprise.entity.pojo.DownstreamEnterpriseContract;
import cn.edu.scut.sse.supply.downstreamenterprise.entity.pojo.DownstreamEnterpriseUser;
import cn.edu.scut.sse.supply.downstreamenterprise.entity.vo.CargoResponseVO;
import cn.edu.scut.sse.supply.downstreamenterprise.entity.vo.DetailCargoVO;
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
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yukino Yukinoshita
 */

@Service
public class DownstreamEnterpriseService {

    private static final int ENTERPRISE_CODE = 4003;
    private static final String PRIVATE_KEY_PATH = "../webapps/downstream-enterprise/WEB-INF/classes/private_key_" + ENTERPRISE_CODE;
    private DownstreamEnterpriseUserDAO downstreamEnterpriseUserDAO;
    private DownstreamEnterpriseCargoDAO downstreamEnterpriseCargoDAO;
    private DownstreamEnterpriseTokenDAO downstreamEnterpriseTokenDAO;
    private DownstreamEnterpriseContractDAO downstreamEnterpriseContractDAO;
    private EnterpriseDAO enterpriseDAO;
    private KeystoreDAO keystoreDAO;

    @Autowired
    public DownstreamEnterpriseService(DownstreamEnterpriseUserDAO downstreamEnterpriseUserDAO,
                                       DownstreamEnterpriseCargoDAO downstreamEnterpriseCargoDAO,
                                       DownstreamEnterpriseTokenDAO downstreamEnterpriseTokenDAO,
                                       DownstreamEnterpriseContractDAO downstreamEnterpriseContractDAO,
                                       EnterpriseDAO enterpriseDAO,
                                       KeystoreDAO keystoreDAO) {
        this.downstreamEnterpriseUserDAO = downstreamEnterpriseUserDAO;
        this.downstreamEnterpriseCargoDAO = downstreamEnterpriseCargoDAO;
        this.downstreamEnterpriseTokenDAO = downstreamEnterpriseTokenDAO;
        this.downstreamEnterpriseContractDAO = downstreamEnterpriseContractDAO;
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
        DownstreamEnterpriseUser user = downstreamEnterpriseUserDAO.getUserByName(username);
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
        if (downstreamEnterpriseUserDAO.getUserByName(username) != null) {
            result.setCode(-2);
            result.setMsg("用户已存在");
            return result;
        }
        DownstreamEnterpriseUser user = new DownstreamEnterpriseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setToken(HashUtil.findToken(user));
        downstreamEnterpriseUserDAO.saveUser(user);

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
        DownstreamEnterpriseUser user = downstreamEnterpriseUserDAO.getUserByToken(token);
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
        downstreamEnterpriseUserDAO.updateUser(user);
        result.setCode(0);
        result.setMsg("修改密码成功");
        result.setData(user);
        return result;
    }

    public ResponseResult contractUpload(String token, byte[] bytes) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        String hash = HashUtil.keccak256(bytes);
        List<DownstreamEnterpriseContract> recycleContracts = downstreamEnterpriseContractDAO.listRecycleContract();
        DownstreamEnterpriseContract contract;
        if (recycleContracts == null || recycleContracts.size() == 0) {
            contract = new DownstreamEnterpriseContract();
            contract.setHash(hash);
            contract.setSponsor(ENTERPRISE_CODE);
            contract.setReceiver(0);
            downstreamEnterpriseContractDAO.saveContract(contract);
        } else {
            contract = recycleContracts.get(0);
            contract.setHash(hash);
            downstreamEnterpriseContractDAO.updateContract(contract);
        }

        ContractUploadResultVO vo = new ContractUploadResultVO();
        vo.setFid(contract.getFid()).setHash(hash);
        return new ResponseResult().setCode(0).setMsg("获取hash成功").setData(vo);
    }

    public ResponseResult contractLaunch(String token, int fid, String hash, int receiver) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (hash == null) {
            return new ResponseResult().setCode(-1).setMsg("hash为空");
        }
        DownstreamEnterpriseContract contract = downstreamEnterpriseContractDAO.getContract(fid);
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
        downstreamEnterpriseContractDAO.updateContract(contract);

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
            return downstreamEnterpriseContractDAO.saveContractToFisco(contract, signature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult receiveContract(String token, int fid) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO detailContract;
        try {
            detailContract = downstreamEnterpriseContractDAO.getContractFromFisco(fid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (Integer.parseInt(detailContract.getSponsor()) != ENTERPRISE_CODE && Integer.parseInt(detailContract.getReceiver()) != ENTERPRISE_CODE) {
            return new ResponseResult().setCode(-9).setMsg("非法请求");
        }
        DownstreamEnterpriseContract contract = downstreamEnterpriseContractDAO.getContract(detailContract.getContractId());
        if (contract == null) {
            contract = new DownstreamEnterpriseContract();
            contract.setFid(detailContract.getContractId());
            contract.setHash(detailContract.getHash());
            contract.setSponsor(Integer.parseInt(detailContract.getSponsor()));
            contract.setReceiver(Integer.parseInt(detailContract.getReceiver()));
            contract.setStartDate(Timestamp.valueOf(detailContract.getStartDate()));
            downstreamEnterpriseContractDAO.saveContract(contract);
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
            return downstreamEnterpriseContractDAO.receiveContractToFisco(fid, ENTERPRISE_CODE, signature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult listContract(String token) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<ContractVO> contracts = downstreamEnterpriseContractDAO.listEnableContract().stream()
                .map(downstreamEnterpriseContract -> {
                    ContractVO vo = new ContractVO();
                    vo.setFid(downstreamEnterpriseContract.getFid());
                    vo.setHash(downstreamEnterpriseContract.getHash());
                    String sponsor = EnterpriseUtil.getEnterpriseNameByCode(downstreamEnterpriseContract.getSponsor());
                    if (sponsor == null) {
                        sponsor = enterpriseDAO.getEnterpriseByCode(downstreamEnterpriseContract.getSponsor()).getName();
                        EnterpriseUtil.putCodeName(downstreamEnterpriseContract.getSponsor(), sponsor);
                    }
                    vo.setSponsor(sponsor);
                    String receiver = EnterpriseUtil.getEnterpriseNameByCode(downstreamEnterpriseContract.getReceiver());
                    if (receiver == null) {
                        receiver = enterpriseDAO.getEnterpriseByCode(downstreamEnterpriseContract.getReceiver()).getName();
                        EnterpriseUtil.putCodeName(downstreamEnterpriseContract.getReceiver(), receiver);
                    }
                    vo.setReceiver(receiver);
                    if (downstreamEnterpriseContract.getStartDate() == null) {
                        vo.setStartDate(0L);
                    } else {
                        vo.setStartDate(downstreamEnterpriseContract.getStartDate().getTime());
                    }
                    return vo;
                }).collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(contracts);
    }

    public ResponseResult getContract(String token, int fid) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO vo;
        try {
            vo = downstreamEnterpriseContractDAO.getContractFromFisco(fid);
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
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return downstreamEnterpriseContractDAO.updateContractStatusToFisco(ENTERPRISE_CODE, fid, status);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部错误");
        }
    }

    public ResponseResult getEnterpriseCredit(String token) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return downstreamEnterpriseTokenDAO.getEnterpriseCredit(ENTERPRISE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult getEnterpriseToken(String token) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return downstreamEnterpriseTokenDAO.getEnterpriseToken(ENTERPRISE_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult payEnterpriseToken(String token, int code, BigInteger val, Integer type, Integer id) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (val.compareTo(BigInteger.valueOf(0)) < 0) {
            return new ResponseResult().setCode(-5).setMsg("金额不可为负值");
        }
        ResponseResult result;
        try {
            result = downstreamEnterpriseTokenDAO.payEnterpriseToken(ENTERPRISE_CODE, code, val);
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
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        List<TransactionRecordVO> list = enterpriseDAO.listTransactionRecord(ENTERPRISE_CODE);
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(list);
    }

    public ResponseResult getSignatureOfText(String token, String text) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
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
        String signature = SignVerifyUtil.sign(privateKey, HashUtil.keccak256(text.getBytes()));
        return new ResponseResult().setCode(0).setMsg("签名成功").setData(signature);
    }

    public ResponseResult saveCargo(String token, String content, int consignor, Integer contractId, Integer expressId, Integer insuranceId) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DownstreamEnterpriseCargoReceive cargoReceive = new DownstreamEnterpriseCargoReceive();
        cargoReceive.setContent(content);
        cargoReceive.setConsignor(consignor);
        cargoReceive.setTime(new Timestamp(System.currentTimeMillis()));
        downstreamEnterpriseCargoDAO.saveCargo(cargoReceive);
        try {
            return downstreamEnterpriseCargoDAO.saveCargoToFisco(cargoReceive.getId(), content, consignor, contractId, insuranceId, expressId);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("服务器内部状态错误");
        }
    }

    public ResponseResult listCargo(String token) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(downstreamEnterpriseCargoDAO.listCargo());
    }

    public ResponseResult getCargo(String token, int id) {
        if (downstreamEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailCargoVO cargoVO;
        try {
            cargoVO = downstreamEnterpriseCargoDAO.getCargoFromFisco(id);
        } catch (Exception e) {
            e.printStackTrace();
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
