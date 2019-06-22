package cn.edu.scut.sse.supply.general.service;

import cn.edu.scut.sse.supply.util.Web3jUtil;
import org.fisco.bcos.web3j.protocol.Web3j;

import java.math.BigInteger;

/**
 * @author Yukino Yukinoshita
 */

@org.springframework.stereotype.Service
public class MainService {

    /**
     * @return getBlockNumber
     */
    public String getBlockNumber() throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        BigInteger blockNumber = web3j.getBlockNumber().send().getBlockNumber();
        return blockNumber.toString();
    }

}
