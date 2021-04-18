package ru.maximenko.service;

import lombok.extern.slf4j.Slf4j;
import ru.maximenko.dao.*;
import ru.maximenko.entity.*;
import ru.maximenko.entity.dto.DocInputDto;
import ru.maximenko.entity.dto.DocArrivalProductDto;
import ru.maximenko.entity.dto.DocMoveDto;
import ru.maximenko.entity.dto.ProductLeftDto;

import java.util.*;

@Slf4j
public class DocService {
    private final DocArrivalHeadDao docArrivalHeadDao = new DocArrivalHeadDao();
    private final DocSaleHeadDao docSaleHeadDao = new DocSaleHeadDao();
    private final DocProductDao docProductDao = new DocProductDao();
    private final StorageDao storageDao = new StorageDao();
    private final ProductDao productDao = new ProductDao();

    //Добавление документа о поступлении продукта
    public String addDocArrival(DocInputDto docInputDto){

        if (docArrivalHeadDao.findByItem(docInputDto.getItem()) != null) return "Документ с таким номером уже существует";

        Storage storage = storageDao.findByName(docInputDto.getStorageName());
        if (storage==null) return "Склад не найден";

        DocArrivalHead docArrivalHead = new DocArrivalHead();
        docArrivalHead.setItem(docInputDto.getItem());
        docArrivalHead.setStorage(storage);

        List<DocProduct> docProducts = new LinkedList<>();

        for(DocArrivalProductDto d: docInputDto.getDocArrivalProducts()){
            DocProduct docProduct = new DocProduct();
            docProduct.setDocArrivalHead(docArrivalHead);
            docProduct.setPrice(d.getPrice());
            docProduct.setQuantity(d.getQuantity());
            docProduct.setDoctype("Arrival");
            Product product = productDao.findByArticle(d.getArticle());
            if (product == null) return "Продукт с атикулом " + d.getArticle() + " не существует !";
            product.setLastPurchasePrice(d.getPrice());

            productDao.updateProduct(product);

            docProduct.setProduct(product);
            docProducts.add(docProduct);
        }
        docArrivalHeadDao.addDocArrivalHead(docArrivalHead);
        docProductDao.addDocArrivalProducts(docProducts);

        return "Success";
    }

    //Добавление документа об удалении продукта
    public String addDocSale(DocInputDto docInputDto){
        log.info("start");

        if (docSaleHeadDao.findByItem(docInputDto.getItem()) != null) return "Документ с таким номером уже существует";

        Storage storage = storageDao.findByName(docInputDto.getStorageName());

        if (storage==null) return "Склад не найден";

        //Проверка наличия товара на складах

        //Берем документацию по указанному складу
        List<DocProduct> docProductList = docProductDao.findByStorage(docInputDto.getStorageName());

        if (docProductList.size() == 0) return "Скад пуст";

        DocSaleHead docSaleHead = new DocSaleHead();
        docSaleHead.setItem(docInputDto.getItem());
        docSaleHead.setStorage(storage);

        List<DocProduct> docProducts = new LinkedList<>();

        List<DocArrivalProductDto> docArrivalProductDtos = docInputDto.getDocArrivalProducts();

        docArrivalProductDtos.sort(Comparator.comparing(DocArrivalProductDto::getArticle));

        ListIterator<DocArrivalProductDto> docArrivalProductDtoListIterator = docArrivalProductDtos.listIterator();

        LinkedList<DocArrivalProductDto> docArrivalProductDtosSorted = new LinkedList<>();

        String article = "";

        while (docArrivalProductDtoListIterator.hasNext()) {
            DocArrivalProductDto docArrivalProductDto = docArrivalProductDtoListIterator.next();
            if (article.equals(docArrivalProductDto.getArticle())){
                docArrivalProductDtosSorted.getLast().setQuantity(docArrivalProductDtosSorted.getLast().getQuantity() + docArrivalProductDto.getQuantity());
            } else {
                article = docArrivalProductDto.getArticle();
                docArrivalProductDtosSorted.add(docArrivalProductDto);
            }
        }


        for(DocArrivalProductDto dap: docArrivalProductDtosSorted){
            Long quantity = 0L;
            for(DocProduct dp: docProductList){
                if (dap.getArticle().equals(dp.getProduct().getArticle())) {
                    if (dp.getDoctype().equals("Arrival")) {
                        quantity+=dp.getQuantity();
                        log.info("1");
                    }
                    if (dp.getDoctype().equals("Sale")) {
                        quantity-=dp.getQuantity();
                        log.info("2");
                    }
                }
            }
            if (quantity < dap.getQuantity()) {
                //Не найден товар для продажи
                return "Артикул :(" + dap.getArticle() + ") Отсутствует на складе";
            } else {
                DocProduct docProduct = new DocProduct();
                docProduct.setDocSaleHead(docSaleHead);
                docProduct.setPrice(dap.getPrice());
                docProduct.setQuantity(dap.getQuantity());
                docProduct.setDoctype("Sale");
                Product product = productDao.findByArticle(dap.getArticle());
                product.setLastSalePrice(dap.getPrice());

                productDao.updateProduct(product);

                docProduct.setProduct(product);
                docProducts.add(docProduct);
            }
        }
        try {
            docSaleHeadDao.addDocSaleHead(docSaleHead);
        } catch (IllegalArgumentException e ) {
            return "Документ с этим номером уже существует";
        }

        docProductDao.addDocArrivalProducts(docProducts);

        log.info("end");
        return "Success";
    }

    public String addDocMove(DocMoveDto docMoveDto){

        Storage storageFrom = storageDao.findByName(docMoveDto.getStorageFromName());
        Storage storageTo = storageDao.findByName(docMoveDto.getStorageToName());

        if (storageFrom == null) return "Склад продажи товара не найден";
        if (storageTo == null) return "Склад поступления товара не найден";

        DocInputDto docSaleDto = new DocInputDto(docMoveDto.getId(),
                docMoveDto.getItem(),
                docMoveDto.getStorageFromName(),
                docMoveDto.getDocArrivalProducts()
                );
        String out = addDocSale(docSaleDto);
        if (!out.equals("Success")) {
            return out;
        }

        DocInputDto docArriveDto = new DocInputDto(docMoveDto.getId(),
                docMoveDto.getItem(),
                docMoveDto.getStorageToName(),
                docMoveDto.getDocArrivalProducts()
        );

        out = addDocArrival(docArriveDto);

        return out;
    }

    public LinkedList<ProductLeftDto> findAllProductsOnStorage() {

        log.info("start");

        ListIterator<DocProduct> productLeftDtoListIterator = docProductDao.getAllDocArrivalProduct().listIterator();

        LinkedList<ProductLeftDto> productLeftDtos = new LinkedList<>();

        //Подсчет количества продуктов

        String lastArticle = "";

        log.info(productLeftDtoListIterator.toString());

        while (productLeftDtoListIterator.hasNext()){

            DocProduct docProduct = productLeftDtoListIterator.next();

            if (lastArticle.equals(docProduct.getProduct().getArticle())){
                if (docProduct.getDoctype().equals("Arrival")) {
                    productLeftDtos.getLast().setQuantity(productLeftDtos.getLast().getQuantity() + docProduct.getQuantity());
                }
                if (docProduct.getDoctype().equals("Sale")) {
                    productLeftDtos.getLast().setQuantity(productLeftDtos.getLast().getQuantity() - docProduct.getQuantity());
                }
            } else {
                lastArticle = docProduct.getProduct().getArticle();
                productLeftDtos.add(new ProductLeftDto(
                        docProduct.getProduct().getArticle(),
                        docProduct.getProduct().getName(),
                        docProduct.getQuantity()
                ));
            }
        }

        LinkedList<ProductLeftDto> productLeftDtos1 = new LinkedList<>();

        for (ProductLeftDto pld: productLeftDtos){
            if (pld.getQuantity() > 0) {
                productLeftDtos1.add(pld);
            }
        }

        return productLeftDtos1;
    }

}
