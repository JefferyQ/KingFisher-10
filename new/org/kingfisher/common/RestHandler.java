package org.kingfisher.common;

import org.kingfisher.common.intf.RestHandlerInterface;
import org.kingfisher.common.model.MessageContext;
import org.kingfisher.common.model.RestHandlerException;
import org.kingfisher.common.model.RestService;

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
        //Получение RestService
        //Получение RestServiceOperation
        //остальная логика
        return null;
    }

    @Override
    public void registerService(final RestService service) throws RestHandlerException {
        // TODO: 29.06.2016 добавить логику
    }

    @Override
    public RestService getService(final String url, final String method, final MessageContext<T> requestCtx) throws RestHandlerException {
        // TODO: 29.06.2016 добавить логику
        return null;
    }
}
