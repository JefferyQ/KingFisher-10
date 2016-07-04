package org.kingfisher.common.intf;

import org.kingfisher.common.model.RestMessageContext;
import org.kingfisher.common.model.RestHandlerException;
import org.kingfisher.common.model.RestValidationException;

import java.util.Map;

// TODO: 03.07.2016 добавить полное описание
public interface RestServiceOperationHandler {
    RestMessageContext handle(final String handler, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException;

    void validate(final String validator, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestValidationException;
}
