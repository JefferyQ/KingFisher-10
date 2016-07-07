package org.kingfisher.common.intf;

import org.kingfisher.common.model.*;

import java.util.Map;

// TODO: 29.06.2016 добавить полное описание
// TODO: 29.06.2016 подумать о возможных методах
public interface RestHandlerInterface<T> {

    RestMessageContext<T> handle(final String url, final String method, final RestMessageContext<T> requestCtx) throws RestHandlerException;

    void registerService(final RestService service) throws RestHandlerException;

    void registerOperation(final RestServiceOperation operation) throws RestHandlerException;

    RestService getService(final String url, final String method, final RestMessageContext<T> requestCtx) throws RestHandlerException;

    RestServiceOperation getOperation(final RestService service, final String url, final String method, final RestMessageContext<T> requestCtx) throws RestHandlerException;

    RestMessageContext createParamsCtx(final RestService service, final RestServiceOperation operation, final RestMessageContext<T> requestCtx) throws RestHandlerException;

    RestMessageContext handleOperation(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException;

    void interceptorsBefore(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException;

    void interceptorsAfter(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException;

    RestMessageContext<T> createResponseCtx(final RestService service, final RestServiceOperation operation, final RestMessageContext resultCtx) throws RestHandlerException;

    void validateOperation(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestValidationException;

    RestMessageContext<T> createResponseValidateCtx(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx) throws RestHandlerException;
}
