package org.kingfisher.common;

import org.kingfisher.common.intf.RestExtractInterface;
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

    public static final String ID_ATTRIBUTE = "id";
    public static final String URL_ATTRIBUTE = "url";
    public static final String SERVICE_ID_ATTRIBUTE = "url";
    public static final String METHOD_ATTRIBUTE = "url";
    public static final String AUTH_ATTRIBUTE = "url";

    @Override
    public Collection<BaseRestServiceEntity> extract(final File xml) throws RestExtractorException {
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
                        res.add(createRestService((Element) item));
                    } else if (item.getNodeName().equals(REST_OPERATION_TAG)) {
                        res.add(createRestOperation((Element) item));
                    }
                }
            }
            return res;
        } catch (Exception e) {
            throw new RestExtractorException(e);
        }
    }

    public RestService createRestService(final Element restServiceElement) {
        RestService res = new RestService();
        // TODO: 06.07.2016 add logic
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
                    // TODO: 06.07.2016 add logic
                } else if (el.getNodeName().equals(OPERATION_HANDLER_TAG)) {
                    // TODO: 06.07.2016 add logic
                } else if (el.getNodeName().equals(INTERCEPTORS_TAG)) {
                    // TODO: 06.07.2016 add logic
                }
            }
        }
        return res;
    }

    public RestServiceOperation createRestOperation(final Element restOperationElement) {
        RestServiceOperation res = new RestServiceOperation();
        // TODO: 06.07.2016 add logic
        NodeList childNodes = restOperationElement.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node node = childNodes.item(i);
            switch (node.getNodeType()) {
                case Node.ATTRIBUTE_NODE:
                    Attr attr = (Attr) node;
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
                    break;
                case Node.ELEMENT_NODE:
                    break;
            }
        }
        return res;
    }
}
