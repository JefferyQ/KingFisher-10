package org.kingfisher.common.model;

import java.util.Date;

// TODO: 28.06.2016 добавить полное описание
// TODO: 29.06.2016 почитать про фабрики, и сделать опитимальнее без рефлексии
public class Cookie {
    private String name;
    private String value;
    private Date expires;
    private String path;
    private String domain;

    public Cookie() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public Date getExpires() {
        return this.expires;
    }

    public void setExpires(final Date expires) {
        this.expires = expires;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(final String domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{name=%s, value=%s, expires=%s, path=%s, domain=%s}",
                this.getClass().getName(),
                this.name,
                this.value,
                this.expires,
                this.path,
                this.domain
        );
    }
}
