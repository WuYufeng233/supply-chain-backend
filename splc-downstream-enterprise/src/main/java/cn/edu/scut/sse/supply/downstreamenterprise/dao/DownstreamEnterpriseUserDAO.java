package cn.edu.scut.sse.supply.downstreamenterprise.dao;

import cn.edu.scut.sse.supply.downstreamenterprise.entity.pojo.DownstreamEnterpriseUser;
import cn.edu.scut.sse.supply.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * @author Yukino Yukinoshita
 */

@Repository
public class DownstreamEnterpriseUserDAO {

    public void saveUser(DownstreamEnterpriseUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUser(DownstreamEnterpriseUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public DownstreamEnterpriseUser getUserById(int uid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<DownstreamEnterpriseUser> criteriaQuery = criteriaBuilder.createQuery(DownstreamEnterpriseUser.class);
        Root<DownstreamEnterpriseUser> root = criteriaQuery.from(DownstreamEnterpriseUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("uid"), uid));

        DownstreamEnterpriseUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public DownstreamEnterpriseUser getUserByName(String username) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<DownstreamEnterpriseUser> criteriaQuery = criteriaBuilder.createQuery(DownstreamEnterpriseUser.class);
        Root<DownstreamEnterpriseUser> root = criteriaQuery.from(DownstreamEnterpriseUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));

        DownstreamEnterpriseUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public DownstreamEnterpriseUser getUserByToken(String token) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<DownstreamEnterpriseUser> criteriaQuery = criteriaBuilder.createQuery(DownstreamEnterpriseUser.class);
        Root<DownstreamEnterpriseUser> root = criteriaQuery.from(DownstreamEnterpriseUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("token"), token));

        DownstreamEnterpriseUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

}
