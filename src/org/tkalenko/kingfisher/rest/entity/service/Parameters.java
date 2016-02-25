package org.tkalenko.kingfisher.rest.entity.service;

import org.tkalenko.kingfisher.rest.entity.RestEntity;

/**
 * Created by tkalenko on 25.02.2016.
 */
public class Parameters {
    private RestEntity pathParameters;
    private RestEntity parameters;

    public Parameters() {
        //no-op
    }

    public RestEntity getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(final RestEntity pathParameters) {
        this.pathParameters = pathParameters;
    }

    public RestEntity getParameters() {
        return parameters;
    }

    public void setParameters(final RestEntity parameters) {
        this.parameters = parameters;
    }
}
