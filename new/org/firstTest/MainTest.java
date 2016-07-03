package org.firstTest;

import org.kingfisher.common.RestHandler;
import org.kingfisher.common.intf.BodyHandlerInterface;
import org.kingfisher.common.intf.RestServiceOperationHandler;
import org.kingfisher.common.model.*;

/**
 * Первый простой пример, потом сложнее будет реализовано
 */
public class MainTest {

    private static class Req {
        String path;
        String method;
        MessageContext<String> context;
    }

    public static void main(String[] args) throws RestHandlerException {
        RestHandler<String> restHandler = new RestHandler<>();
        restHandler.registerService(createRestService());
        restHandler.registerOperation(createRestServiceOperation1());
        restHandler.registerOperation(createRestServiceOperation2());
        restHandler.registerOperation(createRestServiceOperation3());
        restHandler.registerOperation(createRestServiceOperation4());
        restHandler.registerOperation(createRestServiceOperation5());

        for (Req req : createReqs()) {
            System.out.println("--------------------------------");
            try {
                MessageContext<String> response = restHandler.handle(req.path, req.method, req.context);
                System.out.println(String.format("path=%s method=%s req=%s res=%s", req.path, req.method, req.context, response.getBody()));
            } catch (Exception e) {
                System.err.println("error = " + e.getMessage());
            }
            System.out.println("********************************");
        }
    }

    private static Req[] createReqs() {
        Req req1 = new Req();
        MessageContext<String> reqCtx1 = new MessageContext<>();
        req1.path = "/service/op";
        req1.method = "GET";
        req1.context = reqCtx1;
        reqCtx1.setBody("1");

        Req req2 = new Req();
        MessageContext<String> reqCtx2 = new MessageContext<>();
        req2.path = "/service/op";
        req2.method = "POST";
        req2.context = reqCtx2;
        reqCtx2.setBody("2");

        Req req3 = new Req();
        MessageContext<String> reqCtx3 = new MessageContext<>();
        req3.path = "/service/op";
        req3.method = "PUT";
        req3.context = reqCtx3;
        reqCtx3.setBody("3");

        Req req4 = new Req();
        MessageContext<String> reqCtx4 = new MessageContext<>();
        req4.path = "/service/op";
        req4.method = "DELETE";
        req4.context = reqCtx4;
        reqCtx4.setBody("4");

        Req req5 = new Req();
        MessageContext<String> reqCtx5 = new MessageContext<>();
        req5.path = "/service/op";
        req5.method = "OPTIONS";
        req5.context = reqCtx5;
        reqCtx5.setBody("5");

        Req req6 = new Req();
        MessageContext<String> reqCtx6 = new MessageContext<>();
        req6.path = "/service/op";
        req6.method = "GET";
        req6.context = reqCtx6;

        Req req7 = new Req();
        MessageContext<String> reqCtx7 = new MessageContext<>();
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
            public Object marshal(final Class<?> clazz, final String type, final MessageContext<String> requestCtx) throws RestHandlerException {
                return requestCtx.getBody() != null ? requestCtx.getBody() : null;
            }

            @Override
            public String unmarshal(final Class<?> clazz, final String type, final MessageContext resultCtx) throws RestHandlerException {
                return resultCtx.getBody() != null ? (String) resultCtx.getBody() : null;
            }
        });
        res.setOperationHandler(new RestServiceOperationHandler() {
            @Override
            public MessageContext handle(final String handler, final MessageContext paramsCtx) throws RestHandlerException {
                MessageContext res = new MessageContext();
                res.getCookies().addAll(paramsCtx.getCookies());
                res.getHeaders().addAll(paramsCtx.getHeaders());
                res.setBody(String.format("params=%s handle=%s", paramsCtx.getBody(), handler));
                return res;
            }
        });
        return res;
    }


}
