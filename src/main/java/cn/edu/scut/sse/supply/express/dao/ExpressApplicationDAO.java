package cn.edu.scut.sse.supply.express.dao;

import cn.edu.scut.sse.supply.express.entity.pojo.ExpressApplication;
import cn.edu.scut.sse.supply.express.entity.vo.DetailExpressApplicationVO;
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
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Yukino Yukinoshita
 */

@Repository
public class ExpressApplicationDAO {

    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");
    private String address = ContractUtil.EXPRESS_APPLICATION_ADDRESS;

    public int saveExpressApplication(ExpressApplication application) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(application);
        session.getTransaction().commit();
        session.close();
        return application.getFid();
    }

    public ExpressApplication getExpressApplication(int fid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpressApplication> criteriaQuery = criteriaBuilder.createQuery(ExpressApplication.class);
        Root<ExpressApplication> root = criteriaQuery.from(ExpressApplication.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("fid"), fid));

        ExpressApplication application = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return application;
    }

    public List<ExpressApplication> listExpressApplication() {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpressApplication> criteriaQuery = criteriaBuilder.createQuery(ExpressApplication.class);
        Root<ExpressApplication> root = criteriaQuery.from(ExpressApplication.class);
        criteriaQuery.select(root);

        List<ExpressApplication> applications = session.createQuery(criteriaQuery).list();

        session.close();
        return applications;
    }

    public List<ExpressApplication> listExpressApplication(int code) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpressApplication> criteriaQuery = criteriaBuilder.createQuery(ExpressApplication.class);
        Root<ExpressApplication> root = criteriaQuery.from(ExpressApplication.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("sponsor"), code));

        List<ExpressApplication> applications = session.createQuery(criteriaQuery).list();

        session.close();
        return applications;
    }

    public ResponseResult saveExpressApplicationToFisco(int fid, String content, int sponsor, int receiver, String sponsorSignature, int applicationType) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.ExpressApplication expressApplicationContract = cn.edu.scut.sse.supply.contracts.ExpressApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = expressApplicationContract.createApplication(BigInteger.valueOf(fid), content,
                BigInteger.valueOf(sponsor), BigInteger.valueOf(receiver), sponsorSignature,
                BigInteger.valueOf(applicationType)).send();
        List<cn.edu.scut.sse.supply.contracts.ExpressApplication.CreateApplicationEventEventResponse> responseList = expressApplicationContract.getCreateApplicationEventEvents(receipt);
        if (responseList.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        cn.edu.scut.sse.supply.contracts.ExpressApplication.CreateApplicationEventEventResponse response = responseList.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public ResponseResult receiveExpressApplicationToFisco(int fid, String receiverSignature) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.ExpressApplication expressApplicationContract = cn.edu.scut.sse.supply.contracts.ExpressApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = expressApplicationContract.receiveApplication(BigInteger.valueOf(fid), receiverSignature).send();
        List<cn.edu.scut.sse.supply.contracts.ExpressApplication.ReceiveApplicationEventEventResponse> responseList = expressApplicationContract.getReceiveApplicationEventEvents(receipt);
        if (responseList.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        cn.edu.scut.sse.supply.contracts.ExpressApplication.ReceiveApplicationEventEventResponse response = responseList.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public ResponseResult updateExpressApplicationStatusToFisco(int fid, String status) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.ExpressApplication expressApplicationContract = cn.edu.scut.sse.supply.contracts.ExpressApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = expressApplicationContract.updateApplicationStatus(BigInteger.valueOf(fid), status).send();
        List<cn.edu.scut.sse.supply.contracts.ExpressApplication.UpdateApplicationStatusEventEventResponse> responseList = expressApplicationContract.getUpdateApplicationStatusEventEvents(receipt);
        if (responseList.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        cn.edu.scut.sse.supply.contracts.ExpressApplication.UpdateApplicationStatusEventEventResponse response = responseList.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public DetailExpressApplicationVO getExpressApplicationFromFisco(int fid) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.ExpressApplication expressApplicationContract = cn.edu.scut.sse.supply.contracts.ExpressApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = expressApplicationContract.getApplication(BigInteger.valueOf(fid)).send();
        List<cn.edu.scut.sse.supply.contracts.ExpressApplication.GetApplicationCallbackEventResponse> responseList = expressApplicationContract.getGetApplicationCallbackEvents(receipt);
        if (responseList.size() == 0) {
            return null;
        }
        cn.edu.scut.sse.supply.contracts.ExpressApplication.GetApplicationCallbackEventResponse response = responseList.get(0);
        DetailExpressApplicationVO vo = new DetailExpressApplicationVO();
        vo.setId(fid);
        vo.setContent(response.content);
        vo.setSponsor(response.sponsor.intValue());
        vo.setReceiver(response.receiver.intValue());
        vo.setSponsorSignature(response.sponsorSignature);
        vo.setReceiverSignature(response.receiverSignature);
        vo.setApplicationType(response.applicationType.intValue());
        vo.setStartTime(new Timestamp(response.startTime.longValue()));
        vo.setStatus(response.status);
        return vo;
    }

}
