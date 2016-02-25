package org.tkalenko.kingfisher.rest.entity;

/**
 * Created by tkalenko on 25.02.2016.
 */
public abstract class RestEntity {

    public RestEntity() {
        //no-op
    }

    public abstract void setValue(final String name, final String value);
}
