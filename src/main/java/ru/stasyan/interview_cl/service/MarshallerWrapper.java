package ru.stasyan.interview_cl.service;

import java.io.InputStream;

public interface MarshallerWrapper {
    <T>String marshallXml(T obj);
    <T> T unmarshallXml(final InputStream xml);
}
