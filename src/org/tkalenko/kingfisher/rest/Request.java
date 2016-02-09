package org.tkalenko.kingfisher.rest;

import org.tkalenko.kingfisher.common.HttpMethod;
import org.tkalenko.kingfisher.common.RestException;

public class Request {

	private final HttpMethod method;
	private final String request;

	public Request(final String method, final String request) {
		try {
			this.method = HttpMethod.valueOf(method.trim().toUpperCase());
		} catch (Throwable e) {
			throw RestException.getEx(String.format(
					"unsupported HttpMethod=%1$s", method));
		}
		if (request == null) {
			throw RestException.missing("request");
		}
		this.request = Helper.clearPath(request.trim(), '/');
	}

	public String getRequest() {
		return request;
	}

}
