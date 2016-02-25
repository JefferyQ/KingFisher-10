package org.tkalenko.kingfisher.rest.entity.operation;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.common.RestException;
import org.tkalenko.kingfisher.rest.Helper;
import org.tkalenko.kingfisher.rest.entity.RestEntity;

import java.util.Collection;
import java.util.Map;

public class Operation {

    private final String id;
    private final PathOperation path;
    private final String serviceId;
    private final HttpMethod method;
    private final Description pathParametersDescription;
    private final Description parametersDescription;
    //как обрабатывать корректно

    public Operation(final String id, final String path, final String serviceId, final String method, final RestEntity pathParametersDescription, final Description parametersDescription) {
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
        if (this.path.isWithPathParameters()) {
            if (pathParametersDescription == null)
                RestException.missing(String.format("path paremeters description for operation=%s", this.id));
            this.pathParametersDescription = new PathParametersDescription(pathParametersDescription);
        } else {
            this.pathParametersDescription = null;
        }
        this.parametersDescription = parametersDescription;
    }

    public Map<String, String> getPathParameters(final String requestPath) {
        return this.path.getPathParameters(requestPath);
    }

    public Collection<GetParameter> getGetParameters(final String requestPath){
        return this.path.getGetParameters(requestPath);
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
        return this.id;
    }

    public Description getPathParametersDescription() {
        return this.pathParametersDescription;
    }

    public Description getParametersDescription() {
        return this.parametersDescription;
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
