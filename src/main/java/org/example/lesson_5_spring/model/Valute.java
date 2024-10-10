package org.example.lesson_5_spring.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class Valute {
    @XmlAttribute(name = "ID")
    private String id;
    @XmlElement(name = "NumCode")
    private int numCode;
    @XmlElement(name = "CharCode")
    private String charCode;
    @XmlElement(name = "Nominal")
    private int nominal;
    @XmlElement(name = "Name")
    private String name;
    @XmlElement(name = "Value")
    private String value;
    @XmlElement(name = "VunitRate")
    private String vunitRate;
}
