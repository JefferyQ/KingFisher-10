package org.kingfisher.common.intf;

import org.kingfisher.common.model.MessageContext;
import org.kingfisher.common.model.RestHandlerException;

// TODO: 03.07.2016 добавить полное описание
public interface RestServiceOperationHandler {
    MessageContext handle(final String handler, final MessageContext paramsCtx) throws RestHandlerException;
}
