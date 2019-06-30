package cn.edu.scut.sse.supply.general.dao;

import cn.edu.scut.sse.supply.general.entity.pojo.Keystore;
import cn.edu.scut.sse.supply.util.SessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author Yukino Yukinoshita
 */

@Repository
public class KeystoreDAO {

    public Keystore getKeystore(int code) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactoryInstance();
        Session session = sessionFactory.openSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Keystore> criteriaQuery = criteriaBuilder.createQuery(Keystore.class);
        Root<Keystore> root = criteriaQuery.from(Keystore.class);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("code"), code));

        Keystore keystore = session.createQuery(criteriaQuery).uniqueResult();

        session.close();
        return keystore;
    }

    public String getPrivateKeyFromStorage(String filepath) throws Exception {
        File file = new File(filepath);
        if (!file.exists()) {
            return null;
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] content = new byte[150];
        fileInputStream.read(content);
        String result = new String(content).replaceAll("[\0]", "");
        fileInputStream.close();
        return result;
    }

}
