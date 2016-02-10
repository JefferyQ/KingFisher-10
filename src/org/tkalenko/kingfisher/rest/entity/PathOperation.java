package org.tkalenko.kingfisher.rest.entity;

import org.tkalenko.kingfisher.common.RestException;
import org.tkalenko.kingfisher.rest.Helper;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tkalenko on 09.02.2016.
 */
public class PathOperation {
    private final String path;
    private final boolean withPathParameters;
    private final Pattern pathPattern;
    private final Collection<String> pathParameters;

    private static final Pattern PATH_PATTERN = Pattern.compile("(#)([/]{1}((#)|([\\{]{1}#[\\}]{1})))*".replace("#", Helper.COMMON_PATH_PATTERN));
    private static final Pattern PATH_PARAMETERS_PATTERN = Pattern.compile("([\\{]{1}#[\\}]{1})".replace("#", Helper.COMMON_PATH_PATTERN));

    public PathOperation(final String path) {
        if (path == null)
            throw RestException.missing("path");
        this.path = getPath(Helper.clearPath(path.trim(), '/'));
        this.withPathParameters = isWithPathParameters(this.path);
        this.pathParameters = getPathParameters(this.path, this.withPathParameters);
        this.pathPattern = getPathPattern(this.path, this.withPathParameters);
    }

    private Map<String, String> getPathParameters(final String requestPath) {
        if (requestPath == null)
            throw RestException.missing("request path");
        if (this.withPathParameters) {
            //TODO: подумать как это можно сдлеать
        }
        return null;
    }

    private Collection<String> getPathParameters(final String path, final boolean withPathParameters) {
        if (withPathParameters) {
            Collection<String> pathParameters = new ArrayList<String>();
            Matcher matcher = PATH_PARAMETERS_PATTERN.matcher(path);
            while (matcher.find()) {
                String parameter = matcher.group();
                if (pathParameters.contains(parameter))
                    throw RestException.getEx(String.format("in operation={%1$s} already exist path parameter={%2$s}", this, parameter));
                pathParameters.add(parameter);
            }
            return pathParameters;
        }
        return null;
    }

    private Pattern getPathPattern(final String path, final boolean withPathParameters) {
        String pattern = String.format("(%1$s)", path);
        if (withPathParameters) {
            pattern = pattern.replaceAll("[\\{]{1}#[\\}]{1}".replace("#", Helper.COMMON_PATH_PATTERN), "([^/]*)");
            if (true) {
                //TODO: подумать об этом позже
                String p = String.format("(%1$s)", path);
                Matcher matcher = PATH_PARAMETERS_PATTERN.matcher(path);
                while (matcher.find()) {
                    String parameter = matcher.group();
                    matcher.replaceFirst("TEST=" + parameter);
                    System.out.println(matcher);
                }
                StringBuilder builder = null;
//                for (String pathParameter : this.pathParameters) {
//                    p = p.replaceFirst("[\\{]{1}#[\\}]{1}".replace("#", Helper.COMMON_PATH_PATTERN), String.format("(?<%1$s>[^/]*)", pathParameter));
//                }
                System.out.println(p);
            }
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
