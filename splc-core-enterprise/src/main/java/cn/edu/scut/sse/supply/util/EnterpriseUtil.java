package cn.edu.scut.sse.supply.util;

import cn.edu.scut.sse.supply.contracts.ContractRepo;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.edu.scut.sse.supply.util.ContractUtil.CONTRACT_REPO_ADDRESS;

/**
 * @author Yukino Yukinoshita
 */

public class EnterpriseUtil {

    /**
     * 企业代码 -> 企业名称 映射表
     */
    private static Map<Integer, String> enterpriseCodeNameMap = new HashMap<>();

    /**
     * 从映射表读取企业名称
     *
     * @param code 企业代码
     * @return 企业名称， null if not present
     */
    public static String getEnterpriseNameByCode(int code) {
        return enterpriseCodeNameMap.getOrDefault(code, null);
    }

    public static void putCodeName(int code, String name) {
        enterpriseCodeNameMap.put(code, name);
    }

    /**
     * 向区块链读取下一个可用的合同ID
     *
     * @return 合同ID
     */
    public static synchronized int getNextEnterpriseContractId() {
        BigInteger gasPrice = new BigInteger("300000000");
        BigInteger gasLimit = new BigInteger("300000000");
        Credentials credentials = Credentials.create("87f9a16a679c1ba680f7876f45782e44054f9e0662d310068490551452f89460");

        Web3j web3j = Web3jUtil.getWeb3j();
        ContractRepo contractRepo = ContractRepo.load(CONTRACT_REPO_ADDRESS, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt;
        try {
            receipt = contractRepo.getNextContractId().send();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        List<ContractRepo.GetNextContractIdCallbackEventResponse> list = contractRepo.getGetNextContractIdCallbackEvents(receipt);
        if (list.size() == 0) {
            return 0;
        }
        return list.get(0).id.intValue();
    }

}
