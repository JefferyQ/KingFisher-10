package org.tkalenko.kingfisher.rest.entity;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.common.RestException;

public class Operation {

    private final String id;
    private final PathOperation path;
    private final String serviceId;
    private final HttpMethod method;

    public Operation(final String id, final String path, final String serviceId, final String method) {
        if (id == null)
            throw RestException.missing("id");
        if (path == null)
            throw RestException.missing("path");
        if (serviceId == null)
            throw RestException.missing("serviceId");
        this.id = id.trim();
        this.path = new PathOperation(path.trim());
        this.serviceId = serviceId.trim();
        try {
            this.method = HttpMethod.valueOf(method.trim().toUpperCase());
        } catch (Throwable e) {
            throw RestException.getEx(String.format(
                    "unsupported HttpMethod=%1$s", method));
        }
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
            if (this.method == operation.method){
                return isThisOperation(operation.getPath());
            }
        }
        return false;
    }

}
