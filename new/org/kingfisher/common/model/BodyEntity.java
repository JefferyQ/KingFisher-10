package org.kingfisher.common.model;

// TODO: 30.06.2016 добавить полное описание
public class BodyEntity {
    private Class<?> clazz;
    private boolean required = true;

    public BodyEntity() {
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public void setClazz(final Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    @Override
    public String toString() {
        return String.format(
                "BodyEntity{clazz=%s, required=%s}",
                this.clazz,
                this.required
        );
    }
}
