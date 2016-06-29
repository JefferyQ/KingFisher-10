package org.kingfisher.common.model;

// TODO: 29.06.2016 добавить полное описание
public class RestServiceOperationKey {
    private String method;
    private String url;

    public RestServiceOperationKey() {
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{method=%s, url=%s}",
                this.getClass().getName(),
                this.method,
                this.url
        );
    }
}
