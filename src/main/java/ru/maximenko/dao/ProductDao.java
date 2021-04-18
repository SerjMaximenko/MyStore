package ru.maximenko.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.maximenko.configuration.HibernateSessionFactoryUtil;
import ru.maximenko.entity.Product;

import java.util.List;

@Slf4j
public class ProductDao {

    public void addProduct(Product product){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(product);
        } finally {
            transaction.commit();
            session.close();
        }
        log.info("end");
    }

    public void updateProduct(Product product){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(product);
        } finally {
            transaction.commit();
            session.close();
        }
        log.info("end");
    }

    public Product findByArticle(String article){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            Product product = session.createNativeQuery("SELECT * from products where article=:article", Product.class)
                    .setParameter("article", article)
                    .getResultList().get(0);
            transaction.commit();
            session.close();
            return product;
        } catch (Exception e){
            transaction.commit();
            session.close();
            return null;
        }
    }

    public List<Product> findAll(){
        log.info("start");
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
        List<Product> products = session.createNativeQuery("select * from products", Product.class).getResultList();
        transaction.commit();
        session.close();
        return products;
        } catch (Exception e){
            transaction.commit();
            session.close();
            return null;
        }
    }


}
