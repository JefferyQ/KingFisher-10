package org.tkalenko.kingfisher.test;

import java.util.ArrayList;
import java.util.Collection;

import org.tkalenko.kingfisher.rest.Request;
import org.tkalenko.kingfisher.rest.Service;
import org.tkalenko.kingfisher.rest.Servlet;

public class Main {
	public static void main(String[] args) {
		final Servlet servlet = new Servlet(getServices());
		for (Request request : getRequests()) {
			servlet.handle(request);
		}
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
