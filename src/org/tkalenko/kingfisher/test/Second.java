package org.tkalenko.kingfisher.test;

import org.tkalenko.kingfisher.rest.entity.RestEntity;

/**
 * Created by tkalenko on 25.02.2016.
 */
public class Second extends RestEntity {
    private String id;
    private String id_1;
    @Override
    public void setValue(final String name, final String value) {
        if (name.equals("id"))
            this.id = value;
        if (name.equals("id_1"))
            this.id_1 = value;
    }
}
