package ru.stasyan.interview_cl.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import ru.stasyan.interview_cl.entity.*;
import ru.stasyan.interview_cl.util.CodingDecoder;

import java.io.*;

@Service
public class DataService {

    @Value("classpath:strings.xml")
    private Resource resource;
    private String fileEncoding;

    private MarshallerWrapperJaxb2 marshallerWrapperJaxb2;
    private CodingDecoder codingDecoder;

    @Autowired
    private MarshallerWrapperDOM marshallerWrapperDOM;

    @Autowired
    public DataService(MarshallerWrapperJaxb2 marshallerWrapperJaxb2, CodingDecoder codingDecoder) {
        this.marshallerWrapperJaxb2 = marshallerWrapperJaxb2;
        this.codingDecoder = codingDecoder;
    }

    public DataService() {
    }

    public Resources read() {
        Resources resources = null;
        try (FileInputStream fileInputStream = new FileInputStream(getFile())) {
            //resources = marshallerWrapperJaxb2.unmarshallXml(fileInputStream);
            resources = marshallerWrapperDOM.unmarshallXml(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resources;
    }

    public void save(Resources resources) {
//       String source =  marshallerWrapperJaxb2.marshallXml(resources);
       String source =  marshallerWrapperDOM.marshallXml(resources);
        try {
            File existingFile = resource.getFile();
            File parentDir = existingFile.getParentFile();
            File file = new File(parentDir,"string_o.xml"); //fixme закомментить

            //File file = new File(resource.getURI());
            FileUtils.writeStringToFile(file,source, fileEncoding);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {
        File file = null;
        try {
            file = new File(resource.getURI());
            fileEncoding = codingDecoder.getCoding(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
