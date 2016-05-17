package org.tkalenko.kingfisher.operation;

// TODO: 17.05.2016 добавить описания
// TODO: 17.05.2016 подумать об парамтерах пути, действительно ли они нужны? А ПОКА БЕЗ НИХ
public class RestOperation {
    /**
     * Идентификатор операции,  <br>
     * должен быть уникальным.  <br>
     */
    private String id;
    /**
     * Путь обращения к операции,   <br>
     * операции могут иметь одинаковые пути,    <br>
     * но должны различаться по {@link RestOperation#method}. <br>
     * <br>
     * Пример:  <br>
     * 1)/operation <br>
     */
    private String path;
    /**
     * HTTP метод операции, <br>
     * операции могут иметь одинаковые {@link RestOperation#path},    <br>
     * но должны различаться по HTTP методу. <br>
     * <br>
     * Пример: GET, POST, PUT и т.д. <br>
     */
    private String method;

    public RestOperation() {
        //no-op
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

    public String getMethod() {
        return method;
    }

    public void setMethod(final String method) {
        this.method = method;
    }

    /**
     * Две операции равны если: <br>
     * 1){@link RestOperation#id} - будут одинаковы <br>
     * 2){@link RestOperation#method} и {@link RestOperation#path} одновременно совпадают   <br>
     *
     * @param obj внешний объект
     * @return похожи или нет
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof RestOperation))
            return false;
        RestOperation o = (RestOperation) obj;
        if (this.id == null || o.id == null)
            return false;
        if (this.id.equals(o.id))
            return true;
        if (this.method == null || o.method == null)
            return false;
        if (!this.method.equals(o.method))
            return false;
        if (this.path == null || o.path == null)
            return false;
        return this.path.equals(o.path);
    }

    @Override
    public String toString() {
        return String.format("RestOperation={id={%s}, method={%s}, path={%s}}", id, method, path);
    }
}
