package cn.edu.scut.sse.supply.util;

import cn.edu.scut.sse.supply.contracts.ContractRepo;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Yukino Yukinoshita
 */

public class ContractUtil {

    public static final String CONTRACT_REPO_ADDRESS = "0x1ff253adc4044da48c3ab60f6c67a65f99f46d18";
    public static final String HASH_CALCULATOR_ADDRESS = "0xf4fa276d2f22005efeed8e3509e708b9b386d8c7";
    public static final String ENTERPRISE_TOKEN_ADDRESS = "0xd190eba7d51462cce1a6556591fdec01bd7392b4";
    public static final String BANK_APPLICATION_ADDRESS = "0xf67cbea10165443f0318d7bfe387b9b638c71844";
    public static final String INSURANCE_APPLICATION_ADDRESS = "0x97dffdc67c287090360d3e5d8f1393912096bde0";
    public static final String EXPRESS_APPLICATION_ADDRESS = "0x8b94b5d04b50aa80198742b8fef58c60beac877e";
    public static final String CARGO_RECEIVE_ADDRESS = "0xec7e1d5b79d62b37d08275a98b2ddd6cdd0b010f";

    public static synchronized int getNextContractId() {
        BigInteger gasPrice = new BigInteger("300000000");
        BigInteger gasLimit = new BigInteger("300000000");
        Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");

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
