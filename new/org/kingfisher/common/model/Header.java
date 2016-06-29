package org.kingfisher.common.model;

// TODO: 28.06.2016 добавить полное описание
// TODO: 29.06.2016 почитать про фабрики, и сделать опитимальнее без рефлексии
public class Header {
    protected String name;
    protected String value;

    public Header() {
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

    @Override
    public String toString() {
        return String.format(
                "%s{name=%s, value=%s}",
                this.getClass().getName(),
                this.name,
                this.value
        );
    }
}
