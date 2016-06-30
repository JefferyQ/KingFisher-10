package org.kingfisher;

import org.kingfisher.common.RestHandler;
import org.kingfisher.common.model.RestHandlerException;
import org.kingfisher.common.model.RestService;
import org.kingfisher.common.model.RestServiceOperation;

public class MainTest {

    public static void main(String[] args) throws RestHandlerException {
        RestHandler restHandler = new RestHandler<>();
        restHandler.registerService(createService());
        restHandler.registerOperation(createOperation());

        restHandler.handle("/service1/operation", "GET", null);
    }

    private static RestServiceOperation createOperation() {
        RestServiceOperation res = new RestServiceOperation();
        res.setServiceId("service1");
        res.setId("operation1");
        res.setUrl("/operation");
        res.setMethod("GET");
        //а тут уже можно делать эксперименты
        return res;
    }

    private static RestService createService() {
        RestService res = new RestService();
        res.setId("service1");
        res.setUrl("/service1");

        return res;
    }

}
