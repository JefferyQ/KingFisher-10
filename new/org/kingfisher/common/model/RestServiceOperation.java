package org.kingfisher.common.model;

// TODO: 29.06.2016 добавить логику и полное описание
// TODO: 30.06.2016 пересмотреть политику по toString
public class RestServiceOperation extends BaseRestServiceEntity {
    private String method;
    private String serviceId;
    private BodyEntity paremeters;
    private BodyEntity result;
    private BodyEntity entity;

    public RestServiceOperation() {
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    public String getServiceId() {
        return this.serviceId;
    }

    public void setServiceId(final String serviceId) {
        this.serviceId = serviceId;
    }

    public BodyEntity getParemeters() {
        return this.paremeters;
    }

    public void setParemeters(final BodyEntity paremeters) {
        this.paremeters = paremeters;
    }

    public BodyEntity getResult() {
        return this.result;
    }

    public void setResult(final BodyEntity result) {
        this.result = result;
    }

    public BodyEntity getEntity() {
        return this.entity;
    }

    public void setEntity(final BodyEntity entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{id=%s, url=%s, method=%s, serviceId=%s, paremeters=%s, result=%s, entity=%s}",
                this.getClass().getName(),
                this.id,
                this.url,
                this.method,
                this.serviceId,
                this.paremeters,
                this.result,
                this.entity
        );
    }
}
