package ru.stasyan.interview_cl.entity;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "content"
})
@XmlRootElement(name = "string")
public class StringElement {

    @XmlValue
    protected String content;

    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "string")
    protected String name;

    public StringElement(String name, String content) {
        this.content = content;
        this.name = name;
    }

    public StringElement() {
    }

    public String getContent() {
        return content;
    }


    public void setContent(String value) {
        this.content = value;
    }


    public String getName() {
        return name;
    }


    public void setName(String value) {
        this.name = value;
    }

}
