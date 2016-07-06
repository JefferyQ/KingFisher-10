package org.firstTest;

import org.kingfisher.common.RestDomExtractor;
import org.kingfisher.common.RestHandler;
import org.kingfisher.common.intf.BodyHandlerInterface;
import org.kingfisher.common.intf.RestServiceOperationHandler;
import org.kingfisher.common.model.*;

import java.io.File;
import java.util.*;

/**
 * Первый простой пример, потом сложнее будет реализовано
 */
public class MainTest {

    private static class Req {
        String path;
        String method;
        RestMessageContext<String> context;
    }

    public static void main(String[] args) throws Exception {
        // TODO: 06.07.16 протестировать полностью нового загрузчика
        File file = new File("C:\\test\\test.xml");
        System.out.println(new RestDomExtractor().extract(file, MainTest.class.getClassLoader()));



//        RestHandler<String> restHandler = new RestHandler<>();
//        restHandler.registerService(createRestService());
//        restHandler.registerOperation(createRestServiceOperation1());
//        restHandler.registerOperation(createRestServiceOperation2());
//        restHandler.registerOperation(createRestServiceOperation3());
//        restHandler.registerOperation(createRestServiceOperation4());
//        restHandler.registerOperation(createRestServiceOperation5());
//
//        for (Req req : createReqs()) {
//            System.out.println("--------------------------------");
//            try {
//                RestMessageContext<String> response = restHandler.handle(req.path, req.method, req.context);
//                System.out.println(String.format("path=%s method=%s req=%s res=%s", req.path, req.method, req.context, response.getBody()));
//            } catch (Exception e) {
//                System.err.println("error = " + e.getMessage());
//            }
//            System.out.println("********************************");
//        }
    }

    private static Req[] createReqs() {
        Req req1 = new Req();
        RestMessageContext<String> reqCtx1 = new RestMessageContext<>();
        req1.path = "/service/op";
        req1.method = "GET";
        req1.context = reqCtx1;
        reqCtx1.setBody("1");

        Req req2 = new Req();
        RestMessageContext<String> reqCtx2 = new RestMessageContext<>();
        req2.path = "/service/op";
        req2.method = "POST";
        req2.context = reqCtx2;
        reqCtx2.setBody("2");

        Req req3 = new Req();
        RestMessageContext<String> reqCtx3 = new RestMessageContext<>();
        req3.path = "/service/op";
        req3.method = "PUT";
        req3.context = reqCtx3;
        reqCtx3.setBody("3");

        Req req4 = new Req();
        RestMessageContext<String> reqCtx4 = new RestMessageContext<>();
        req4.path = "/service/op";
        req4.method = "DELETE";
        req4.context = reqCtx4;
        reqCtx4.setBody("4");

        Req req5 = new Req();
        RestMessageContext<String> reqCtx5 = new RestMessageContext<>();
        req5.path = "/service/op";
        req5.method = "OPTIONS";
        req5.context = reqCtx5;
        reqCtx5.setBody("5");

        Req req6 = new Req();
        RestMessageContext<String> reqCtx6 = new RestMessageContext<>();
        req6.path = "/service/op";
        req6.method = "GET";
        req6.context = reqCtx6;

        Req req7 = new Req();
        RestMessageContext<String> reqCtx7 = new RestMessageContext<>();
        req7.path = "/service/op";
        req7.method = "POST";
        req7.context = reqCtx7;

        return new Req[]{
                req1,
                req2,
                req3,
                req4,
                req5,
                req6,
                req7,
        };
    }

    private static RestServiceOperation createRestServiceOperation5() {
        RestServiceOperation res = new RestServiceOperation();
        res.setServiceId("service");
        res.setId("op5");
        res.setUrl("/op");
        res.setMethod("OPTIONS");
        res.setHandler("handler_for_op5");

        BodyEntity result = new BodyEntity();
        result.setClazz(String.class);

        res.setResult(result);
        return res;
    }

    private static RestServiceOperation createRestServiceOperation4() {
        RestServiceOperation res = new RestServiceOperation();
        res.setServiceId("service");
        res.setId("op4");
        res.setUrl("/op");
        res.setMethod("DELETE");
        res.setHandler("handler_for_op4");

        BodyEntity parameters = new BodyEntity();
        parameters.setClazz(String.class);

        res.setParemeters(parameters);
        return res;
    }

    private static RestServiceOperation createRestServiceOperation3() {
        RestServiceOperation res = new RestServiceOperation();
        res.setServiceId("service");
        res.setId("op3");
        res.setUrl("/op");
        res.setMethod("PUT");
        res.setHandler("handler_for_op3");

        BodyEntity entity = new BodyEntity();
        entity.setClazz(String.class);

        res.setEntity(entity);
        return res;
    }

    private static RestServiceOperation createRestServiceOperation2() {
        RestServiceOperation res = new RestServiceOperation();
        res.setServiceId("service");
        res.setId("op2");
        res.setUrl("/op");
        res.setMethod("POST");
        res.setHandler("handler_for_op2");

        BodyEntity parameters = new BodyEntity();
        BodyEntity result = new BodyEntity();
        parameters.setClazz(String.class);
        result.setClazz(String.class);

        res.setParemeters(parameters);
        res.setResult(result);
        return res;
    }

    private static RestServiceOperation createRestServiceOperation1() {
        RestServiceOperation res = new RestServiceOperation();
        res.setServiceId("service");
        res.setId("op1");
        res.setUrl("/op");
        res.setMethod("GET");
        res.setHandler("handler_for_op1");

        BodyEntity parameters = new BodyEntity();
        BodyEntity result = new BodyEntity();
        parameters.setClazz(String.class);
        parameters.setRequired(false);
        result.setClazz(String.class);

        res.setParemeters(parameters);
        res.setResult(result);
        return res;
    }

    private static RestService createRestService() {
        RestService res = new RestService();
        res.setId("service");
        res.setUrl("/service");
        res.setBodyHandler(new BodyHandlerInterface<String>() {
            @Override
            public Object marshal(final Class<?> clazz, final String type, final RestMessageContext<String> requestCtx) throws RestHandlerException {
                return requestCtx.getBody() != null ? requestCtx.getBody() : null;
            }

            @Override
            public String unmarshal(final Class<?> clazz, final String type, final RestMessageContext resultCtx) throws RestHandlerException {
                return resultCtx.getBody() != null ? (String) resultCtx.getBody() : null;
            }

            @Override
            public String unmarshalValidation(final Class<?> clazz, final String type, final RestMessageContext paramsCtx) throws RestHandlerException {
                return paramsCtx.getBody() != null ? (String) paramsCtx.getBody() : null;
            }
        });
        res.setOperationHandler(new RestServiceOperationHandler() {
            @Override
            public RestMessageContext handle(final String handler, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestHandlerException {
                RestMessageContext res = new RestMessageContext();
                res.getCookies().addAll(paramsCtx.getCookies());
                res.getHeaders().addAll(paramsCtx.getHeaders());
                res.setBody(String.format("params=%s handle=%s", paramsCtx.getBody(), handler));
                return res;
            }

            @Override
            public void validate(final String validator, final RestMessageContext paramsCtx, final Map<String, Object> data) throws RestValidationException {

            }
        });
        return res;
    }


}
