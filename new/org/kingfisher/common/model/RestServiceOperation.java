package org.kingfisher.common.model;

// TODO: 29.06.2016 добавить логику и полное описание
public class RestServiceOperation extends BaseRestServiceEntity {
    private String method;

    public RestServiceOperation() {
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{id=%s, url=%s, method=%s}",
                this.getClass().getName(),
                this.id,
                this.url,
                this.method
        );
    }
}
