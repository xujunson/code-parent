package com.atu.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Message")
public class Request1033Confirm extends RequestComm {
    private static JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(Request1033Confirm.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected JAXBContext getJAXBContext() {
        return jaxbContext;
    }

    private Param1033Confirm detail;

    @XmlElement(name = "Detail")
    public Param1033Confirm getDetail() {
        return detail;
    }

    public void setDetail(Param1033Confirm apply) {
        this.detail = apply;
    }
}


