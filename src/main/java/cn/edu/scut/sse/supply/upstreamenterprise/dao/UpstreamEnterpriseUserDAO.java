package cn.edu.scut.sse.supply.upstreamenterprise.dao;

import cn.edu.scut.sse.supply.upstreamenterprise.entity.pojo.UpstreamEnterpriseUser;
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
public class UpstreamEnterpriseUserDAO {

    public void saveUser(UpstreamEnterpriseUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUser(UpstreamEnterpriseUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public UpstreamEnterpriseUser getUserById(int uid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UpstreamEnterpriseUser> criteriaQuery = criteriaBuilder.createQuery(UpstreamEnterpriseUser.class);
        Root<UpstreamEnterpriseUser> root = criteriaQuery.from(UpstreamEnterpriseUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("uid"), uid));

        UpstreamEnterpriseUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public UpstreamEnterpriseUser getUserByName(String username) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UpstreamEnterpriseUser> criteriaQuery = criteriaBuilder.createQuery(UpstreamEnterpriseUser.class);
        Root<UpstreamEnterpriseUser> root = criteriaQuery.from(UpstreamEnterpriseUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));

        UpstreamEnterpriseUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public UpstreamEnterpriseUser getUserByToken(String token) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UpstreamEnterpriseUser> criteriaQuery = criteriaBuilder.createQuery(UpstreamEnterpriseUser.class);
        Root<UpstreamEnterpriseUser> root = criteriaQuery.from(UpstreamEnterpriseUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("token"), token));

        UpstreamEnterpriseUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

}
