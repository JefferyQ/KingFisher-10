package org.tkalenko.kingfisher.test;

import org.tkalenko.kingfisher.rest.entity.RestEntity;

/**
 * Created by tkalenko on 25.02.2016.
 */
public class First extends RestEntity {
    private String id;

    @Override
    public void setValue(final String name, final String value) {
        this.id = value;
    }
}
