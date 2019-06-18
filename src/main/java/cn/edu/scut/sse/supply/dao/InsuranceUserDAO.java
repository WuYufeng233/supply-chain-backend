package cn.edu.scut.sse.supply.dao;

import cn.edu.scut.sse.supply.pojo.InsuranceUser;
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
public class InsuranceUserDAO {

    public void saveUser(InsuranceUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUser(InsuranceUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public InsuranceUser getUserById(int uid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<InsuranceUser> criteriaQuery = criteriaBuilder.createQuery(InsuranceUser.class);
        Root<InsuranceUser> root = criteriaQuery.from(InsuranceUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("uid"), uid));

        InsuranceUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public InsuranceUser getUserByName(String username) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<InsuranceUser> criteriaQuery = criteriaBuilder.createQuery(InsuranceUser.class);
        Root<InsuranceUser> root = criteriaQuery.from(InsuranceUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));

        InsuranceUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public InsuranceUser getUserByToken(String token) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<InsuranceUser> criteriaQuery = criteriaBuilder.createQuery(InsuranceUser.class);
        Root<InsuranceUser> root = criteriaQuery.from(InsuranceUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("token"), token));

        InsuranceUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

}
