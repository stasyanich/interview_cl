package ru.stasyan.interview_cl.service;

import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import ru.stasyan.interview_cl.entity.Resources;
import ru.stasyan.interview_cl.entity.StringArray;
import ru.stasyan.interview_cl.entity.StringElement;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class MarshallerWrapperDOM implements MarshallerWrapper {

    private Document document;

    @Override
    public <T> String marshallXml(final T obj) {
        writeAllLines((Resources)obj);
        return transformDocumentToString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public  Resources unmarshallXml(InputStream xml) {
        DocumentBuilderFactory dbf = getDocumentBuilderFactory();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(xml);
            return parseAllLines(document);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private Resources parseAllLines(Document document) {
        NodeList nl = document.getDocumentElement().getChildNodes();
        ArrayList list = new ArrayList();
        Comment comment = null;
        for (int i = 0; i < nl.getLength(); i++) {
            Node node = nl.item(i);
            NamedNodeMap attributes = node.getAttributes();
            if (node.getNodeName().equals("string")) {
                String localName = attributes.getNamedItem("name").getNodeValue();
                String textContent = node.getTextContent();
                list.add(new StringElement(localName, textContent));
            }
            if (node.getNodeName().equals("string-array")) {
                String localName = attributes.getNamedItem("name").getNodeValue();
                StringArray stringArray = new StringArray(localName);
                List items = stringArray.getItem();

                NodeList children = node.getChildNodes();
                for (int j = 0; j < children.getLength(); j++) {
                    Node child = children.item(j);
                    String item = child.getTextContent().trim();
                    if (item.length()>0){
                        items.add(item);
                    }
                }
                list.add(stringArray);
            }
        }
        return new Resources(list);
    }

    @SuppressWarnings("unchecked")
    private void writeAllLines(Resources resources) {
        ArrayList elements = resources.getStringOrStringArray();
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NamedNodeMap attributes = node.getAttributes();
            if (node.getNodeName().equals("string")) {
                for (Object element:elements) {
                    if (element instanceof StringElement) {
                        StringElement stringElement = (StringElement) element;
                        if (attributes.getNamedItem("name").getNodeValue().equals(stringElement.getName())) {
                            node.setTextContent(stringElement.getContent());
                        }
                    }
                }
            }
            if (node.getNodeName().equals("string-array")) {
                for (Object element : elements) {
                    if (element instanceof StringArray) {
                        StringArray stringArray = (StringArray) element;
                        if (attributes.getNamedItem("name").getNodeValue().equals(stringArray.getName())) {
                            NodeList nodeItems = node.getChildNodes();
                            List<String> elementItems =  stringArray.getItem();
                            int num = 0;
                            for (int j = 0; j < nodeItems.getLength() ; j++) {
                                String item = nodeItems.item(j).getTextContent().trim();
                                if (item.length() > 0) {
                                   nodeItems.item(j).setTextContent(elementItems.get(num++));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private String transformDocumentToString()  {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString().replace(" standalone=\"no\"","");
        } catch (TransformerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }

    private DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setCoalescing(false);
        dbf.setExpandEntityReferences(false);
        dbf.setIgnoringComments(false);
        dbf.setIgnoringElementContentWhitespace(false);
        return dbf;
    }
}
