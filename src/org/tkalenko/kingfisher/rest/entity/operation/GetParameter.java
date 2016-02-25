package org.tkalenko.kingfisher.rest.entity.operation;

import org.tkalenko.kingfisher.rest.Helper;

/**
 * Created by tkalenko on 25.02.2016.
 */
public class GetParameter {
    private String id;
    private String value;

    public GetParameter(final String id, final String value) {
        Helper.validate(id, "id");
        Helper.validate(value, "value");
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }
}
