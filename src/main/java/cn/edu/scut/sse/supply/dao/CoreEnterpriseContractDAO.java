package cn.edu.scut.sse.supply.dao;

import cn.edu.scut.sse.supply.pojo.CoreEnterpriseContract;
import cn.edu.scut.sse.supply.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author Yukino Yukinoshita
 */

@Repository
public class CoreEnterpriseContractDAO {

    public void saveContract(CoreEnterpriseContract contract) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(contract);
        session.getTransaction().commit();
        session.close();
    }

    public void updateContract(CoreEnterpriseContract contract) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.update(contract);
        session.getTransaction().commit();
        session.close();
    }

    public CoreEnterpriseContract getContract(int fid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CoreEnterpriseContract> criteriaQuery = criteriaBuilder.createQuery(CoreEnterpriseContract.class);
        Root<CoreEnterpriseContract> root = criteriaQuery.from(CoreEnterpriseContract.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("fid"), fid));

        CoreEnterpriseContract contract = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return contract;
    }

    public List<CoreEnterpriseContract> listContract() {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CoreEnterpriseContract> criteriaQuery = criteriaBuilder.createQuery(CoreEnterpriseContract.class);
        Root<CoreEnterpriseContract> root = criteriaQuery.from(CoreEnterpriseContract.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.notEqual(root.get("receiver"), 0));

        List<CoreEnterpriseContract> contracts = session.createQuery(criteriaQuery).list();

        session.close();
        return contracts;
    }

    public List<CoreEnterpriseContract> listRecycleContract() {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CoreEnterpriseContract> criteriaQuery = criteriaBuilder.createQuery(CoreEnterpriseContract.class);
        Root<CoreEnterpriseContract> root = criteriaQuery.from(CoreEnterpriseContract.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("receiver"), 0));

        List<CoreEnterpriseContract> contracts = session.createQuery(criteriaQuery).list();

        session.close();
        return contracts;
    }

}
