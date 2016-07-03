package org.kingfisher.common.intf;

import org.kingfisher.common.model.MessageContext;
import org.kingfisher.common.model.RestHandlerException;
import org.kingfisher.common.model.RestService;
import org.kingfisher.common.model.RestServiceOperation;

// TODO: 29.06.2016 добавить полное описание
// TODO: 29.06.2016 подумать о возможных методах
public interface RestHandlerInterface<T> {

    MessageContext<T> handle(final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException;

    void registerService(final RestService service) throws RestHandlerException;

    void registerOperation(final RestServiceOperation operation) throws RestHandlerException;

    RestService getService(final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException;

    RestServiceOperation getOperation(final RestService service, final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException;

    MessageContext createParamsCtx(final RestService service, final RestServiceOperation operation, final MessageContext<T> requestCtx) throws RestHandlerException;

    MessageContext handleOperation(final RestService service, final RestServiceOperation operation, final MessageContext paramsCtx) throws RestHandlerException;

    MessageContext<T> createResponseCtx(final RestService service, final RestServiceOperation operation, final MessageContext resultCtx) throws RestHandlerException;
}
