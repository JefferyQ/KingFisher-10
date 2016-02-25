package org.tkalenko.kingfisher.rest.entity.operation;

import org.tkalenko.kingfisher.rest.Helper;
import org.tkalenko.kingfisher.rest.entity.RestEntity;

/**
 * Created by tkalenko on 25.02.2016.
 */
public class Description {
    private final boolean option;
    private final RestEntity entity;

    public Description(final boolean option, final RestEntity entity) {
        Helper.validate(option, "option");
        Helper.validate(entity, "entity");
        this.option = option;
        this.entity = entity;
    }

    public boolean isOption() {
        return this.option;
    }

    public RestEntity getEntity() {
        return this.entity;
    }
}
