package org.tkalenko.kingfisher.rest.entity;

import jdk.nashorn.internal.runtime.regexp.joni.Matcher;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.tkalenko.kingfisher.common.RestException;
import org.tkalenko.kingfisher.rest.Helper;

import java.util.regex.Pattern;

/**
 * Created by tkalenko on 09.02.2016.
 */
public class PathOperation {
    private final String path;
    private final boolean withPathParameters;
    private final Pattern pathPattern;

    private static final Pattern PATH_PATTERN = Pattern.compile("(#)([/]{1}((#)|([\\{]{1}#[\\}]{1})))*".replace("#", Helper.COMMON_PATH_PATTERN));

    public PathOperation(final String path) {
        if (path == null)
            throw RestException.missing("path");
        this.path = getPath(Helper.clearPath(path.trim(), '/'));
        this.withPathParameters = isWithPathParameters(this.path);
        this.pathPattern = getPathPattern(this.path, this.withPathParameters);
    }

    private Pattern getPathPattern(final String path, final boolean withPathParameters) {
        String pattern = String.format("(%1$s)", path);
        if (withPathParameters) {
            pattern = pattern.replaceAll("[\\{]{1}#[\\}]{1}".replace("#", Helper.COMMON_PATH_PATTERN), "([^/]*)");
        }
        pattern = pattern.concat("([\\?]{1}.*)?");
        return Pattern.compile(pattern);
    }

    private boolean isWithPathParameters(final String path) {
        return path != null ? path.contains("{") : false;
    }

    private String getPath(final String path) {
        if (!PATH_PATTERN.matcher(path).matches()) {
            throw RestException
                    .getEx(String
                            .format("bad operation path={%1$s}, example good operation path's={operation,operation1,operation2/{some_id}}",
                                    path));
        }
        return path;
    }

    public boolean isThisPath(final String path) {
        return this.pathPattern.matcher(path).matches();
    }

    public String getPath() {
        return this.path;
    }
}
