package ru.maximenko.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import ru.maximenko.entity.*;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Product.class);
                configuration.addAnnotatedClass(Storage.class);
                configuration.addAnnotatedClass(DocArrivalHead.class);
                configuration.addAnnotatedClass(DocMovingHead.class);
                configuration.addAnnotatedClass(DocSaleHead.class);
                configuration.addAnnotatedClass(DocProduct.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e){
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}
