package ru.maximenko.service;

import lombok.extern.slf4j.Slf4j;
import ru.maximenko.dao.ProductDao;
import ru.maximenko.entity.Product;

import java.util.Comparator;
import java.util.List;

@Slf4j
public class ProductService {

    private final ProductDao productDao = new ProductDao();


    public String addProduct(Product product){
        if (productDao.findByArticle(product.getArticle()) != null) return "Товар с заданным артикулом уже существует";
        productDao.addProduct(product);
        return "Успешно";
    }

    public List<Product> findAll() {
        List<Product> products = productDao.findAll();

        products.sort(Comparator.comparing(Product::getName));

        log.info(products.toString());

        return products;
    }



}
