package cn.edu.scut.sse.supply.express.dao;

import cn.edu.scut.sse.supply.express.entity.pojo.ExpressUser;
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
public class ExpressUserDAO {

    public void saveUser(ExpressUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUser(ExpressUser user) {
        Session session = SessionFactoryUtil.getSessionFactoryInstance().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

    public ExpressUser getUserById(int uid) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpressUser> criteriaQuery = criteriaBuilder.createQuery(ExpressUser.class);
        Root<ExpressUser> root = criteriaQuery.from(ExpressUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("uid"), uid));

        ExpressUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public ExpressUser getUserByName(String username) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpressUser> criteriaQuery = criteriaBuilder.createQuery(ExpressUser.class);
        Root<ExpressUser> root = criteriaQuery.from(ExpressUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));

        ExpressUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

    public ExpressUser getUserByToken(String token) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<ExpressUser> criteriaQuery = criteriaBuilder.createQuery(ExpressUser.class);
        Root<ExpressUser> root = criteriaQuery.from(ExpressUser.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("token"), token));

        ExpressUser user = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return user;
    }

}
