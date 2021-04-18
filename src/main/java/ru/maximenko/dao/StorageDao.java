package ru.maximenko.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.maximenko.configuration.HibernateSessionFactoryUtil;
import ru.maximenko.entity.Storage;


@Slf4j
public class StorageDao {

    public void addStorage(Storage storage){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(storage);
        }finally {
            transaction.commit();
            session.close();
        }
        log.info("end");
    }

    public Storage findByName(String name){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Storage storage = session.createNativeQuery("SELECT * from storages where name=:name", Storage.class)
                    .setParameter("name", name)
                    .getResultList()
                    .get(0);
            transaction.commit();
            session.close();
            return storage;
        } catch (Exception e){
            transaction.commit();
            session.close();
            return null;
        }
    }

}
