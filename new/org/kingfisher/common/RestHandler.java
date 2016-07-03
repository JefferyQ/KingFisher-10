package org.kingfisher.common;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.kingfisher.common.intf.RestHandlerInterface;
import org.kingfisher.common.model.*;

import java.util.HashMap;
import java.util.Map;

// TODO: 29.06.2016 добавить полное описание
// TODO: 29.06.2016 подумать о возможных методах
// TODO: 29.06.2016 подумать о логике в целом
// TODO: 03.07.2016 протестировать первую часть
public class RestHandler<T> implements RestHandlerInterface<T> {
    private Map<String, RestService> services = new HashMap<String, RestService>();

    @Override
    public MessageContext<T> handle(final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException {
        final RestService service = getService(url, method, requestCtx);
        final RestServiceOperation operation = getOperation(service, url, method, requestCtx);
        final MessageContext paramsCtx = createParamsCtx(service, operation, requestCtx);
        final MessageContext resultCtx = handleOperation(service, operation, paramsCtx);
        return createResponseCtx(service, operation, resultCtx);
    }

    @Override
    public MessageContext<T> createResponseCtx(final RestService service, final RestServiceOperation operation, final MessageContext resultCtx) throws RestHandlerException {
        BodyEntity response = null;
        String type = null;
        if (operation.getResult() != null) {
            response = operation.getResult();
            type = RestHandlerHelper.RESULT_BODY_ENTITY_TYPE;
        } else if (operation.getEntity() != null) {
            response = operation.getEntity();
            type = RestHandlerHelper.ENTITY_BODY_ENTITY_TYPE;
        }
        if (resultCtx.getBody() == null) {
            if (response != null && response.isRequired())
                throw new IllegalArgumentException(String.format("%s required result", operation));
        }
        MessageContext<T> res = new MessageContext<T>();
        res.getCookies().addAll(resultCtx.getCookies());
        res.getHeaders().addAll(resultCtx.getHeaders());
        if (response != null && resultCtx != null)
            res.setBody((T) service.getBodyHandler().unmarshal(response.getClazz(), type, resultCtx));
        return res;
    }

    @Override
    public MessageContext handleOperation(final RestService service, final RestServiceOperation operation, final MessageContext paramsCtx) throws RestHandlerException {
        return service.getOperationHandler().handle(operation.getHandler(), paramsCtx);
    }

    @Override
    public MessageContext createParamsCtx(final RestService service, final RestServiceOperation operation, final MessageContext<T> requestCtx) throws RestHandlerException {
        BodyEntity params = null;
        String type = null;
        if (operation.getParemeters() != null) {
            params = operation.getParemeters();
            type = RestHandlerHelper.PARAMETERS_BODY_ENTITY_TYPE;
        } else if (operation.getEntity() != null) {
            params = operation.getEntity();
            type = RestHandlerHelper.ENTITY_BODY_ENTITY_TYPE;
        }
        if (requestCtx.getBody() == null) {
            if (params != null && params.isRequired())
                throw new IllegalArgumentException(String.format("%s required request", operation));
        }
        MessageContext res = new MessageContext();
        res.getCookies().addAll(requestCtx.getCookies());
        res.getHeaders().addAll(requestCtx.getHeaders());
        if (params != null && requestCtx.getBody() != null)
            res.setBody(service.getBodyHandler().marshal(params.getClazz(), type, requestCtx));
        return res;
    }

    @Override
    public void registerService(final RestService service) throws RestHandlerException {
        if (service == null)
            throw new RestHandlerException("register null RestService");

        if (StringUtils.isBlank(service.getUrl()))
            throw new RestHandlerException(String.format("register %s with empty url", service));
        if (!RestHandlerHelper.validateUrl(service.getUrl()))
            throw new RestHandlerException(String.format("register %s with incorrect url", service));

        if (service.getBodyHandler() == null)
            throw new RestHandlerException("register %s don't contain BodyHandler");
        if (service.getOperationHandler() == null)
            throw new RestHandlerException(String.format("register %s don't contain OperationHandler"));

        if (this.services.containsKey(service.getUrl()))
            throw new RestHandlerException(String.format("service with url=%s is already registered", service.getUrl()));

        this.services.put(service.getUrl(), service);
    }

    @Override
    public void registerOperation(final RestServiceOperation operation) throws RestHandlerException {
        if (operation == null)
            throw new RestHandlerException("register null RestServiceOperation");

        if (StringUtils.isBlank(operation.getServiceId()) ||
                StringUtils.isBlank(operation.getUrl()) ||
                StringUtils.isBlank(operation.getMethod()) ||
                StringUtils.isBlank(operation.getHandler()))
            throw new RestHandlerException(String.format("register %s with empty serviceId or url or method or handler", operation));

        if (!RestHandlerHelper.validateUrl(operation.getUrl()))
            throw new RestHandlerException(String.format("register %s with incorrect url", operation));

        if (operation.getParemeters() != null && operation.getParemeters().getClazz() == null)
            throw new RestHandlerException(String.format("register %s don't contain clazz in parameters", operation));
        if (operation.getEntity() != null && operation.getEntity().getClazz() == null)
            throw new RestHandlerException(String.format("register %s don't contain clazz in entity", operation));
        if (operation.getResult() != null && operation.getResult().getClazz() == null)
            throw new RestHandlerException(String.format("register %s don't contain clazz in result", operation));
        if ((operation.getParemeters() != null || operation.getResult() != null) && operation.getEntity() != null)
            throw new RestHandlerException(String.format("register %s can't contain entity and parameters or(and) result at the same time", operation));

        RestService service = null;
        for (RestService restService : this.services.values()) {
            if (operation.getServiceId().equals(restService.getId()))
                service = restService;
        }
        if (service == null)
            throw new RestHandlerException(String.format("register %s can't find RestService by id=%s", operation, operation.getServiceId()));

        RestServiceOperationKey key = new RestServiceOperationKey();
        key.setMethod(operation.getMethod());
        key.setUrl(operation.getUrl());
        if (service.getOperations().containsKey(key))
            throw new RestHandlerException(String.format("operation with method=%s and url=%s already registered in %s", operation.getMethod(), operation.getUrl(), service));

        service.getOperations().put(key, operation);
    }

    @Override
    public RestService getService(final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException {
        RestService res;
        for (String chainUrl : RestHandlerHelper.getChainUrls(url)) {
            res = this.services.get(chainUrl);
            if (res != null)
                return res;
        }
        throw new RestHandlerException(String.format("can't find service by url=%s", url));
    }

    @Override
    public RestServiceOperation getOperation(final RestService service, final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException {
        RestServiceOperationKey key = new RestServiceOperationKey();
        key.setMethod(method);
        key.setUrl(RestHandlerHelper.clearUrl(url, service.getUrl()));
        RestServiceOperation res = service.getOperations().get(key);
        if (res == null)
            throw new RestHandlerException(String.format("can't find operation in %s by url=%s and method=%s", service, url, method));
        return res;
    }
}
