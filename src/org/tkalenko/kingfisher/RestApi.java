package org.tkalenko.kingfisher;

import org.tkalenko.kingfisher.service.RestService;

import java.util.ArrayList;
import java.util.Collection;

// TODO: 17.05.2016 добавить описание и логику
public class RestApi {
    /**
     * Сервисы, все сервисы должны быть уникальными.     <br>
     */
    private final Collection<RestService> services = new ArrayList<RestService>();

    public RestApi() {
        //no-op
    }

    // TODO: 17.05.2016 написать логику и документация
    public void service(final String path, final String method) throws IllegalArgumentException {
        RestService service = getService(path);
        service.service(path, method);
    }

    /**
     * Поиск сервиса по внешнему запросу.   <br>
     * Запрос должен быть полным и окончательным.   <br>
     * Пример внешнего сервиса: /service/operation  <br>
     * Если не получится найти сервис по запросу, то появится ошибка. <br>
     *
     * @param path внешний запрос
     * @return сервис
     * @throws IllegalArgumentException если не получилось найти сервис по внешнему пути
     */
    public RestService getService(final String path) throws IllegalArgumentException {
        for (RestService service : this.services) {
            if (service.isThisService(path)) {
                return service;
            }
        }
        throw new IllegalArgumentException(String.format("ERROR: can't find service by path={%s}"));
    }

    /**
     * Добавление нового сервиса.    <br>
     * Новый сервис должен быть уникальным, иначе будет ошибка добавления.   <br>
     * Сравнение сервисов описано в {@link RestService#equals(Object)}   <br>
     *
     * @param service новый сервис
     * @throws IllegalArgumentException если новый сервис будет не уникален
     */
    public void addService(final RestService service) throws IllegalArgumentException {
        for (RestService restService : this.services) {
            if (restService.equals(service))
                throw new IllegalArgumentException(String.format(
                        "ERROR: while add new $s, because same %s already exists",
                        service,
                        restService
                ));
        }
        this.services.add(service);
    }
}
