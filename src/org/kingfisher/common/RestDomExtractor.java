package org.kingfisher.common;

import org.kingfisher.common.intf.BodyHandlerInterface;
import org.kingfisher.common.intf.RestExtractInterface;
import org.kingfisher.common.intf.RestInterceptorInterface;
import org.kingfisher.common.intf.RestServiceOperationHandler;
import org.kingfisher.common.model.*;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

// TODO: 06.07.2016 добавить полное описание
public class RestDomExtractor implements RestExtractInterface {
    public static final String REST_SERVICE_TAG = "rest-service";
    public static final String REST_OPERATION_TAG = "rest-operation";
    public static final String DESCRIPTION_TAG = "description";
    public static final String BODY_HANDLER_TAG = "body-handler";
    public static final String OPERATION_HANDLER_TAG = "operation-handler";
    public static final String INTERCEPTORS_TAG = "interceptors";
    public static final String INTERCEPTOR_TAG = "interceptor";
    public static final String HANDLER_TAG = "handler";
    public static final String VALIDATOR_TAG = "validator";
    public static final String PARAMETERS_TAG = "parameters";
    public static final String RESULT_TAG = "result";
    public static final String ENTITY_TAG = "entity";

    public static final String ID_ATTRIBUTE = "id";
    public static final String URL_ATTRIBUTE = "url";
    public static final String SERVICE_ID_ATTRIBUTE = "serviceId";
    public static final String METHOD_ATTRIBUTE = "method";
    public static final String AUTH_ATTRIBUTE = "auth";
    public static final String REQUIRED_ATTRIBUTE = "required";

    @Override
    public Collection<BaseRestServiceEntity> extract(final File xml, final ClassLoader classLoader) throws RestExtractorException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xml);

            Element root = doc.getDocumentElement();
            Collection<BaseRestServiceEntity> res = new ArrayList<BaseRestServiceEntity>();

            NodeList childNodes = root.getChildNodes();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(i);
                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    if (item.getNodeName().equals(REST_SERVICE_TAG)) {
                        res.add(createRestService((Element) item, classLoader));
                    } else if (item.getNodeName().equals(REST_OPERATION_TAG)) {
                        res.add(createRestOperation((Element) item, classLoader));
                    }
                }
            }
            return res;
        } catch (Exception e) {
            throw new RestExtractorException(e);
        }
    }

    public RestService createRestService(final Element restServiceElement, final ClassLoader classLoader) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        RestService res = new RestService();
        NodeList elements = restServiceElement.getChildNodes();
        NamedNodeMap attributes = restServiceElement.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            if (attribute.getNodeType() == Node.ATTRIBUTE_NODE) {
                Attr attr = (Attr) attribute;
                if (attr.getNodeName().equals(ID_ATTRIBUTE)) {
                    res.setId(attr.getTextContent());
                } else if (attr.getNodeName().equals(URL_ATTRIBUTE)) {
                    res.setUrl(attr.getTextContent());
                }
            }
        }
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            if (element.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) element;
                if (el.getNodeName().equals(DESCRIPTION_TAG)) {
                    res.setDescription(el.getTextContent());
                } else if (el.getNodeName().equals(BODY_HANDLER_TAG)) {
                    res.setBodyHandler((BodyHandlerInterface) classLoader.loadClass(el.getTextContent()).newInstance());
                } else if (el.getNodeName().equals(OPERATION_HANDLER_TAG)) {
                    res.setOperationHandler((RestServiceOperationHandler) classLoader.loadClass(el.getTextContent()).newInstance());
                } else if (el.getNodeName().equals(INTERCEPTORS_TAG)) {
                    NodeList interceptors = el.getChildNodes();
                    for (int j = 0; j < interceptors.getLength(); j++) {
                        Node interceptor = interceptors.item(j);
                        if (interceptor.getNodeType() == Node.ELEMENT_NODE) {
                            Element interceptorEl = (Element) interceptor;
                            if (interceptorEl.getNodeName().equals(INTERCEPTOR_TAG)) {
                                res.getInterceptors().add((RestInterceptorInterface) classLoader.loadClass(interceptorEl.getTextContent()).newInstance());
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    public RestServiceOperation createRestOperation(final Element restOperationElement, final ClassLoader classLoader) throws ClassNotFoundException {
        RestServiceOperation res = new RestServiceOperation();
        NodeList elements = restOperationElement.getChildNodes();
        NamedNodeMap attributes = restOperationElement.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            if (attribute.getNodeType() == Node.ATTRIBUTE_NODE) {
                Attr attr = (Attr) attribute;
                if (attr.getNodeName().equals(ID_ATTRIBUTE)) {
                    res.setId(attr.getTextContent());
                } else if (attr.getNodeName().equals(URL_ATTRIBUTE)) {
                    res.setUrl(attr.getTextContent());
                } else if (attr.getNodeName().equals(SERVICE_ID_ATTRIBUTE)) {
                    res.setServiceId(attr.getTextContent());
                } else if (attr.getNodeName().equals(METHOD_ATTRIBUTE)) {
                    res.setMethod(attr.getTextContent());
                } else if (attr.getNodeName().equals(AUTH_ATTRIBUTE)) {
                    res.setAuth(Boolean.valueOf(attr.getTextContent()));
                }
            }
        }
        for (int i = 0; i < elements.getLength(); i++) {
            Node element = elements.item(i);
            if (element.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) element;
                if (el.getNodeName().equals(DESCRIPTION_TAG)) {
                    res.setDescription(el.getTextContent());
                } else if (el.getNodeName().equals(HANDLER_TAG)) {
                    res.setHandler(el.getTextContent());
                } else if (el.getNodeName().equals(VALIDATOR_TAG)) {
                    res.setValidator(el.getTextContent());
                } else if (el.getNodeName().equals(PARAMETERS_TAG)) {
                    res.setParemeters(createBodyEntity(el, classLoader));
                } else if (el.getNodeName().equals(RESULT_TAG)) {
                    res.setResult(createBodyEntity(el, classLoader));
                } else if (el.getNodeName().equals(ENTITY_TAG)) {
                    res.setEntity(createBodyEntity(el, classLoader));
                }
            }
        }
        return res;
    }

    public BodyEntity createBodyEntity(final Element el, final ClassLoader classLoader) throws ClassNotFoundException {
        BodyEntity res = new BodyEntity();
        NamedNodeMap attributes = el.getAttributes();

        for (int i = 0; i < attributes.getLength(); i++) {
            Node attribute = attributes.item(i);
            if (attribute.getNodeType() == Node.ATTRIBUTE_NODE) {
                Attr attr = (Attr) attribute;
                if (attr.getNodeName().equals(REQUIRED_ATTRIBUTE)) {
                    res.setRequired(Boolean.valueOf(attr.getTextContent()));
                }
            }
        }
        res.setClazz(classLoader.loadClass(el.getTextContent()));
        return res;
    }
}
