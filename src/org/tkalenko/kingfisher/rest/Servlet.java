package org.tkalenko.kingfisher.rest;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.tkalenko.kingfisher.common.RestException;

public class Servlet {

	private final Set<Service> services;

	public Servlet(final Collection<Service> services) {
		this.services = new HashSet<Service>();
		for(Service service : services){
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
		System.out.println(request.getRequest() + "#" + service.getPath());
		//2-надо определить опрерацию
		return null;
	}

	private Service getService(final String requestPath) {
		for (Service service : this.services) {
			if (requestPath.startsWith(service.getPath())) {
				return service;
			}
		}
		throw RestException.getEx(String.format(
				"can't find service by path={%1$s}", requestPath));
	}
}
