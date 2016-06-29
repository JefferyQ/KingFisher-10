package org.kingfisher.common.model;

// TODO: 29.06.2016 добавить полное описание
// TODO: 29.06.2016 почитать про фабрики, и сделать опитимальнее без рефлексии
public class BaseRestServiceEntity {
    protected String id;
    protected String url;

    public BaseRestServiceEntity() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{id=%s, url=%s}",
                this.getClass().getName(),
                this.id,
                this.url
        );
    }
}
