package org.tkalenko.kingfisher.rest;

import org.tkalenko.kingfisher.common.RestException;

public class Service {

	private final String id;
	private final String path;

	public Service(final String id, final String path) {
		if (id == null) {
			throw RestException.missing("id");
		}
		if (path == null) {
			throw RestException.missing("path");
		}
		this.id = id;
		this.path = getPath(Helper.clearPath(path.trim(), '/')).concat("/");
	}

	private String getPath(final String path) {
		if (!path.matches("[a-zA-Z]{1}[a-zA-Z\\d_]*")) {
			throw RestException
					.getEx(String
							.format("bad servlet path={%1$s}, example good servlet path's={service,service1,service_1}",
									path));
		}
		return path;
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
