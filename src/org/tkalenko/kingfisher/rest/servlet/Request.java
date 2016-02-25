package org.tkalenko.kingfisher.rest.servlet;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.common.RestException;
import org.tkalenko.kingfisher.rest.Helper;

public class Request {

    private final HttpMethod method;
    private final String path;
    private final String body;

    public Request(final String method, final String request, final String body) {
        try {
            this.method = HttpMethod.valueOf(method.trim().toUpperCase());
        } catch (Throwable e) {
            throw RestException.getEx(String.format(
                    "unsupported HttpMethod=%1$s", method));
        }
        Helper.validate(request, "request path");
        this.path = Helper.clearPath(request.trim(), '/');
        this.body = body;
    }

    public String getPath() {
        return this.path;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getBody() {
        return this.body;
    }
}
