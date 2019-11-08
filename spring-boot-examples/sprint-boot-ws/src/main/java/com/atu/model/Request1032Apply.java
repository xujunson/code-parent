package com.atu.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
public class Request1032Apply extends RequestComm {
    private static JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(Request1032Apply.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected JAXBContext getJAXBContext() {
        return jaxbContext;
    }

    private Param1032Apply detail;

    @XmlElement(name = "Detail")
    public Param1032Apply getDetail() {
        return detail;
    }

    public void setDetail(Param1032Apply apply) {
        this.detail = apply;
    }
}


