package org.tkalenko.kingfisher.rest.servlet;

import java.util.ArrayList;
import java.util.Collection;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.common.RestException;
import org.tkalenko.kingfisher.rest.Helper;
import org.tkalenko.kingfisher.rest.entity.Operation;
import org.tkalenko.kingfisher.rest.entity.Service;

public class Servlet {

    private final Collection<Service> services;

    public Servlet(final Collection<Service> services) {
        this.services = new ArrayList<Service>();
        for (Service service : services) {
            if (service == null) {
                throw RestException.missing("service");
            }
            if (this.services.contains(service)) {
                throw RestException.getEx(String.format("%1$s is already exist", service));
            }
            this.services.add(service);
        }
    }

    public Response handle(final Request request) {
        Helper.validate(request, "request");
        final Service service = getService(request.getPath());
        final Operation operation = getOperation(request.getPath(), request.getMethod(), service);
        operation.someTest(request.getPath());
        return null;
    }

    private Operation getOperation(final String requestPath, final HttpMethod method, final Service service) {
        if (requestPath != null && service != null) {
            Operation operation = service.getOperation(requestPath, method);
            if (operation != null)
                return operation;
        }
        throw RestException.getEx(String.format(
                "can't find operation in service={%1$s} by path={%2$s}", service, requestPath));
    }

    private Service getService(final String requestPath) {
        if (requestPath != null) {
            for (Service service : this.services) {
                if (service.isThisService(requestPath))
                    return service;
            }
        }
        throw RestException.getEx(String.format(
                "can't find service by path={%1$s}", requestPath));
    }
}
