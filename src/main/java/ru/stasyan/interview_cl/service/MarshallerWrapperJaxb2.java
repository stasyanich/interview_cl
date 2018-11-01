package ru.stasyan.interview_cl.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringWriter;

@Component
public class MarshallerWrapperJaxb2 implements MarshallerWrapper {

    private Jaxb2Marshaller marshaller;

    @Autowired
    public MarshallerWrapperJaxb2(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public <T> String marshallXml(final T obj) {
        StringWriter sw = new StringWriter();
        Result result = new StreamResult(sw);
        marshaller.marshal(obj, result);
        return sw.toString();
    }

    @SuppressWarnings("unchecked")
    public <T> T unmarshallXml(final InputStream xml)  {
        return (T) marshaller.unmarshal(new StreamSource(xml));
    }
}
