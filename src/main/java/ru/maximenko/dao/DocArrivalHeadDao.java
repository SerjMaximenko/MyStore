package ru.maximenko.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.maximenko.configuration.HibernateSessionFactoryUtil;
import ru.maximenko.entity.DocArrivalHead;

import java.util.List;

@Slf4j
public class DocArrivalHeadDao {

    public void addDocArrivalHead(DocArrivalHead docArrivalHead){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(docArrivalHead);
        transaction.commit();
        session.close();
        log.info("end");
    }

    public DocArrivalHead findByItem(String item){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            DocArrivalHead docArrivalHead = session.createNativeQuery("SELECT * from documents_arrival_head where item=:item", DocArrivalHead.class)
                    .setParameter("item", item)
                    .getResultList().get(0);
            transaction.commit();
            session.close();
            return docArrivalHead;
        } catch (Exception e){
            transaction.commit();
            session.close();
            return null;
        }
    }

}
