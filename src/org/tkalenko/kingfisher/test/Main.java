package org.tkalenko.kingfisher.test;

import java.util.ArrayList;
import java.util.Collection;

import org.tkalenko.kingfisher.rest.entity.RestEntity;
import org.tkalenko.kingfisher.rest.entity.operation.Operation;
import org.tkalenko.kingfisher.rest.entity.service.Handler;
import org.tkalenko.kingfisher.rest.entity.service.Marshaller;
import org.tkalenko.kingfisher.rest.entity.service.Parameters;
import org.tkalenko.kingfisher.rest.servlet.Request;
import org.tkalenko.kingfisher.rest.entity.service.Service;
import org.tkalenko.kingfisher.rest.servlet.Servlet;

public class Main {
    public static void main(String[] args) {
        someTest();
        final Servlet servlet = new Servlet(getServices());
        for (Request request : getRequests()) {
            try {
                servlet.handle(request);
            } catch (Throwable throwable) {
                System.out.println(throwable);
            }
        }
    }

    private static void someTest() {
        //no-op
    }

    private static Collection<Request> getRequests() {
        Collection<Request> requests = new ArrayList<Request>();
        requests.add(new Request("get", "s1/op1", null));
        requests.add(new Request("get", "s1/op1?a=123&b=345", null));
        requests.add(new Request("get", "s1/op1/asd/aa", null));
        requests.add(new Request("get", "s1/op1/asd/as/asdasdasd123123123", null));
        return requests;
    }

    private static Collection<Service> getServices() {
        Collection<Service> services = new ArrayList<Service>();

        Service service = new Service("s1", "s1", getMarshaller(), getHandler());
        service.addOperation(new Operation("op1", "op1", "s1", "get", null, null));
        service.addOperation(new Operation("op2", "op1/op_1_1", "s1", "get", null, null));
        service.addOperation(new Operation("op3", "op1/op_1_1/op_1_1", "s1", "get", null, null));
        service.addOperation(new Operation("op4", "op1/{id}/aa", "s1", "get", new First(), null));
        service.addOperation(new Operation("op5", "op1/{id}/as/{id_1}", "s1", "get", new Second(), null));

        services.add(service);
        return services;
    }

    private static Handler getHandler() {
        return new Handler() {
            @Override
            public RestEntity handle(final Parameters parameters) throws Exception {
                return null;
            }
        };
    }

    private static Marshaller getMarshaller() {
        return new Marshaller() {
            @Override
            public RestEntity marshall(final String body, final RestEntity entity) {
                return null;
            }
        };
    }
}
