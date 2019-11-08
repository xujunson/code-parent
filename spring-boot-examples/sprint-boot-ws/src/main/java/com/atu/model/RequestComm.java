package com.atu.model;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.io.StringWriter;

public abstract class RequestComm implements Serializable {
    //protected JAXBContext jaxbContext; 
    protected abstract JAXBContext getJAXBContext();

    private static final String encoding = "GB2312";
    private static final boolean formatted = false;

    @SuppressWarnings("unused")
    private AuthInfo authInfo;

    @XmlElement(name = "AuthInfo")
    public AuthInfo getAuthInfo() {
        //统一提供
        return new AuthInfo("atu", "1995");
        //return authInfo;
    }
    public String toXML() {
        try {
            StringWriter writer = new StringWriter();
            createMarshaller().marshal(this, writer);
            writer.close();
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Marshaller createMarshaller() {
        try {
            Marshaller marshaller = getJAXBContext().createMarshaller();
            //是否格式化XML   
            if (formatted) {
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            }
            marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            return marshaller;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
