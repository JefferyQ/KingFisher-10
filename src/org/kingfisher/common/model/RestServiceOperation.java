package org.kingfisher.common.model;

// TODO: 29.06.2016 добавить логику и полное описание
// TODO: 30.06.2016 пересмотреть политику по toString
public class RestServiceOperation extends BaseRestServiceEntity {
    private String method;
    private String serviceId;
    private BodyEntity paremeters;
    private BodyEntity result;
    private BodyEntity entity;
    private String handler;
    private String validator;
    private boolean auth = true;

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

    public String getHandler() {
        return this.handler;
    }

    public void setHandler(final String handler) {
        this.handler = handler;
    }

    public boolean isAuth() {
        return this.auth;
    }

    public void setAuth(final boolean auth) {
        this.auth = auth;
    }

    public String getValidator() {
        return this.validator;
    }

    public void setValidator(final String validator) {
        this.validator = validator;
    }

    @Override
    public String toString() {
        return String.format(
                "RestServiceOperation{serviceId=%s, id=%s, url=%s, method=%s}",
                this.serviceId,
                this.id,
                this.url,
                this.method
        );
    }
}
