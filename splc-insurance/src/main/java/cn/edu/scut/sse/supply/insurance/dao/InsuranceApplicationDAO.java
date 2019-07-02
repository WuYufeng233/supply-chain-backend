package cn.edu.scut.sse.supply.insurance.dao;

import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.insurance.entity.pojo.InsuranceApplication;
import cn.edu.scut.sse.supply.insurance.entity.vo.DetailInsuranceApplicationVO;
import cn.edu.scut.sse.supply.insurance.entity.vo.InsuranceApplicationStatusVO;
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
public class InsuranceApplicationDAO {

    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("b320b7fe226d449c395c884f59ccc5e42b632027cfce2dab30dff1f31f2f7dae");
    private String address = ContractUtil.INSURANCE_APPLICATION_ADDRESS;

    public int saveInsuranceApplication(InsuranceApplication application) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(application);
        session.getTransaction().commit();
        session.close();
        return application.getFid();
    }

    public InsuranceApplication getInsuranceApplication(int fid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<InsuranceApplication> criteriaQuery = criteriaBuilder.createQuery(InsuranceApplication.class);
        Root<InsuranceApplication> root = criteriaQuery.from(InsuranceApplication.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("fid"), fid));

        InsuranceApplication application = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return application;
    }

    public List<InsuranceApplication> listInsuranceApplication() {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<InsuranceApplication> criteriaQuery = criteriaBuilder.createQuery(InsuranceApplication.class);
        Root<InsuranceApplication> root = criteriaQuery.from(InsuranceApplication.class);
        criteriaQuery.select(root);

        List<InsuranceApplication> applications = session.createQuery(criteriaQuery).list();

        session.close();
        return applications;
    }

    public List<InsuranceApplication> listInsuranceApplication(int code) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<InsuranceApplication> criteriaQuery = criteriaBuilder.createQuery(InsuranceApplication.class);
        Root<InsuranceApplication> root = criteriaQuery.from(InsuranceApplication.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("sponsor"), code));

        List<InsuranceApplication> applications = session.createQuery(criteriaQuery).list();

        session.close();
        return applications;
    }

    public ResponseResult saveInsuranceApplicationToFisco(int fid, String content, int sponsor, int receiver, String sponsorSignature, int applicationType) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.InsuranceApplication insuranceApplicationContract = cn.edu.scut.sse.supply.contracts.InsuranceApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = insuranceApplicationContract.createApplication(BigInteger.valueOf(fid), content,
                BigInteger.valueOf(sponsor), BigInteger.valueOf(receiver), sponsorSignature,
                BigInteger.valueOf(applicationType)).send();
        List<cn.edu.scut.sse.supply.contracts.InsuranceApplication.CreateApplicationEventEventResponse> responseList = insuranceApplicationContract.getCreateApplicationEventEvents(receipt);
        if (responseList.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        cn.edu.scut.sse.supply.contracts.InsuranceApplication.CreateApplicationEventEventResponse response = responseList.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public ResponseResult receiveInsuranceApplicationToFisco(int fid, String receiverSignature) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.InsuranceApplication insuranceApplicationContract = cn.edu.scut.sse.supply.contracts.InsuranceApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = insuranceApplicationContract.receiveApplication(BigInteger.valueOf(fid), receiverSignature).send();
        List<cn.edu.scut.sse.supply.contracts.InsuranceApplication.ReceiveApplicationEventEventResponse> responseList = insuranceApplicationContract.getReceiveApplicationEventEvents(receipt);
        if (responseList.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        cn.edu.scut.sse.supply.contracts.InsuranceApplication.ReceiveApplicationEventEventResponse response = responseList.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public ResponseResult updateInsuranceApplicationStatusToFisco(int fid, String status) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.InsuranceApplication insuranceApplicationContract = cn.edu.scut.sse.supply.contracts.InsuranceApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = insuranceApplicationContract.updateApplicationStatus(BigInteger.valueOf(fid), status).send();
        List<cn.edu.scut.sse.supply.contracts.InsuranceApplication.UpdateApplicationStatusEventEventResponse> responseList = insuranceApplicationContract.getUpdateApplicationStatusEventEvents(receipt);
        if (responseList.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得服务器返回消息");
        }
        cn.edu.scut.sse.supply.contracts.InsuranceApplication.UpdateApplicationStatusEventEventResponse response = responseList.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public DetailInsuranceApplicationVO getInsuranceApplicationFromFisco(int fid) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.InsuranceApplication insuranceApplicationContract = cn.edu.scut.sse.supply.contracts.InsuranceApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = insuranceApplicationContract.getApplication(BigInteger.valueOf(fid)).send();
        List<cn.edu.scut.sse.supply.contracts.InsuranceApplication.GetApplicationCallbackEventResponse> responseList = insuranceApplicationContract.getGetApplicationCallbackEvents(receipt);
        if (responseList.size() == 0) {
            return null;
        }
        cn.edu.scut.sse.supply.contracts.InsuranceApplication.GetApplicationCallbackEventResponse response = responseList.get(0);
        DetailInsuranceApplicationVO vo = new DetailInsuranceApplicationVO();
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

    public InsuranceApplicationStatusVO getInsuranceApplicationStatus(int fid) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        cn.edu.scut.sse.supply.contracts.InsuranceApplication insuranceApplicationContract = cn.edu.scut.sse.supply.contracts.InsuranceApplication.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = insuranceApplicationContract.getApplicationStatus(BigInteger.valueOf(fid)).send();
        List<cn.edu.scut.sse.supply.contracts.InsuranceApplication.GetApplicationStatusCallbackEventResponse> responseList = insuranceApplicationContract.getGetApplicationStatusCallbackEvents(receipt);
        if (responseList.size() == 0) {
            return null;
        }
        cn.edu.scut.sse.supply.contracts.InsuranceApplication.GetApplicationStatusCallbackEventResponse response = responseList.get(0);
        InsuranceApplicationStatusVO vo = new InsuranceApplicationStatusVO();
        vo.setReceiverSignature(response.receiverSignature);
        vo.setStatus(response.status);
        return vo;
    }

}
