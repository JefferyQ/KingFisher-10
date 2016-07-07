package org.kingfisher.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.kingfisher.common.intf.RestHandlerInterface;
import org.kingfisher.common.intf.RestInterceptorInterface;
import org.kingfisher.common.model.*;

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

// TODO: 29.06.2016 добавить полное описание
public class RestHandler<T> implements RestHandlerInterface<T> {
    private Map<String, RestService> services = new HashMap<String, RestService>();

    @Override
    public RestMessageContext<T> handle(final String url, final String method, final RestMessageContext<T> requestCtx) throws RestHandlerException {
        final RestService service = getService(url, method, requestCtx);
        final RestServiceOperation operation = getOperation(service, url, method, requestCtx);
        final RestMessageContext paramsCtx = createParamsCtx(service, operation, requestCtx);
        final Map<String, Object> data = new HashMap<String, Object>();

        RestMessageContext resultCtx = null;
        interceptorsBefore(service, operation, paramsCtx, data);
        try {
            if (StringUtils.endsWith(url, RestHandlerHelper.VALIDATE_URL)) {
                if (StringUtils.isEmpty(operation.getValidator()))
                    throw new RestHandlerException(String.format("%s don't contain validator", operation));
                validateOperation(service, operation, paramsCtx, data);
            } else {
                validateOperation(service, operation, paramsCtx, data);
                resultCtx = handleOperation(service, operation, paramsCtx, data);
            }
        } catch (final RestValidationException e) {
            interceptorsAfter(service, operation, paramsCtx, data);
            return createResponseValidateCtx(service, operation, paramsCtx);
        }
        interceptorsAfter(service, operation, resultCtx, data);

        return createResponseCtx(service, operation, resultCtx);
    }

    @Override
    public RestMessageContext<T> createResponseValidateCtx(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx) throws RestHandlerException {
        Pair<BodyEntity, String> pair = getParamsEntity(operation);
        RestMessageContext<T> res = new RestMessageContext<T>();
        res.getCookies().addAll(paramsCtx.getCookies());
        res.getCookies().addAll(paramsCtx.getHeaders());
        res.setBody((T) service.getBodyHandler().unmarshalValidation(pair.getLeft().getClazz(), pair.getRight(), paramsCtx));
        return res;
    }

    @Override
    public void validateOperation(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestValidationException {
        if (paramsCtx.getBody() == null || StringUtils.isEmpty(operation.getValidator()))
            return;
        service.getOperationHandler().validate(operation.getValidator(), paramsCtx, data);
    }

    @Override
    public RestMessageContext<T> createResponseCtx(final RestService service, final RestServiceOperation operation, final RestMessageContext resultCtx) throws RestHandlerException {
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
        RestMessageContext<T> res = new RestMessageContext<T>();
        res.getCookies().addAll(resultCtx.getCookies());
        res.getHeaders().addAll(resultCtx.getHeaders());
        if (response != null && resultCtx != null)
            res.setBody((T) service.getBodyHandler().unmarshal(response.getClazz(), type, resultCtx));
        return res;
    }

    @Override
    public void interceptorsAfter(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException {
        for (ListIterator<RestInterceptorInterface> iterator = service.getInterceptors().listIterator(service.getInterceptors().size()); iterator.hasPrevious(); ) {
            iterator.next().after(paramsCtx, data);
        }
    }

    @Override
    public RestMessageContext handleOperation(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException {
        return service.getOperationHandler().handle(operation.getHandler(), paramsCtx, data);
    }

    @Override
    public void interceptorsBefore(final RestService service, final RestServiceOperation operation, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException {
        for (ListIterator<RestInterceptorInterface> iterator = service.getInterceptors().listIterator(); iterator.hasNext(); ) {
            iterator.next().before(paramsCtx, data);
        }
    }

    public Pair<BodyEntity, String> getParamsEntity(final RestServiceOperation operation) {
        BodyEntity params = null;
        String type = null;
        if (operation.getParemeters() != null) {
            params = operation.getParemeters();
            type = RestHandlerHelper.PARAMETERS_BODY_ENTITY_TYPE;
        } else if (operation.getEntity() != null) {
            params = operation.getEntity();
            type = RestHandlerHelper.ENTITY_BODY_ENTITY_TYPE;
        }
        return Pair.of(params, type);
    }

    @Override
    public RestMessageContext createParamsCtx(final RestService service, final RestServiceOperation operation, final RestMessageContext<T> requestCtx) throws RestHandlerException {
        Pair<BodyEntity, String> pair = getParamsEntity(operation);
        BodyEntity params = pair.getLeft();
        String type = pair.getRight();
        if (requestCtx.getBody() == null) {
            if (params != null && params.isRequired())
                throw new IllegalArgumentException(String.format("%s required request", operation));
        }
        RestMessageContext res = new RestMessageContext();
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
    public RestService getService(final String url, final String method, final RestMessageContext<T> requestCtx) throws RestHandlerException {
        RestService res;
        for (String chainUrl : RestHandlerHelper.getChainUrls(url)) {
            res = this.services.get(chainUrl);
            if (res != null)
                return res;
        }
        throw new RestHandlerException(String.format("can't find service by url=%s", url));
    }

    @Override
    public RestServiceOperation getOperation(final RestService service, final String url, final String method, final RestMessageContext<T> requestCtx) throws RestHandlerException {
        RestServiceOperationKey key = new RestServiceOperationKey();
        key.setMethod(method);
        key.setUrl(RestHandlerHelper.clearEndOfUrl(RestHandlerHelper.clearStartOfUrl(url, service.getUrl()), RestHandlerHelper.VALIDATE_URL));
        RestServiceOperation res = service.getOperations().get(key);
        if (res == null)
            throw new RestHandlerException(String.format("can't find operation in %s by url=%s and method=%s", service, url, method));
        return res;
    }
}
