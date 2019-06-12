package cn.edu.scut.sse.supply.service;

import cn.edu.scut.sse.supply.contracts.HelloWorld;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;

/**
 * @author Yukino Yukinoshita
 */

@org.springframework.stereotype.Service
public class MainService {

    /**
     * @return get
     */
    public String get() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        channelEthereumService.setTimeout(10000);

        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());
        BigInteger gasPrice = new BigInteger("300000000");
        BigInteger gasLimit = new BigInteger("300000000");
        Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");
        String address = "0x8c17cf316c1063ab6c89df875e96c9f0f5b2f744";

        HelloWorld contract = HelloWorld.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        return contract.get().send();
    }

    /**
     * @return getBlockNumber
     */
    public String getBlockNumber() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);

        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());

        BigInteger blockNumber = web3j.getBlockNumber().send().getBlockNumber();
        return blockNumber.toString();
    }

    /**
     * @return tx ref
     */
    public String set(String val) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.run();
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);
        channelEthereumService.setTimeout(10000);

        Web3j web3j = Web3j.build(channelEthereumService, service.getGroupId());
        BigInteger gasPrice = new BigInteger("300000000");
        BigInteger gasLimit = new BigInteger("300000000");
        Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");
        String address = "0x8c17cf316c1063ab6c89df875e96c9f0f5b2f744";

        HelloWorld contract = HelloWorld.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        TransactionReceipt transactionReceipt = contract.set(val).send();
        return transactionReceipt.getTransactionHash();
    }

}
