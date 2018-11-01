package ru.stasyan.interview_cl.util;

import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//Определение кодировки входящего файла
@Component
public class CodingDecoder {

    public String getCoding(File file) throws IOException {
        String result;
        int nread;
        byte[] buf = new byte[4096];
        UniversalDetector universalDetector = new UniversalDetector(null);
        FileInputStream fileInputStream = new FileInputStream(file);

        while ((nread = fileInputStream.read(buf)) > 0 && !universalDetector.isDone()) {
            universalDetector.handleData(buf, 0, nread);
        }
        universalDetector.dataEnd();

        result = universalDetector.getDetectedCharset();

        universalDetector.reset();
        fileInputStream.close();

        if (result != null) {
            return result;
        } else {
            throw new IOException("No encoding detected.");
        }
    }
}
