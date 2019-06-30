package cn.edu.scut.sse.supply.general.dao;

import cn.edu.scut.sse.supply.general.entity.pojo.Enterprise;
import cn.edu.scut.sse.supply.general.entity.pojo.TokenTransactionRecord;
import cn.edu.scut.sse.supply.general.entity.vo.ResponseResult;
import cn.edu.scut.sse.supply.general.entity.vo.TransactionRecordVO;
import cn.edu.scut.sse.supply.util.ContractUtil;
import cn.edu.scut.sse.supply.util.EnterpriseUtil;
import cn.edu.scut.sse.supply.util.SessionFactoryUtil;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yukino Yukinoshita
 */

@Repository
public class EnterpriseDAO {

    private BigInteger gasPrice = new BigInteger("300000000");
    private BigInteger gasLimit = new BigInteger("300000000");
    private Credentials credentials = Credentials.create("87f9a16a679c1ba680f7876f45782e44054f9e0662d310068490551452f89460");
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

    public ResponseResult saveTokenTransaction(String transactionHash, int payer, int payee, BigInteger value) {
        return saveTokenTransaction(transactionHash, payer, payee, value, 0, 0);
    }

    public ResponseResult saveTokenTransaction(String transactionHash, int payer, int payee, BigInteger value, int type, int id) {
        TokenTransactionRecord record = new TokenTransactionRecord();
        record.setTransactionHash(transactionHash);
        record.setValue(value.longValue());
        record.setPayer(payer);
        record.setPayee(payee);
        record.setBindingType(type);
        record.setBindingId(id);
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(record);
        session.getTransaction().commit();
        session.close();
        return new ResponseResult().setCode(0).setMsg("记录保存成功").setData(record);
    }

    public List<TransactionRecordVO> listTransactionRecord(int code) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<TokenTransactionRecord> criteriaQuery = criteriaBuilder.createQuery(TokenTransactionRecord.class);
        Root<TokenTransactionRecord> root = criteriaQuery.from(TokenTransactionRecord.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.or(
                criteriaBuilder.equal(root.get("payer"), code),
                criteriaBuilder.equal(root.get("payee"), code)));

        List<TokenTransactionRecord> records = session.createQuery(criteriaQuery).list();
        List<TransactionRecordVO> vos = records.stream()
                .map(tokenTransactionRecord -> {
                    TransactionRecordVO vo = new TransactionRecordVO();
                    vo.setId(tokenTransactionRecord.getId());
                    vo.setTransactionHash(tokenTransactionRecord.getTransactionHash());
                    vo.setFid(tokenTransactionRecord.getBindingId());
                    vo.setValue(BigInteger.valueOf(tokenTransactionRecord.getValue()));
                    String payer = EnterpriseUtil.getEnterpriseNameByCode(tokenTransactionRecord.getPayer());
                    if (payer == null) {
                        payer = getEnterpriseByCode(tokenTransactionRecord.getPayer()).getName();
                        EnterpriseUtil.putCodeName(tokenTransactionRecord.getPayer(), payer);
                    }
                    vo.setPayer(payer);
                    String payee = EnterpriseUtil.getEnterpriseNameByCode(tokenTransactionRecord.getPayee());
                    if (payee == null) {
                        payee = getEnterpriseByCode(tokenTransactionRecord.getPayee()).getName();
                        EnterpriseUtil.putCodeName(tokenTransactionRecord.getPayee(), payee);
                    }
                    vo.setPayee(payee);
                    String type;
                    switch (tokenTransactionRecord.getBindingType()) {
                        case 0:
                            type = "未绑定";
                            break;
                        case 1:
                            type = "合同";
                            break;
                        default:
                            type = "未知类型";
                    }
                    vo.setType(type);
                    return vo;
                }).collect(Collectors.toList());
        session.close();
        return vos;
    }

}
