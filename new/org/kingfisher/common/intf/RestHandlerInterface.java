package org.kingfisher.common.intf;

import org.kingfisher.common.model.MessageContext;
import org.kingfisher.common.model.RestHandlerException;
import org.kingfisher.common.model.RestService;

// TODO: 29.06.2016 добавить полное описание
// TODO: 29.06.2016 подумать о возможных методах
public interface RestHandlerInterface<T> {

    MessageContext<T> handle(final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException;

    void registerService(final RestService service) throws RestHandlerException;

    RestService getService(final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException;
}
