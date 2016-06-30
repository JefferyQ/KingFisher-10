package org.kingfisher.common.model;

import org.kingfisher.common.intf.BodyHandlerInterface;

import java.util.HashMap;
import java.util.Map;

// TODO: 29.06.2016 добавить логику и полное описание
// TODO: 29.06.2016 шлифовать логику
public class RestService extends BaseRestServiceEntity {
    private final Map<RestServiceOperationKey, RestServiceOperation> operations = new HashMap<RestServiceOperationKey, RestServiceOperation>();
    private BodyHandlerInterface bodyHandler;

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

    @Override
    public String toString() {
        return String.format(
                "%s{id=%s, url=%s, operations=%s, bodyHandler=%s}",
                this.getClass().getName(),
                this.id,
                this.url,
                this.operations,
                this.bodyHandler
        );
    }
}
