package com.mycompany.jaxb.javafx.sample;

import com.mycompany.jaxb.javafx.sample.model.ExaminationCard;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

    public ObjectFactory() {
    }

    public ExaminationCard createKartaEgzaminacyjna() {
        return new ExaminationCard();
    }
}
