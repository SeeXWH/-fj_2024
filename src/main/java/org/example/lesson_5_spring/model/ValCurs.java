package org.example.lesson_5_spring.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class ValCurs {
    @XmlAttribute(name = "Date")
    private String date;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "Valute")
    private List<Valute> valutes = new ArrayList<>();
}
