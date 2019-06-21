package cn.edu.scut.sse.supply.coreenterprise.service;

import cn.edu.scut.sse.supply.coreenterprise.dao.CoreEnterpriseContractDAO;
import cn.edu.scut.sse.supply.coreenterprise.dao.CoreEnterpriseUserDAO;
import cn.edu.scut.sse.supply.general.dao.EnterpriseDAO;
import cn.edu.scut.sse.supply.general.dao.KeystoreDAO;
import cn.edu.scut.sse.supply.coreenterprise.entity.pojo.CoreEnterpriseContract;
import cn.edu.scut.sse.supply.coreenterprise.entity.pojo.CoreEnterpriseUser;
import cn.edu.scut.sse.supply.general.entity.pojo.Enterprise;
import cn.edu.scut.sse.supply.general.entity.vo.ContractUploadResultVO;
import cn.edu.scut.sse.supply.general.entity.vo.ContractVO;
import cn.edu.scut.sse.supply.general.entity.vo.DetailContractVO;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.util.EnterpriseUtil;
import cn.edu.scut.sse.supply.util.HashUtil;
import cn.edu.scut.sse.supply.util.SignVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author Yukino Yukinoshita
 */

@Service
public class CoreEnterpriseService {

    private CoreEnterpriseUserDAO coreEnterpriseUserDAO;
    private CoreEnterpriseContractDAO coreEnterpriseContractDAO;
    private EnterpriseDAO enterpriseDAO;
    private KeystoreDAO keystoreDAO;

    private static final int ENTERPRISE_CODE = 4001;
    private static final String PRIVATE_KEY_PATH = "../webapps/api/WEB-INF/classes/private_key_" + ENTERPRISE_CODE;

    @Autowired
    public CoreEnterpriseService(CoreEnterpriseUserDAO coreEnterpriseUserDAO,
                                 CoreEnterpriseContractDAO coreEnterpriseContractDAO,
                                 EnterpriseDAO enterpriseDAO,
                                 KeystoreDAO keystoreDAO) {
        this.coreEnterpriseUserDAO = coreEnterpriseUserDAO;
        this.coreEnterpriseContractDAO = coreEnterpriseContractDAO;
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
        CoreEnterpriseUser user = coreEnterpriseUserDAO.getUserByName(username);
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
        if (coreEnterpriseUserDAO.getUserByName(username) != null) {
            result.setCode(-2);
            result.setMsg("用户已存在");
            return result;
        }
        CoreEnterpriseUser user = new CoreEnterpriseUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setToken(HashUtil.findToken(user));
        coreEnterpriseUserDAO.saveUser(user);

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
        CoreEnterpriseUser user = coreEnterpriseUserDAO.getUserByToken(token);
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
        coreEnterpriseUserDAO.updateUser(user);
        result.setCode(0);
        result.setMsg("修改密码成功");
        result.setData(user);
        return result;
    }

    public ResponseResult contractUpload(String token, byte[] bytes) {
        if (coreEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        String hash = HashUtil.keccak256(bytes);
        List<CoreEnterpriseContract> recycleContracts = coreEnterpriseContractDAO.listRecycleContract();
        CoreEnterpriseContract contract;
        if (recycleContracts == null || recycleContracts.size() == 0) {
            contract = new CoreEnterpriseContract();
            contract.setHash(hash);
            contract.setSponsor(ENTERPRISE_CODE);
            contract.setReceiver(0);
            coreEnterpriseContractDAO.saveContract(contract);
        } else {
            contract = recycleContracts.get(0);
            contract.setHash(hash);
            coreEnterpriseContractDAO.updateContract(contract);
        }

        ContractUploadResultVO vo = new ContractUploadResultVO();
        vo.setFid(contract.getFid()).setHash(hash);
        return new ResponseResult().setCode(0).setMsg("获取hash成功").setData(vo);
    }

    public ResponseResult contractLaunch(String token, int fid, String hash, int receiver) {
        if (coreEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        if (hash == null) {
            return new ResponseResult().setCode(-1).setMsg("hash为空");
        }
        CoreEnterpriseContract contract = coreEnterpriseContractDAO.getContract(fid);
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
        coreEnterpriseContractDAO.updateContract(contract);

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
            return coreEnterpriseContractDAO.saveContractToFisco(contract, signature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult receiveContract(String token, int fid) {
        if (coreEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO detailContract;
        try {
            detailContract = coreEnterpriseContractDAO.getContractFromFisco(fid);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
        if (Integer.parseInt(detailContract.getSponsor()) != ENTERPRISE_CODE && Integer.parseInt(detailContract.getReceiver()) != ENTERPRISE_CODE) {
            return new ResponseResult().setCode(-9).setMsg("非法请求");
        }
        CoreEnterpriseContract contract = coreEnterpriseContractDAO.getContract(detailContract.getContractId());
        if (contract == null) {
            contract = new CoreEnterpriseContract();
            contract.setHash(detailContract.getHash());
            contract.setSponsor(Integer.parseInt(detailContract.getSponsor()));
            contract.setReceiver(Integer.parseInt(detailContract.getReceiver()));
            contract.setStartDate(Timestamp.valueOf(detailContract.getStartDate()));
            coreEnterpriseContractDAO.saveContract(contract);
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
            return coreEnterpriseContractDAO.receiveContractToFisco(fid, ENTERPRISE_CODE, signature);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部状态错误");
        }
    }

    public ResponseResult listContract(String token) {
        if (coreEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        List<ContractVO> contracts = coreEnterpriseContractDAO.listEnableContract().stream()
                .map(coreEnterpriseContract -> {
                    ContractVO vo = new ContractVO();
                    vo.setFid(coreEnterpriseContract.getFid());
                    vo.setHash(coreEnterpriseContract.getHash());
                    String sponsor = EnterpriseUtil.getEnterpriseNameByCode(coreEnterpriseContract.getSponsor());
                    if (sponsor == null) {
                        sponsor = enterpriseDAO.getEnterpriseByCode(coreEnterpriseContract.getSponsor()).getName();
                        EnterpriseUtil.putCodeName(coreEnterpriseContract.getSponsor(), sponsor);
                    }
                    vo.setSponsor(sponsor);
                    String receiver = EnterpriseUtil.getEnterpriseNameByCode(coreEnterpriseContract.getReceiver());
                    if (receiver == null) {
                        receiver = enterpriseDAO.getEnterpriseByCode(coreEnterpriseContract.getReceiver()).getName();
                        EnterpriseUtil.putCodeName(coreEnterpriseContract.getReceiver(), receiver);
                    }
                    vo.setReceiver(receiver);
                    if (coreEnterpriseContract.getStartDate() == null) {
                        vo.setStartDate("未知日期");
                    } else {
                        vo.setStartDate(format.format(coreEnterpriseContract.getStartDate()));
                    }
                    return vo;
                }).collect(Collectors.toList());
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(contracts);
    }

    public ResponseResult getContract(String token, int fid) {
        if (coreEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        DetailContractVO vo;
        try {
            vo = coreEnterpriseContractDAO.getContractFromFisco(fid);
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
        if (coreEnterpriseUserDAO.getUserByToken(token) == null) {
            return new ResponseResult().setCode(-1).setMsg("用户状态已改变");
        }
        try {
            return coreEnterpriseContractDAO.updateContractStatusToFisco(ENTERPRISE_CODE, fid, status);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult().setCode(-11).setMsg("内部错误");
        }
    }

    private boolean checkLegalEnterpriseType(int type) {
        List<Integer> codeList = enterpriseDAO.listEnterprise().stream()
                .map(Enterprise::getCode).collect(Collectors.toList());
        return codeList.contains(type);
    }

}