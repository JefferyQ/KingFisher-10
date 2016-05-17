package org.tkalenko.kingfisher.service;

import org.tkalenko.kingfisher.operation.RestOperation;

import java.util.HashMap;
import java.util.Map;

// TODO: 17.05.2016 добавить описания
public class RestService {
    /**
     * Идентификатор сервиса,   <br>
     * должен быть уникальным.  <br>
     */
    private String id;
    /**
     * Путь обращения к сервису,    <br>
     * должен быть уникальным.      <br>
     * Пример пути: /service        <br>
     */
    private String path;
    /**
     * Операции сервиса,    <br>
     * которые разделены на 2 уровня: такое разделение сделано для оптимизации, получения операции  <br>
     * 1)разделения по {@link RestOperation#method} <br>
     * 2)разделение по {@link RestOperation#path}   <br>
     * <br>
     * Пример хранения: <br>
     * 1)GET  : /operation1, /operation2, /operation3   <br>
     * 2)POST : /operation4, /operation2, /operation3, /operation5  <br>
     * и т.д.   <br>
     */
    private final Map<String, Map<String, RestOperation>> operations = new HashMap<String, Map<String, RestOperation>>();

    public RestService() {
        //no-op
    }

    // TODO: 17.05.2016 написать логику и документация
    public void service(final String path, final String method) throws IllegalArgumentException {
        RestOperation operation = getOperation(path, method);
    }

    /**
     * Получение операции сервиса. По полному внешнему запросу, и методу.   <br>
     * Пример внешнего запроса: /service/operation  <br>
     * Если не получится найти по запросу и методу операцию, будет ошибка.  <br>
     *
     * @param path   полный внешний запрос
     * @param method HTTP метод внешнего запроса
     * @return операция
     * @throws IllegalArgumentException если по запросу и методу не нашли операцию
     */
    public RestOperation getOperation(final String path, final String method) throws IllegalArgumentException {
        Map<String, RestOperation> byMethod = this.operations.get(method);
        if (byMethod == null)
            throw new IllegalArgumentException(String.format("ERROR: can't find operations by method={$s} in $s", method, this));
        RestOperation operation = byMethod.get(clearPath(path));
        if (operation == null)
            throw new IllegalArgumentException(String.format("ERROR: can't find operation by path={$s} in $s ", path, this));
        return operation;
    }

    /**
     * Добавление новой операции в сервис,  <br>
     * новая операция не должна повторяться.    <br>
     * Если новая операция будет не уникальной, то появиться ошибка.    <br>
     * Сравнение операций описано в {@link RestOperation#equals(Object)}  <br>
     *
     * @param operation новая операция для сервиса
     * @throws IllegalArgumentException в случае если новая операция для сервиса не будет уникальна
     */
    public void addOperation(final RestOperation operation) throws IllegalArgumentException {
        for (Map<String, RestOperation> byMethods : operations.values()) {
            for (RestOperation serviceOperation : byMethods.values()) {
                if (serviceOperation.equals(operation))
                    throw new IllegalArgumentException(String.format(
                            "Error: while add new %s into %s, because same %s already exists in service",
                            operation,
                            this,
                            serviceOperation)
                    );
            }
        }
        Map<String, RestOperation> byMethodOperations = this.operations.get(operation.getMethod());
        if (byMethodOperations == null) {
            byMethodOperations = new HashMap<String, RestOperation>();
            this.operations.put(operation.getMethod(), byMethodOperations);
        }
        byMethodOperations.put(operation.getPath(), operation);
    }

    /**
     * Очистка внешнего запроса от {@link RestService#path} сервиа. <br>
     * <br>
     * Пример:  <br>
     * 1)внешний запрос: /service/operation <br>
     * 2){@link RestService#path} сервиса: /service <br>
     * 3)результат: /operation  <br>
     *
     * @param path внешний запрос
     * @return внешний запрос очищенный от {@link RestService#path} сервиса
     */
    public String clearPath(final String path) {
        return path.replaceFirst(this.path, "");
    }

    /**
     * Метод позволяет определить,          <br>
     * адресован ли запрос данному севису.  <br>
     * <br>
     * Пример запроса: /service/operation <br>
     *
     * @param path внешний запрос
     * @return запрос соответсвцет/не соответсвует сервису
     */
    public boolean isThisService(final String path) {
        return path.startsWith(this.path);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    /**
     * Два сервиса будут равны если:    <br>
     * 1){@link RestService#id} - равны <br>
     * 2){@link RestService#path} - равны   <br>
     *
     * @param obj внешний объект
     * @return похожы или нет
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof RestOperation))
            return false;
        RestService o = (RestService) obj;
        if (this.id == null || o.id == null)
            return false;
        if (this.id.equals(o.id))
            return true;
        if (this.path == null || o.path == null)
            return false;
        return this.path.equals(o.path);
    }

    @Override
    public String toString() {
        return String.format("RestService={id={%s}, path={%s}}", id, path);
    }
}
