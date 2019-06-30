package cn.edu.scut.sse.supply.bank.dao;

import cn.edu.scut.sse.supply.bank.entity.pojo.BankUser;
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
public class BankUserDAO {

    public void saveUser(BankUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUser(BankUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public BankUser getUserById(int uid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BankUser> criteriaQuery = criteriaBuilder.createQuery(BankUser.class);
        Root<BankUser> root = criteriaQuery.from(BankUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("uid"), uid));

        BankUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public BankUser getUserByName(String username) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BankUser> criteriaQuery = criteriaBuilder.createQuery(BankUser.class);
        Root<BankUser> root = criteriaQuery.from(BankUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));

        BankUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public BankUser getUserByToken(String token) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<BankUser> criteriaQuery = criteriaBuilder.createQuery(BankUser.class);
        Root<BankUser> root = criteriaQuery.from(BankUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("token"), token));

        BankUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

}
