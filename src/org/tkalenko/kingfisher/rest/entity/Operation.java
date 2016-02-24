package org.tkalenko.kingfisher.rest.entity;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.common.RestException;
import org.tkalenko.kingfisher.rest.Helper;

public class Operation {

    private final String id;
    private final PathOperation path;
    private final String serviceId;
    private final HttpMethod method;

    public Operation(final String id, final String path, final String serviceId, final String method) {
        Helper.validate(id, "id");
        Helper.validate(id, "path");
        Helper.validate(id, "serviceId");
        this.id = id.trim();
        this.path = new PathOperation(path);
        this.serviceId = serviceId.trim();
        try {
            this.method = HttpMethod.valueOf(method.trim().toUpperCase());
        } catch (Throwable e) {
            throw RestException.getEx(String.format(
                    "unsupported HttpMethod=%1$s", method));
        }
    }

    public void someTest(final String requestPath) {
        // TODO: 24.02.2016 Сделать получение параметров запросов и гет параметров
        System.out.println(requestPath);
        System.out.println(this.path.getPathParameters(requestPath));
        if (this.method == HttpMethod.GET)
            System.out.println(this.path.getGetParameters(requestPath));
    }

    public boolean isThisOperation(final String path) {
        return this.path.isThisPath(path);
    }

    public boolean isThisOperation(final String requestPath, final HttpMethod method) {
        return method == this.method ? isThisOperation(requestPath) : false;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public String getPath() {
        return this.path.getPath();
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        if (this.serviceId.equals(operation.serviceId)) {
            if (this.id.equals(operation.id))
                return true;
            if (this.method == operation.method) {
                return isThisOperation(operation.getPath());
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "id='" + id + '\'' +
                '}';
    }
}
