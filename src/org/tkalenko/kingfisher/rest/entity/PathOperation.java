package org.tkalenko.kingfisher.rest.entity;

import jdk.nashorn.internal.runtime.regexp.joni.Matcher;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.tkalenko.kingfisher.common.RestException;

import java.util.regex.Pattern;

/**
 * Created by tkalenko on 09.02.2016.
 */
public class PathOperation {
    private final String path;
    private final boolean withPathParameters;

    public PathOperation(final String path) {
        if (path == null)
            throw RestException.missing("path");
        this.path = path;
        this.withPathParameters = false;
        getPath(path);
    }

    private String getPath(final String path) {
//        if (!path.matches("[a-zA-Z]{1}[a-zA-Z\\d_]*"))
//            throw RestException
//                    .getEx(String
//                            .format("bad servlet path={%1$s}, example good servlet path's={service,service1,service_1}",
//                                    path));
        String pattern = "([a-zA-Z]{1}[a-zA-Z\\d_]*)" + "(" + "[/]{1}" + "(([a-zA-Z]{1}[a-zA-Z\\d_]*)|[\\{]{1}([a-zA-Z]{1}[a-zA-Z\\d_]*)[\\}]{1})" + ")*";
        Pattern regex = Pattern.compile(pattern);
        System.out.println(path + " == " + regex.matcher(path).matches());
        return null;
    }

    public boolean isThisPath(final String path) {
        //TODO:добавить логику
        //TODO:немного усложняется так как хочу ввести понятние параметров пути тут, а это вносит некоторую сложность в код
        return false;
    }

    public String getPath() {
        return this.path;
    }
}
