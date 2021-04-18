package ru.maximenko.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.maximenko.configuration.HibernateSessionFactoryUtil;
import ru.maximenko.entity.DocSaleHead;

@Slf4j
public class DocSaleHeadDao {

    public void addDocSaleHead(DocSaleHead docSaleHead){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(docSaleHead);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.commit();
            session.close();
            throw new IllegalArgumentException();
        }
        log.info("end");
    }

    public DocSaleHead findByItem(String item){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            DocSaleHead docSaleHead = session.createNativeQuery("SELECT * from documents_sale_head where item=:item", DocSaleHead.class)
                    .setParameter("item", item)
                    .getResultList().get(0);
            transaction.commit();
            session.close();
            return docSaleHead;
        } catch (Exception e){
            transaction.commit();
            session.close();
            return null;
        }
    }

}
