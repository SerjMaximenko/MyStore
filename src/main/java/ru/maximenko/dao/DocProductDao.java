package ru.maximenko.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.maximenko.configuration.HibernateSessionFactoryUtil;
import ru.maximenko.entity.DocProduct;

import java.util.List;
@Slf4j
public class DocProductDao {

    public void addDocArrivalProduct(DocProduct docProduct){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(docProduct);
        transaction.commit();
        session.close();
        log.info("end");
    }

    public void addDocArrivalProducts(List<DocProduct> docProducts){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        for (DocProduct d: docProducts){
            log.info(d.toString());
            session.save(d);
        }
        transaction.commit();
        session.close();
        log.info("end");
    }

    public List<DocProduct> getAllDocArrivalProduct(){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<DocProduct> docProducts = session.createNativeQuery("select * from documents_product ORDER BY product_id", DocProduct.class).getResultList();
        transaction.commit();
        session.close();
        //log.info(docArrivalProducts.toString());
        log.info("end");
        return docProducts;
    }

    public List<DocProduct> findByStorage(String storageName){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        List<DocProduct> docProducts = session.createNativeQuery(
                "(select * from documents_product JOIN documents_arrival_head dah on documents_product.doc_arrival_head_id = dah.id " +
                        "JOIN storages s on dah.storage_id = s.id where s.name =:name) \n" +
                        "union\n" +
                        "(select * from documents_product JOIN documents_sale_head dsh on documents_product.doc_sale_head_id = dsh.id " +
                        "JOIN storages s on dsh.storage_id = s.id where s.name =:name) ", DocProduct.class)
                .setParameter("name", storageName)
                .getResultList();
        transaction.commit();
        session.close();
        log.info("end");
        return docProducts;
    }
}
