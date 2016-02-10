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

        final Servlet servlet = new Servlet(getServices());
        for (Request request : getRequests()) {
            try {
                servlet.handle(request);
            } catch (Throwable throwable){
                System.out.println(throwable);
            }
        }
    }

    private static Collection<Request> getRequests() {
        Collection<Request> requests = new ArrayList<Request>();
        requests.add(new Request("get", "s1/op1"));
        requests.add(new Request("get", "s1/op2"));
        requests.add(new Request("get", "s1/op1/asd/aa"));
        requests.add(new Request("get", "s1/op1/asd/as/asdasdasd123123123"));
        return requests;
    }

    private static Collection<Service> getServices() {
        Collection<Service> services = new ArrayList<Service>();

        Service service = new Service("s1", "s1");
        service.addOperation(new Operation("op1", "op1", "s1", "get"));
        service.addOperation(new Operation("op2", "op1/op_1_1", "s1", "get"));
        service.addOperation(new Operation("op3", "op1/op_1_1/op_1_1", "s1", "get"));
        service.addOperation(new Operation("op4", "op1/{id}/aa", "s1", "get"));
        service.addOperation(new Operation("op5", "op1/{id}/as/{id_1}/", "s1", "get"));

        services.add(service);
        return services;
    }
}
