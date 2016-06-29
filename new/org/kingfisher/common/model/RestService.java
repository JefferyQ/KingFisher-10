package org.kingfisher.common.model;

import java.util.HashMap;
import java.util.Map;

// TODO: 29.06.2016 добавить логику и полное описание
// TODO: 29.06.2016 шлифовать логику
public class RestService extends BaseRestServiceEntity {
    private final Map<RestServiceOperationKey, RestServiceOperation> operations = new HashMap<RestServiceOperationKey, RestServiceOperation>();

    public RestService() {
    }

    public Map<RestServiceOperationKey, RestServiceOperation> getOperations() {
        return this.operations;
    }

    @Override
    public String toString() {
        return String.format(
                "%s{id=%s, url=%s, operations=%s}",
                this.id,
                this.url,
                this.operations
        );
    }
}
