package cn.edu.scut.sse.supply.downstreamenterprise.dao;

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
public class DownstreamEnterpriseTokenDAO {

    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("b7a89f23d6e8570c8d09bd6a0f651437c0e5efa0c4d4004cf7fde9149be39fc2");
    private String address = ContractUtil.ENTERPRISE_TOKEN_ADDRESS;

    public ResponseResult getEnterpriseCredit(int code) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        BigInteger credit = enterpriseToken.getCredit(BigInteger.valueOf(code)).send();
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(credit);
    }

    public ResponseResult getEnterpriseToken(int code) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        BigInteger token = enterpriseToken.getToken(BigInteger.valueOf(code)).send();
        return new ResponseResult().setCode(0).setMsg("查询成功").setData(token);
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
