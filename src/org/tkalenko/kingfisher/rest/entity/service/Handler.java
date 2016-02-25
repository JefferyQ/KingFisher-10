package org.tkalenko.kingfisher.rest.entity.service;

import org.tkalenko.kingfisher.rest.entity.RestEntity;

/**
 * Created by tkalenko on 25.02.2016.
 */
public abstract class Handler {

    public Handler() {
        //no-op
    }

    public abstract RestEntity handle(final Parameters parameters) throws Exception;
}
