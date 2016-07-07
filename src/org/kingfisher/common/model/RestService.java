package org.kingfisher.common.model;

import org.kingfisher.common.intf.BodyHandlerInterface;
import org.kingfisher.common.intf.RestInterceptorInterface;
import org.kingfisher.common.intf.RestServiceOperationHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: 29.06.2016 добавить логику и полное описание
// TODO: 29.06.2016 шлифовать логику
public class RestService extends BaseRestServiceEntity {
    private final Map<RestServiceOperationKey, RestServiceOperation> operations = new HashMap<RestServiceOperationKey, RestServiceOperation>();
    private BodyHandlerInterface bodyHandler;
    private RestServiceOperationHandler operationHandler;
    private final List<RestInterceptorInterface> interceptors = new ArrayList<RestInterceptorInterface>();

    public RestService() {
    }

    public Map<RestServiceOperationKey, RestServiceOperation> getOperations() {
        return this.operations;
    }

    public BodyHandlerInterface getBodyHandler() {
        return this.bodyHandler;
    }

    public void setBodyHandler(final BodyHandlerInterface bodyHandler) {
        this.bodyHandler = bodyHandler;
    }

    public RestServiceOperationHandler getOperationHandler() {
        return this.operationHandler;
    }

    public void setOperationHandler(final RestServiceOperationHandler operationHandler) {
        this.operationHandler = operationHandler;
    }

    public List<RestInterceptorInterface> getInterceptors() {
        return this.interceptors;
    }

    @Override
    public String toString() {
        return String.format(
                "RestService{id=%s, url=%s}",
                this.id,
                this.url
        );
    }
}
