package ru.maximenko.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;
import ru.maximenko.entity.Product;
import ru.maximenko.entity.Storage;
import ru.maximenko.json.JsonConverter;
import ru.maximenko.service.ProductService;
import ru.maximenko.service.StorageService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AddStorageHandler extends MainHandler implements HttpHandler {

    private final StorageService storageService = new StorageService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            final Headers headers = exchange.getResponseHeaders();
            final String requestMethod = exchange.getRequestMethod().toUpperCase();
            switch (requestMethod) {
                case METHOD_POST:
                    String responseBody = "Успешно";

                    try {
                        JSONObject jsonObject = new JsonConverter(exchange.getRequestBody()).getJsonObject();
                        responseBody = storageService.addStorage(new Gson().fromJson(jsonObject.toString(), Storage.class));
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
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }
}