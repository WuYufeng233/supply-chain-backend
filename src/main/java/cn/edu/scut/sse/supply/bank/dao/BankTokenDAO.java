package cn.edu.scut.sse.supply.bank.dao;

import cn.edu.scut.sse.supply.contracts.EnterpriseToken;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.util.ContractUtil;
import cn.edu.scut.sse.supply.util.Web3jUtil;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Yukino Yukinoshita
 */

@Repository
public class BankTokenDAO {

    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");
    private String address = ContractUtil.ENTERPRISE_TOKEN_ADDRESS;

    public ResponseResult setEnterpriseCredit(int code, BigInteger credit) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = enterpriseToken.setCredit(BigInteger.valueOf(code), credit).send();
        List<EnterpriseToken.SetCreditEventEventResponse> list = enterpriseToken.getSetCreditEventEvents(receipt);
        if (list.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得返回消息");
        }
        EnterpriseToken.SetCreditEventEventResponse response = list.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public BigInteger getEnterpriseCredit(int code) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        return enterpriseToken.getCredit(BigInteger.valueOf(code)).send();
    }

    public ResponseResult addEnterpriseToken(int code, BigInteger val) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = enterpriseToken.addToken(BigInteger.valueOf(code), val).send();
        List<EnterpriseToken.AddTokenEventEventResponse> list = enterpriseToken.getAddTokenEventEvents(receipt);
        if (list.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得返回消息");
        }
        EnterpriseToken.AddTokenEventEventResponse response = list.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public ResponseResult subEnterpriseToken(int code, BigInteger val) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = enterpriseToken.subToken(BigInteger.valueOf(code), val).send();
        List<EnterpriseToken.SubTokenEventEventResponse> list = enterpriseToken.getSubTokenEventEvents(receipt);
        if (list.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得返回消息");
        }
        EnterpriseToken.SubTokenEventEventResponse response = list.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public BigInteger getEnterpriseToken(int code) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        return enterpriseToken.getToken(BigInteger.valueOf(code)).send();
    }

    public ResponseResult payEnterpriseToken(int source, int target, BigInteger val) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = enterpriseToken.payToken(BigInteger.valueOf(source), BigInteger.valueOf(target), val).send();
        List<EnterpriseToken.PayTokenEventEventResponse> list = enterpriseToken.getPayTokenEventEvents(receipt);
        if (list.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得返回消息");
        }
        EnterpriseToken.PayTokenEventEventResponse response = list.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg).setData(receipt.getTransactionHash());
    }

}
