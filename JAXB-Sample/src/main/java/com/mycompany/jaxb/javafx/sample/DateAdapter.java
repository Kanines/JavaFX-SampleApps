package com.mycompany.jaxb.javafx.sample;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

    @Override
    public Date unmarshal(String v) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").parse(v.toString());
    }

    @Override
    public String marshal(Date v) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").format((Date) v);
    }
}
