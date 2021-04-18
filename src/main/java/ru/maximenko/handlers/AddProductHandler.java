package ru.maximenko.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import ru.maximenko.entity.Product;
import ru.maximenko.json.JsonConverter;
import ru.maximenko.service.ProductService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddProductHandler extends MainHandler implements HttpHandler{

    private final ProductService productService = new ProductService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            final Headers headers = exchange.getResponseHeaders();
            final String requestMethod = exchange.getRequestMethod().toUpperCase();
            switch (requestMethod) {
                case METHOD_POST:
                    final Map<String, List<String>> requestParameters = getRequestParameters(exchange.getRequestURI());

                    System.out.println(exchange.getRequestURI().getQuery());

                    String responseBody = "Успешно";

                    try {
                        JSONObject jsonObject = new JsonConverter(exchange.getRequestBody()).getJsonObject();
                        responseBody = productService.addProduct(new Gson().fromJson(jsonObject.toString(), Product.class));
                    } catch (JSONException e) {
                        responseBody = "Некорректное тело запроса !";
                    }

                    headers.set(HEADER_CONTENT_TYPE, String.format("application/json; charset=%s", CHARSET));
                    final byte[] rawResponseBody = responseBody.getBytes(CHARSET);
                    exchange.sendResponseHeaders(STATUS, rawResponseBody.length);
                    exchange.getResponseBody().write(rawResponseBody);
                    break;
                default:
                    headers.set(HEADER_ALLOW, ALLOWED_METHODS);
                    exchange.sendResponseHeaders(STATUS_METHOD_NOT_ALLOWED, NO_RESPONSE_LENGTH);
                    break;
            }
        } finally {
            exchange.close();
        }
    }
}
