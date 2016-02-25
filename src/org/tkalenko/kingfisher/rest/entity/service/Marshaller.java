package org.tkalenko.kingfisher.rest.entity.service;

import org.tkalenko.kingfisher.common.RestException;
import org.tkalenko.kingfisher.rest.Helper;
import org.tkalenko.kingfisher.rest.entity.RestEntity;
import org.tkalenko.kingfisher.rest.entity.operation.GetParameter;

import java.util.Collection;
import java.util.Map;

/**
 * Created by tkalenko on 25.02.2016.
 */
public abstract class Marshaller {

    public RestEntity marshall(Map<String, String> parameters, Class<? extends RestEntity> clazz) throws IllegalAccessException, InstantiationException {
        Helper.validate(parameters, "path parameters from request");
        Helper.validate(clazz, "class of path parameters");
        if (parameters.isEmpty())
            throw RestException.missing("path parameters");
        RestEntity entity = clazz.newInstance();
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            entity.setValue(entry.getKey(), entry.getValue());
        }
        return entity;
    }

    public RestEntity marshall(Collection<GetParameter> parameters, Class<? extends RestEntity> clazz) throws IllegalAccessException, InstantiationException {
        Helper.validate(clazz, "class of get parameters");
        if (parameters == null || parameters.isEmpty())
            return null;
        RestEntity entity = clazz.newInstance();
        for (GetParameter parameter : parameters) {
            entity.setValue(parameter.getId(), parameter.getValue());
        }
        return entity;
    }

    public RestEntity marshall(final String body, Class<? extends RestEntity> clazz) throws IllegalAccessException, InstantiationException {
        Helper.validate(clazz, "class of parameters");
        if (body == null)
            return null;
        return marshall(body, clazz.newInstance());
    }

    public abstract RestEntity marshall(final String body, RestEntity entity);
}
