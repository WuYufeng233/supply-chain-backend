package cn.edu.scut.sse.supply.util;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Web3j 单例
 *
 * @author Yukino Yukinoshita
 */

public class Web3jUtil {

    private static volatile Web3j web3j;

    public static Web3j getWeb3j() {
        if (web3j == null) {
            synchronized (Web3jUtil.class) {
                if (web3j == null) {
                    ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
                    Service service = context.getBean(Service.class);
                    try {
                        service.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                    ChannelEthereumService channelEthereumService = new ChannelEthereumService();
                    channelEthereumService.setChannelService(service);
                    channelEthereumService.setTimeout(10000);
                    web3j = Web3j.build(channelEthereumService, service.getGroupId());
                }
            }
        }
        return web3j;
    }

}
