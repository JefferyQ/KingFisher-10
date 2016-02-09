package org.tkalenko.kingfisher.common;

public class RestException extends RuntimeException {

	public RestException(final String message) {
		super(message);
	}

	public static RestException getEx(final String message) throws RestException {
		return new RestException(message);
	}
	
	public static RestException missing(final String missing) throws RestException {
		return new RestException(String.format("missing %1$s", missing));
	}
}
