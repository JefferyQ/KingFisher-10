package org.kingfisher.common.intf;

import org.kingfisher.common.model.*;

import java.util.Collection;

// TODO: 30.06.2016 добавить полное описание
public interface BodyHandlerInterface<T> {
    Object marshal(final Class<?> clazz, final String type, final MessageContext<T> requestCtx) throws RestHandlerException;

    T unmarshal(final Class<?> clazz, final String type, final MessageContext resultCtx) throws RestHandlerException;
}
