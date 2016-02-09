package org.tkalenko.kingfisher.rest.servlet;

import java.util.ArrayList;
import java.util.Collection;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.common.RestException;
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
        if (request == null) {
            throw RestException.missing("request");
        }
        final Service service = getService(request.getRequest());
        final Operation operation = getOperation(request.getRequest(), request.getMethod(), service);
        return null;
    }

    private Operation getOperation(final String requestPath, final HttpMethod method, final Service service) {
        if (requestPath != null && service != null) {
            return service.getOperation(requestPath, method);
        }
        throw RestException.getEx(String.format(
                "can't find operation in service={%2$s} by path={%2$s}", service, requestPath));
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
