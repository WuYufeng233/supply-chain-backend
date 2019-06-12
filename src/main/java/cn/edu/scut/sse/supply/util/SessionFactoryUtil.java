package cn.edu.scut.sse.supply.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Yukino Yukinoshita
 */

public class SessionFactoryUtil {

    private static volatile SessionFactory sessionFactoryInstance;

    public static SessionFactory getSessionFactoryInstance() {
        if (sessionFactoryInstance == null) {
            synchronized (SessionFactoryUtil.class) {
                if (sessionFactoryInstance == null) {
                    Configuration configuration = new Configuration().configure();
                    SessionFactoryUtil.sessionFactoryInstance = configuration.buildSessionFactory();
                }
            }
        }
        return sessionFactoryInstance;
    }

}
