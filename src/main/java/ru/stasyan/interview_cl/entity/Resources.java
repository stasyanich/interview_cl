package ru.stasyan.interview_cl.entity;

import org.springframework.stereotype.Component;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Component
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "stringOrStringArray"
})
@XmlRootElement(name = "resources")
public class Resources {

    @XmlElements({
            @XmlElement(name = "string", type = StringElement.class),
            @XmlElement(name = "string-array", type = StringArray.class)
    })

    protected ArrayList<StringElement> stringOrStringArray;

    public ArrayList<StringElement> getStringOrStringArray() {
        if (stringOrStringArray == null) {
            stringOrStringArray = new ArrayList<>();
        }
        return this.stringOrStringArray;
    }

    public void setStringOrStringArray(ArrayList<StringElement> stringOrStringArray) {
        this.stringOrStringArray = stringOrStringArray;
    }

    public Resources(ArrayList<StringElement> stringOrStringArray) {
        this.stringOrStringArray = stringOrStringArray;
    }

    public Resources() {
    }
}



