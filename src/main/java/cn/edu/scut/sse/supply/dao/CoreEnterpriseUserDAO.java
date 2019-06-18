package cn.edu.scut.sse.supply.dao;

import cn.edu.scut.sse.supply.pojo.CoreEnterpriseUser;
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
public class CoreEnterpriseUserDAO {

    public void saveUser(CoreEnterpriseUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUser(CoreEnterpriseUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public CoreEnterpriseUser getUserById(int uid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CoreEnterpriseUser> criteriaQuery = criteriaBuilder.createQuery(CoreEnterpriseUser.class);
        Root<CoreEnterpriseUser> root = criteriaQuery.from(CoreEnterpriseUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("uid"), uid));

        CoreEnterpriseUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public CoreEnterpriseUser getUserByName(String username) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CoreEnterpriseUser> criteriaQuery = criteriaBuilder.createQuery(CoreEnterpriseUser.class);
        Root<CoreEnterpriseUser> root = criteriaQuery.from(CoreEnterpriseUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));

        CoreEnterpriseUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public CoreEnterpriseUser getUserByToken(String token) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<CoreEnterpriseUser> criteriaQuery = criteriaBuilder.createQuery(CoreEnterpriseUser.class);
        Root<CoreEnterpriseUser> root = criteriaQuery.from(CoreEnterpriseUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("token"), token));

        CoreEnterpriseUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

}
