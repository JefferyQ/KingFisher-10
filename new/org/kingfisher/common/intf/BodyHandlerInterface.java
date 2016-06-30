package org.kingfisher.common.intf;

import org.kingfisher.common.model.*;

import java.util.Collection;

// TODO: 30.06.2016 добавить полное описание
public interface BodyHandlerInterface<T> {
    Object marshal(final Class<?> clazz, final MessageContext<T> requestCtx) throws RestHandlerException;

    // FIXME: 30.06.2016 на счет него не уверен до конца
    MessageContext<T> unmarshal(final Class<?> clazz, final Object response, final Collection<Cookie> cookies, final Collection<Header> headers) throws RestHandlerException;
}
