package ru.maximenko;

import com.sun.net.httpserver.HttpServer;
import ru.maximenko.handlers.*;
import ru.maximenko.service.ProductService;

import java.io.*;
import java.net.InetSocketAddress;

public class StorageApplication {

    private static final String HOSTNAME = "localhost";
    private static final int PORT = 8080;
    private static final int BACKLOG = 1;

    private static final ProductService productService = new ProductService();

    public static void main(final String... args) throws IOException {

        productService.findAll();

        final HttpServer server = HttpServer.create(new InetSocketAddress(HOSTNAME, PORT), BACKLOG);
        server.createContext("/add/product", new AddProductHandler());
        server.createContext("/add/storage", new AddStorageHandler());
        server.createContext("/add/doc/arrival", new AddDocArrivalHandler());
        server.createContext("/add/doc/sale", new AddDocSaleHandler());
        server.createContext("/add/doc/move", new AddDocMoveHandler());
        server.createContext("/show/leftproducts", new GetAllProductsHandler());
        server.start();
    }


}
