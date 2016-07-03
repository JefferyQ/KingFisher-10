package org.kingfisher.common.model;

import org.kingfisher.common.intf.BodyHandlerInterface;
import org.kingfisher.common.intf.RestServiceOperationHandler;

import java.util.HashMap;
import java.util.Map;

// TODO: 29.06.2016 добавить логику и полное описание
// TODO: 29.06.2016 шлифовать логику
public class RestService extends BaseRestServiceEntity {
    private final Map<RestServiceOperationKey, RestServiceOperation> operations = new HashMap<RestServiceOperationKey, RestServiceOperation>();
    private BodyHandlerInterface bodyHandler;
    private RestServiceOperationHandler operationHandler;

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

    @Override
    public String toString() {
        return String.format(
                "%s{id=%s, url=%s, operations=%s, bodyHandler=%s, operationHandler=%s}",
                this.getClass().getName(),
                this.id,
                this.url,
                this.operations,
                this.bodyHandler,
                this.operationHandler
        );
    }
}
