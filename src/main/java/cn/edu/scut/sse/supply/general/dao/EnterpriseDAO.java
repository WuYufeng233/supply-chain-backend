package cn.edu.scut.sse.supply.general.dao;

import cn.edu.scut.sse.supply.general.entity.pojo.Enterprise;
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
public class EnterpriseDAO {

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

}
