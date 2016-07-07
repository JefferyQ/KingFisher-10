package org.kingfisher.common;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

// TODO: 30.06.2016 добавить полное описание
public final class RestHandlerHelper {
    private static final String BASE_URL_REGEX = "/[a-zA-Z0-9]+";
    private static final String SPLIT_URL_REGEX = String.format("(?=%s)", BASE_URL_REGEX);
    private static final Pattern URL_PATTERN = Pattern.compile(String.format("(%s)+", BASE_URL_REGEX));

    public static final String PARAMETERS_BODY_ENTITY_TYPE = "parameters";
    public static final String ENTITY_BODY_ENTITY_TYPE = "entity";
    public static final String RESULT_BODY_ENTITY_TYPE = "result";

    public static final String VALIDATE_URL = "/validate";

    public static String clearEndOfUrl(final String baseUrl, final String clearUrl) {
        return StringUtils.removeEnd(baseUrl, clearUrl);
    }

    public static String clearStartOfUrl(final String baseUrl, final String clearUrl) {
        return StringUtils.removeStart(baseUrl, clearUrl);
    }

    public static boolean validateUrl(final String url) {
        return URL_PATTERN.matcher(url).matches();
    }

    public static String[] getChainUrls(final String baseUrl) {
        final String[] res = baseUrl.split(SPLIT_URL_REGEX);
        for (int i = 0; i < res.length; i++) {
            res[i] = i == 0 ? res[i] : res[i - 1].concat(res[i]);
        }
        return res;
    }
}
