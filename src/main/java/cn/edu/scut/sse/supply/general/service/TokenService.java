package cn.edu.scut.sse.supply.general.service;

import cn.edu.scut.sse.supply.contracts.EnterpriseToken;
import cn.edu.scut.sse.supply.general.dao.UserDAO;
import cn.edu.scut.sse.supply.general.entity.pojo.User;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.util.Web3jUtil;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author Yukino Yukinoshita
 */

@Service
public class TokenService {

    private UserDAO userDAO;
    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");
    private String address = "0x4a9cdfa028ccc5c1e2ff6787cdaf436130960f2a";

    @Autowired
    public TokenService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ResponseResult getToken(String token) throws Exception {
        ResponseResult result = new ResponseResult();
        int code = getEnterpriseCodeByUserToken(token);
        if (code == 0) {
            result.setCode(-1);
            result.setMsg("用户不存在");
            return result;
        }

        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken contract = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        BigInteger tokenVal = contract.getToken(BigInteger.valueOf(code)).send();
        result.setCode(0);
        result.setMsg("查询成功");
        result.setData(tokenVal);
        return result;
    }

    public ResponseResult addToken(String token, String enterpriseCode, BigInteger value) throws Exception {
        ResponseResult result = new ResponseResult();
        int code = getEnterpriseCodeByUserToken(token);
        if (code == 0) {
            result.setCode(-1);
            result.setMsg("用户不存在");
            return result;
        }
        if (code != 1001) {
            result.setCode(-10);
            result.setMsg("只有银行账户可以增加token");
            return result;
        }

        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken contract = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        TransactionReceipt receipt = contract.addToken(new BigInteger(enterpriseCode), value).send();
        if (contract.getAddTokenEventEvents(receipt).size() < 1) {
            result.setCode(-6);
            result.setMsg("未获得返回消息");
            result.setData(receipt.getOutput());
            return result;
        }

        result.setCode(contract.getAddTokenEventEvents(receipt).get(0).code.intValue());
        result.setMsg(contract.getAddTokenEventEvents(receipt).get(0).msg);
        result.setData(receipt.getOutput());
        return result;
    }

    public ResponseResult subToken(String token, String enterpriseCode, BigInteger value) throws Exception {
        ResponseResult result = new ResponseResult();
        int code = getEnterpriseCodeByUserToken(token);
        if (code == 0) {
            result.setCode(-1);
            result.setMsg("用户不存在");
            return result;
        }
        if (code != 1001) {
            result.setCode(-10);
            result.setMsg("只有银行账户可以减少token");
            return result;
        }

        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken contract = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        TransactionReceipt receipt = contract.subToken(new BigInteger(enterpriseCode), value).send();
        if (contract.getSubTokenEventEvents(receipt).size() < 1) {
            result.setCode(-6);
            result.setMsg("未获得返回消息");
            result.setData(receipt.getOutput());
            return result;
        }

        result.setCode(contract.getSubTokenEventEvents(receipt).get(0).code.intValue());
        result.setMsg(contract.getSubTokenEventEvents(receipt).get(0).msg);
        result.setData(receipt.getOutput());
        return result;
    }

    public ResponseResult payToken(String token, String targetEnterpriseCode, BigInteger value) throws Exception {
        ResponseResult result = new ResponseResult();
        int code = getEnterpriseCodeByUserToken(token);
        if (code == 0) {
            result.setCode(-1);
            result.setMsg("用户不存在");
            return result;
        }

        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken contract = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        TransactionReceipt receipt = contract.payToken(BigInteger.valueOf(code), new BigInteger(targetEnterpriseCode), value).send();
        if (contract.getPayTokenEventEvents(receipt).size() < 1) {
            result.setCode(-6);
            result.setMsg("未获得返回消息");
            result.setData(receipt.getOutput());
            return result;
        }

        result.setCode(contract.getPayTokenEventEvents(receipt).get(0).code.intValue());
        result.setMsg(contract.getPayTokenEventEvents(receipt).get(0).msg);
        result.setData(receipt.getOutput());
        return result;
    }

    private int getEnterpriseCodeByUserToken(String token) {
        User user = userDAO.getUserByToken(token);
        if (user == null) {
            return 0;
        }
        return user.getType();
    }

}
