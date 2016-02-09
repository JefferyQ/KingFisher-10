package org.tkalenko.kingfisher.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.rest.entity.Operation;
import org.tkalenko.kingfisher.rest.servlet.Request;
import org.tkalenko.kingfisher.rest.entity.Service;
import org.tkalenko.kingfisher.rest.servlet.Servlet;

public class Main {
    public static void main(String[] args) {
        someTest();
        final Servlet servlet = new Servlet(getServices());
        for (Request request : getRequests()) {
            servlet.handle(request);
        }
    }

    private static void someTest() {
        Service service = new Service("s1", "s1");
        service.addOperation(new Operation("op1", "op1", "s1", "get"));
        service.addOperation(new Operation("op2", "op1/op_1_1", "s1", "get"));
        service.addOperation(new Operation("op3", "op1/op_1_1/op_1_1", "s1", "get"));
        service.addOperation(new Operation("op4", "op1/{id}/aa", "s1", "get"));
        service.addOperation(new Operation("op5", "op1/{id}/as/{id_1}/", "s1", "get"));

        System.out.println(service.getOperation("op1", HttpMethod.GET));
        System.out.println(service.getOperation("op2", HttpMethod.GET));
        System.out.println(service.getOperation("op1/asd/aa", HttpMethod.GET));
        System.out.println(service.getOperation("op1/asd/as/asdasdasd123123123", HttpMethod.GET));
    }

    private static Collection<Request> getRequests() {
        Collection<Request> requests = new ArrayList<Request>();
        requests.add(new Request("get", "s1/p1"));
        requests.add(new Request("get", "s1/p1"));
        requests.add(new Request("get", "s2/p1"));
        requests.add(new Request("get", "s4_/p1"));
        return requests;
    }

    private static Collection<Service> getServices() {
        Collection<Service> services = new ArrayList<Service>();
        services.add(new Service("s1", "s1/"));
        services.add(new Service("s2", "/s2/"));
        services.add(new Service("s3", "/s3"));
        services.add(new Service("s4", "s4_/"));
        return services;
    }
}
