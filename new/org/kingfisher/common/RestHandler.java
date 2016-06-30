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
public class RestHandler<T> implements RestHandlerInterface<T> {
    private Map<String, RestService> services = new HashMap<String, RestService>();

    @Override
    public MessageContext<T> handle(final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException {
        // TODO: 29.06.2016 добавить логику
        final RestService service = getService(url, method, requestCtx);
        final RestServiceOperation operation = getOperation(service, url, method, requestCtx);
        final MessageContext bodyCtx = createBodyCtx(service, operation, requestCtx);
        // TODO: 30.06.2016 надо придумать остальную всю логику есть первая часть
        return null;
    }

    @Override
    public MessageContext createBodyCtx(final RestService service, final RestServiceOperation operation, final MessageContext<T> requestCtx) throws RestHandlerException {
        if (service.getBodyHandler() == null)
            throw new RestHandlerException("%s don't contain BodyHandler");
        // TODO: 30.06.2016 добавить логику
        return null;
    }

    @Override
    public void registerService(final RestService service) throws RestHandlerException {
        if (service == null)
            throw new RestHandlerException("register null RestService");
        if (StringUtils.isBlank(service.getUrl()))
            throw new RestHandlerException(String.format("register %s with empty url", service));
        if (!RestHandlerHelper.validateUrl(service.getUrl()))
            throw new RestHandlerException(String.format("register %s with incorrect url", service));
        if (this.services.containsKey(service.getUrl()))
            throw new RestHandlerException(String.format("service with url=%s is already registered", service.getUrl()));
        this.services.put(service.getUrl(), service);
    }

    @Override
    public void registerOperation(final RestServiceOperation operation) throws RestHandlerException {
        if (operation == null)
            throw new RestHandlerException("register null RestServiceOperation");
        if (StringUtils.isBlank(operation.getServiceId()) || StringUtils.isBlank(operation.getUrl()) || StringUtils.isBlank(operation.getMethod()))
            throw new RestHandlerException(String.format("register %s with empty serviceId or url or method", operation));
        if (!RestHandlerHelper.validateUrl(operation.getUrl()))
            throw new RestHandlerException(String.format("register %s with incorrect url", operation));
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
