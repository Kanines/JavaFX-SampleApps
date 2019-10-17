package com.mycompany.jaxb.javafx.sample.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"subjects"})
@XmlRootElement(name = "examinationCard")
public class ExaminationCard {

    List<Subject> subjects;

    public ExaminationCard() {
        subjects = new ArrayList<>();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
}
