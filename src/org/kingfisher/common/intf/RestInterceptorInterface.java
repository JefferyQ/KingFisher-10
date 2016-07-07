package org.kingfisher.common.intf;

import org.kingfisher.common.model.RestMessageContext;
import org.kingfisher.common.model.RestHandlerException;

import java.util.Map;

// TODO: 04.07.2016 Добавить полное описание
public interface RestInterceptorInterface {
    void before(final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException;

    void after(final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException;
}
