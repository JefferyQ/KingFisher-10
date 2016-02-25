package org.tkalenko.kingfisher.rest.entity.operation;

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
        Helper.validate(path, "path");
        this.path = getPath(Helper.clearPath(path.trim(), '/'));
        this.withPathParameters = isHasPathParameters();
        this.pathParameters = getPathParameters();
        this.pathPattern = getPathPattern();
    }

    private boolean isHasPathParameters() {
        return isHasPathParameters(this.path);
    }

    private Collection<String> getPathParameters() {
        return getPathParameters(this.path, this.withPathParameters);
    }

    private Pattern getPathPattern() {
        return getPathPattern(this.path, this.withPathParameters, this.pathParameters);
    }

    public Map<String, String> getPathParameters(final String requestPath) {
        Helper.validate(requestPath, "request path");
        if (!this.withPathParameters)
            return null;
        Matcher matcher = this.pathPattern.matcher(requestPath);
        Map<String, String> res = new LinkedHashMap<String, String>();
        matcher.find();
        int i = 1;
        for (Iterator<String> iterator = this.pathParameters.iterator(); iterator.hasNext(); ) {
            String parameter = iterator.next();
            res.put(parameter, matcher.group(++i));
        }
        return res;
    }

    public Collection<GetParameter> getGetParameters(final String requestPath) {
        Helper.validate(requestPath, "request path");
        int startGetRequest = requestPath.indexOf('?');
        if (startGetRequest < 0)
            return null;
        Collection<GetParameter> res = new ArrayList<GetParameter>();
        String[] parameters = requestPath.substring(startGetRequest + 1, requestPath.length()).split("&");
        for (String parameter : parameters) {
            String[] params = parameter.split("=");
            res.add(new GetParameter(params[0], params[1]));
        }
        return res;
    }

    private Collection<String> getPathParameters(final String path, final boolean withPathParameters) {
        if (!withPathParameters)
            return null;
        Collection<String> parameters = new ArrayList<String>();
        StringBuilder builder = new StringBuilder(path);
        for (int i = 0; i < builder.length(); i++) {
            char c = builder.charAt(i);
            if (c == '{') {
                for (int j = ++i; j < builder.length(); j++) {
                    char ci = builder.charAt(j);
                    if (ci == '}') {
                        String parameter = builder.substring(i, j);
                        if (parameters.contains(parameter))
                            throw RestException.getEx(String.format("path_parameter=%1$s already exist in path=%2$s", path, parameter));
                        parameters.add(parameter);
                        i = j;
                        break;
                    }
                }
            }
        }
        return parameters;
    }

    private Pattern getPathPattern(final String path, final boolean withPathParameters, final Collection<String> pathParameters) {
        String pattern = String.format("(%1$s)", path);
        if (withPathParameters) {
            for (String pathParameter : pathParameters) {
                pattern = pattern.replace(String.format("{%s}", pathParameter), "(?:([^/]*))");
            }
        }
        pattern = pattern.concat("([\\?]{1}.*)?");
        return Pattern.compile(pattern);
    }

    private boolean isHasPathParameters(final String path) {
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

    public boolean isWithPathParameters() {
        return this.withPathParameters;
    }
}
