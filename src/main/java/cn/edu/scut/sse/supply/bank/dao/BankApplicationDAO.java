package cn.edu.scut.sse.supply.bank.dao;

import cn.edu.scut.sse.supply.bank.entity.pojo.BankApplication;
import cn.edu.scut.sse.supply.bank.entity.vo.DetailBankApplicationVO;
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
public class BankApplicationDAO {

    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");
    private String address = ContractUtil.BANK_APPLICATION_ADDRESS;

    public int saveBankApplication(BankApplication application) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(application);
        session.getTransaction().commit();
        session.close();
        return application.getFid();
    }

    public BankApplication getBankApplication(int fid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BankApplication> criteriaQuery = criteriaBuilder.createQuery(BankApplication.class);
        Root<BankApplication> root = criteriaQuery.from(BankApplication.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("fid"), fid));

        BankApplication application = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return application;
    }

    public List<BankApplication> listBankApplication() {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BankApplication> criteriaQuery = criteriaBuilder.createQuery(BankApplication.class);
        Root<BankApplication> root = criteriaQuery.from(BankApplication.class);
        criteriaQuery.select(root);

        List<BankApplication> applications = session.createQuery(criteriaQuery).list();

        session.close();
        return applications;
    }

    public List<BankApplication> listBankApplication(int code) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BankApplication> criteriaQuery = criteriaBuilder.createQuery(BankApplication.class);
        Root<BankApplication> root = criteriaQuery.from(BankApplication.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("sponsor"), code));

        List<BankApplication> applications = session.createQuery(criteriaQuery).list();

        session.close();
        return applications;
    }

    public ResponseResult saveBankApplicationToFisco(int fid, String content, int sponsor, int receiver, String sponsorSignature, int applicationType) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.BankApplication bankApplicationContract = cn.edu.scut.sse.supply.contracts.BankApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = bankApplicationContract.createApplication(BigInteger.valueOf(fid), content,
                BigInteger.valueOf(sponsor), BigInteger.valueOf(receiver), sponsorSignature,
                BigInteger.valueOf(applicationType)).send();
        List<cn.edu.scut.sse.supply.contracts.BankApplication.CreateApplicationEventEventResponse> responseList = bankApplicationContract.getCreateApplicationEventEvents(receipt);
        if (responseList.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        cn.edu.scut.sse.supply.contracts.BankApplication.CreateApplicationEventEventResponse response = responseList.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public ResponseResult receiveBankApplicationToFisco(int fid, String receiverSignature) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.BankApplication bankApplicationContract = cn.edu.scut.sse.supply.contracts.BankApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = bankApplicationContract.receiveApplication(BigInteger.valueOf(fid), receiverSignature).send();
        List<cn.edu.scut.sse.supply.contracts.BankApplication.ReceiveApplicationEventEventResponse> responseList = bankApplicationContract.getReceiveApplicationEventEvents(receipt);
        if (responseList.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        cn.edu.scut.sse.supply.contracts.BankApplication.ReceiveApplicationEventEventResponse response = responseList.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public ResponseResult updateBankApplicationStatusToFisco(int fid, String status) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.BankApplication bankApplicationContract = cn.edu.scut.sse.supply.contracts.BankApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = bankApplicationContract.updateApplicationStatus(BigInteger.valueOf(fid), status).send();
        List<cn.edu.scut.sse.supply.contracts.BankApplication.UpdateApplicationStatusEventEventResponse> responseList = bankApplicationContract.getUpdateApplicationStatusEventEvents(receipt);
        if (responseList.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        cn.edu.scut.sse.supply.contracts.BankApplication.UpdateApplicationStatusEventEventResponse response = responseList.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public DetailBankApplicationVO getBankApplicationFromFisco(int fid) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.BankApplication bankApplicationContract = cn.edu.scut.sse.supply.contracts.BankApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = bankApplicationContract.getApplication(BigInteger.valueOf(fid)).send();
        List<cn.edu.scut.sse.supply.contracts.BankApplication.GetApplicationCallbackEventResponse> responseList = bankApplicationContract.getGetApplicationCallbackEvents(receipt);
        if (responseList.size() == 0) {
            return null;
        }
        cn.edu.scut.sse.supply.contracts.BankApplication.GetApplicationCallbackEventResponse response = responseList.get(0);
        DetailBankApplicationVO vo = new DetailBankApplicationVO();
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
