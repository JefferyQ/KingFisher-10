package org.kingfisher.common.intf;

import org.kingfisher.common.model.*;

// TODO: 30.06.2016 добавить полное описание
public interface BodyHandlerInterface<T> {
    Object marshal(final Class<?> clazz, final String type, final RestMessageContext<T> requestCtx) throws RestHandlerException;

    T unmarshal(final Class<?> clazz, final String type, final RestMessageContext resultCtx) throws RestHandlerException;

    T unmarshalValidation(final Class<?> clazz, final String type, final RestMessageContext paramsCtx) throws RestHandlerException;
}
