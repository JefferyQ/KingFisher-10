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
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestServiceOperationKey key = (RestServiceOperationKey) o;
        if ((this.method == null || this.url == null) || (key.method == null || key.url == null)) return false;
        return this.method.equals(key.method) & this.url.equals(key.url);
    }

    @Override
    public int hashCode() {
        int res = this.method == null ? 0 : this.method.hashCode();
        res = 31 * res + this.url == null ? 0 : this.url.hashCode();
        return res;
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
