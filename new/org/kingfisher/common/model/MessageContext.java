package org.kingfisher.common.model;

import java.util.ArrayList;
import java.util.Collection;

// TODO: 28.06.2016 добавить полное описание
public class MessageContext<T> {
    private T body;
    private final Collection<Header> headers = new ArrayList<Header>();
    private final Collection<Cookie> cookies = new ArrayList<Cookie>();

    public MessageContext() {
    }

    public T getBody() {
        return this.body;
    }

    public void setBody(final T body) {
        this.body = body;
    }

    public Collection<Header> getHeaders() {
        return this.headers;
    }

    public Collection<Cookie> getCookies() {
        return this.cookies;
    }

    @Override
    public String toString() {
        String.format(
                "%s{body=%s, headers=%s, cookies=%s}",
                this.getClass().getName(),
                this.body,
                this.headers,
                this.cookies
        );
        return "MessageContext{" +
                "body=" + body +
                ", headers=" + headers +
                ", cookies=" + cookies +
                '}';
    }
}
