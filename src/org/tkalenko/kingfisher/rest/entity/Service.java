package org.tkalenko.kingfisher.rest.entity;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.common.RestException;
import org.tkalenko.kingfisher.rest.Helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class Service {

    private final String id;
    private final String path;
    private final Collection<Operation> operations;

    private static final Pattern PATH_PATTERN = Pattern.compile(Helper.COMMON_PATH_PATTERN);

    public Service(final String id, final String path) {
        if (id == null) {
            throw RestException.missing("id");
        }
        if (path == null) {
            throw RestException.missing("path");
        }
        this.id = id.trim();
        this.path = getPath(Helper.clearPath(path.trim(), '/')).concat("/");
        this.operations = new ArrayList<Operation>();
    }

    public boolean addOperation(final Operation operation) {
        if (operation != null) {
            if (!this.id.equals(operation.getServiceId()))
                throw RestException.getEx(String.format("operation={%1$s} is not for service={%2$s}", operation, this));
            for (Operation serviceOperation : this.operations) {
                if (operation.equals(serviceOperation)) {
                    throw RestException.getEx(String.format("same operations new_operation={%1$s} and service_operation={%2$s}", operation, serviceOperation));
                }
            }
            this.operations.add(operation);
            return true;
        }
        return false;
    }

    public Operation getOperation(final String requestPath, HttpMethod method) {
        if (requestPath != null && method != null) {
            String requestPathWithoutService = getPathWithoutService(requestPath);
            for (Operation operation : this.operations) {
                if (operation.isThisOperation(requestPathWithoutService))
                    return operation;
            }
        }
        return null;
    }

    private String getPath(final String path) {
        if (!PATH_PATTERN.matcher(path).matches()) {
            throw RestException
                    .getEx(String
                            .format("bad servlet path={%1$s}, example good servlet path's={service,service1,service_1}",
                                    path));
        }
        return path;
    }

    public String getPathWithoutService(final String requestPath) {
        return requestPath != null ? requestPath.replaceFirst(this.path, "") : null;
    }

    public boolean isThisService(final String requestPath) {
        return requestPath != null ? requestPath.startsWith(this.path) : false;
    }

    public String getId() {
        return this.id;
    }

    public String getPath() {
        return this.path;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Service other = (Service) obj;
        if (this.path.equals(other.getPath())) {
            return true;
        }
        if (this.id.equals(other.id)) {
            return true;
        }
        return false;
    }

}