package ru.maximenko.handlers;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface HandlerConfig {

    String HEADER_ALLOW = "Allow";
    String HEADER_CONTENT_TYPE = "Content-Type";

    Charset CHARSET = StandardCharsets.UTF_8;

    int STATUS = 200;
    int STATUS_METHOD_NOT_ALLOWED = 405;

    int NO_RESPONSE_LENGTH = -1;

    String METHOD_GET = "GET";
    String METHOD_POST = "POST";
    String METHOD_OPTIONS = "OPTIONS";
    String ALLOWED_METHODS = METHOD_GET + "," + METHOD_OPTIONS;

}
