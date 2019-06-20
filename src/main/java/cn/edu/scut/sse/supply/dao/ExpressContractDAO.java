package cn.edu.scut.sse.supply.dao;

import cn.edu.scut.sse.supply.contracts.ContractRepo;
import cn.edu.scut.sse.supply.pojo.ExpressContract;
import cn.edu.scut.sse.supply.pojo.vo.DetailContractVO;
import cn.edu.scut.sse.supply.pojo.vo.ResponseResult;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author Yukino Yukinoshita
 */

@Repository
public class ExpressContractDAO {

    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("b33405550c96ef5ae7d7d9a6b323fa739277bb469546db96c1e2e5690ea871fe");
    private String address = "0x1fb61ea047b115a0942a2b6220016b935c90ee8f";

    public void saveContract(ExpressContract contract) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(contract);
        session.getTransaction().commit();
        session.close();
    }

    public void updateContract(ExpressContract contract) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.update(contract);
        session.getTransaction().commit();
        session.close();
    }

    public ExpressContract getContract(int fid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpressContract> criteriaQuery = criteriaBuilder.createQuery(ExpressContract.class);
        Root<ExpressContract> root = criteriaQuery.from(ExpressContract.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("fid"), fid));

        ExpressContract contract = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return contract;
    }

    public List<ExpressContract> listEnableContract() {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpressContract> criteriaQuery = criteriaBuilder.createQuery(ExpressContract.class);
        Root<ExpressContract> root = criteriaQuery.from(ExpressContract.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.notEqual(root.get("receiver"), 0));

        List<ExpressContract> contracts = session.createQuery(criteriaQuery).list();

        session.close();
        return contracts;
    }

    public List<ExpressContract> listRecycleContract() {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpressContract> criteriaQuery = criteriaBuilder.createQuery(ExpressContract.class);
        Root<ExpressContract> root = criteriaQuery.from(ExpressContract.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("receiver"), 0));

        List<ExpressContract> contracts = session.createQuery(criteriaQuery).list();

        session.close();
        return contracts;
    }

    public DetailContractVO getContractFromFisco(int fid) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        ContractRepo contractRepo = ContractRepo.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));
        TransactionReceipt receipt = contractRepo.getContract(BigInteger.valueOf(fid)).send();
        List<ContractRepo.GetContractCallbackEventResponse> list = contractRepo.getGetContractCallbackEvents(receipt);
        if (list.size() == 0) {
            return null;
        }
        ContractRepo.GetContractCallbackEventResponse response = list.get(0);
        DetailContractVO vo = new DetailContractVO();
        vo.setContractId(fid);
        vo.setHash(response.hash);
        vo.setSponsor(response.sponsor.toString());
        vo.setReceiver(response.receiver.toString());
        vo.setSponsorSignature(response.sponsorSignature);
        vo.setReceiverSignature(response.receiverSignature);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        vo.setStartDate(format.format(new Date(response.startTime.longValue())));
        vo.setStatus(response.status.intValue());
        return vo;
    }

    public ResponseResult saveContractToFisco(ExpressContract expressContract, String sponsorSignature) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        ContractRepo contractRepo = ContractRepo.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        TransactionReceipt receipt = contractRepo.launchContract(BigInteger.valueOf(expressContract.getFid()),
                expressContract.getHash(), BigInteger.valueOf(expressContract.getSponsor()),
                BigInteger.valueOf(expressContract.getReceiver()), sponsorSignature).send();
        List<ContractRepo.LaunchContractEventEventResponse> list = contractRepo.getLaunchContractEventEvents(receipt);
        if (list.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得返回消息");
        }
        ContractRepo.LaunchContractEventEventResponse response = list.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

    public ResponseResult receiveContractToFisco(int fid, String signature) throws Exception {
        Web3j web3j = Web3jUtil.getWeb3j();
        ContractRepo contractRepo = ContractRepo.load(address, web3j, credentials, new StaticGasProvider(gasPrice, gasLimit));

        TransactionReceipt receipt = contractRepo.receiveContract(BigInteger.valueOf(fid), signature).send();
        List<ContractRepo.ReceiveContractEventEventResponse> list = contractRepo.getReceiveContractEventEvents(receipt);
        if (list.size() == 0) {
            return new ResponseResult().setCode(-6).setMsg("未获得返回消息");
        }
        ContractRepo.ReceiveContractEventEventResponse response = list.get(0);
        return new ResponseResult().setCode(response.code.intValue()).setMsg(response.msg);
    }

}
