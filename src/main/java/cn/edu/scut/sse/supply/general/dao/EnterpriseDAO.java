package cn.edu.scut.sse.supply.general.dao;

import cn.edu.scut.sse.supply.contracts.EnterpriseToken;
import cn.edu.scut.sse.supply.general.entity.pojo.Enterprise;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.util.ContractUtil;
import cn.edu.scut.sse.supply.util.SessionFactoryUtil;
import cn.edu.scut.sse.supply.util.Web3jUtil;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;

/**
 * @author Yukino Yukinoshita
 */

@Repository
public class EnterpriseDAO {

    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");
    private String address = ContractUtil.ENTERPRISE_TOKEN_ADDRESS;

    public Enterprise getEnterpriseByCode(int code) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Enterprise> criteriaQuery = criteriaBuilder.createQuery(Enterprise.class);
        Root<Enterprise> root = criteriaQuery.from(Enterprise.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("code"), code));

        Enterprise enterprise = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return enterprise;
    }

    public List<Enterprise> listEnterprise() {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Enterprise> criteriaQuery = criteriaBuilder.createQuery(Enterprise.class);
        Root<Enterprise> root = criteriaQuery.from(Enterprise.class);
        criteriaQuery.select(root);

        List<Enterprise> enterprises = session.createQuery(criteriaQuery).list();

        session.close();
        return enterprises;
    }

    public ResponseResult createTokenAccountToFisco(int code, String name) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = enterpriseToken.createEnterprise(BigInteger.valueOf(code), name).send();
        List<EnterpriseToken.CreateEnterpriseEventEventResponse> list = enterpriseToken.getCreateEnterpriseEventEvents(receipt);
        if (list.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得返回消息");
        }
        EnterpriseToken.CreateEnterpriseEventEventResponse response = list.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public ResponseResult updateTokenAccountToFisco(int code, String name) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        EnterpriseToken enterpriseToken = EnterpriseToken.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = enterpriseToken.updateEnterprise(BigInteger.valueOf(code), name).send();
        List<EnterpriseToken.UpdateEnterpriseEventEventResponse> list = enterpriseToken.getUpdateEnterpriseEventEvents(receipt);
        if (list.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得返回消息");
        }
        EnterpriseToken.UpdateEnterpriseEventEventResponse response = list.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

}
