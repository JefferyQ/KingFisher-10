package org.tkalenko.kingfisher.rest;

public final class Helper {

	public static String clearPath(final String path, char delete) {
		StringBuilder builder = new StringBuilder(path);
		if (builder.charAt(0) == delete) {
			builder.deleteCharAt(0);
		}
		if (builder.charAt(builder.length() - 1) == delete) {
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}
}
