package org.kingfisher.common.model;

// TODO: 29.06.2016 добавить полное описание
// TODO: 29.06.2016 почитать про фабрики, и сделать опитимальнее без рефлексии
public class BaseRestServiceEntity {
    protected String id;
    protected String url;
    protected String description;

    public BaseRestServiceEntity() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "BaseRestServiceEntity{id=%s, url=%s}",
                this.id,
                this.url
        );
    }
}
