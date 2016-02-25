package org.tkalenko.kingfisher.rest.entity.service;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.common.RestException;
import org.tkalenko.kingfisher.rest.Helper;
import org.tkalenko.kingfisher.rest.entity.RestEntity;
import org.tkalenko.kingfisher.rest.entity.operation.Operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Service {

    private final String id;
    private final String path;
    private final Map<HttpMethod, Collection<Operation>> operations;
    private final Marshaller marshaller;
    private final Handler handler;

    private static final Pattern PATH_PATTERN = Pattern.compile(Helper.COMMON_PATH_PATTERN);

    public Service(final String id, final String path, final Marshaller marshaller, final Handler handler) {
        Helper.validate(id, "id");
        Helper.validate(path, "path");
        Helper.validate(marshaller, "marshaller");
        Helper.validate(handler, "handler");
        this.id = id.trim();
        this.path = getPath(Helper.clearPath(path.trim(), '/')).concat("/");
        this.operations = new HashMap<HttpMethod, Collection<Operation>>();
        this.marshaller = marshaller;
        this.handler = handler;
    }

    public Parameters getParameters(final Operation operation, final String requestPath, final String requestBody) throws IllegalAccessException, InstantiationException {
        Helper.validate(operation, "service operation");
        Parameters parameters = new Parameters();
        if (operation.getPathParametersDescription() != null) {
            parameters.setPathParameters(this.marshaller.marshall(operation.getPathParameters(requestPath), operation.getPathParametersDescription().getEntity().getClass()));
        }
        if (operation.getParametersDescription() != null) {
            Class<? extends RestEntity> clazz = operation.getParametersDescription().getEntity().getClass();
            RestEntity parametersEntity = operation.getMethod() == HttpMethod.GET ? this.marshaller.marshall(operation.getGetParameters(requestPath), clazz) : null;
            if (parametersEntity == null && !operation.getParametersDescription().isOption())
                RestException.getEx("empty parameters");
            parameters.setParameters(parametersEntity);
        }
        return parameters;
    }

    public RestEntity handle(final Parameters parameters) throws Exception {
        Helper.validate(parameters, "parameters");
        return this.handler.handle(parameters);
    }

    public boolean addOperation(final Operation operation) {
        if (operation != null) {
            if (!this.id.equals(operation.getServiceId()))
                throw RestException.getEx(String.format("operation={%1$s} is not for service={%2$s}", operation, this));
            Collection<Operation> operations = this.operations.get(operation.getMethod());
            if (operations == null) {
                operations = new ArrayList<Operation>();
                this.operations.put(operation.getMethod(), operations);
            }
            for (Operation serviceOperation : operations) {
                if (serviceOperation.equals(operation)) {
                    throw RestException.getEx(String.format("same operations new_operation={%1$s} and service_operation={%2$s}", operation, serviceOperation));
                }
            }
            operations.add(operation);
            return true;
        }
        return false;
    }

    public Operation getOperation(final String requestPath, HttpMethod method) {
        if (requestPath != null && method != null) {
            Collection<Operation> operations = this.operations.get(method);
            if (operations == null)
                return null;
            String requestPathWithoutService = getPathWithoutService(requestPath);
            for (Operation operation : operations) {
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
