package ru.maximenko.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ru.maximenko.service.DocService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetAllProductsHandler extends MainHandler implements HttpHandler

    {
        private final DocService docService = new DocService();

        @Override
        public void handle(HttpExchange exchange) throws IOException {

        try {
            final Headers headers = exchange.getResponseHeaders();
            final String requestMethod = exchange.getRequestMethod().toUpperCase();
            switch (requestMethod) {
                case METHOD_GET:
                    final Map<String, List<String>> requestParameters = getRequestParameters(exchange.getRequestURI());

                    final String responseBody = new Gson().toJson(docService.findAllProductsOnStorage());

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
