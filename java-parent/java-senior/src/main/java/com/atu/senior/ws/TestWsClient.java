package com.atu.senior.ws;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.*;

public class TestWsClient {
    private static final Logger LOG = LoggerFactory.getLogger(TestWsClient.class);
    public static String sendToTrafficSys(String request_type, String content) {
        return process(request_type,content);
    }

    public static String process(String request_type, String request_content) {

        LOG.info("request_content" + request_content);
        String str = request_content;
        Service serv = new Service();
        Call call;
        String receive_str = "";
        try {
            call = (Call) serv.createCall();
            call.setTargetEndpointAddress("http://192.162.130.160:9002/cmbagent-web/AgentCustomerService?wsdl");
            call.setOperationName(new QName("http://com/wtkj/zwgl/agent/web/webservice", "RequestByClient"));
            call.addParameter(new QName("http://com/wtkj/zwgl/agent/web/webservice", "Request_Type"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
            call.addParameter(new QName("http://com/wtkj/zwgl/agent/web/webservice", "Request_Content"), org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
            call.setReturnClass(String.class);
            receive_str = (String) call.invoke(new Object[]{request_type, str});
            System.out.println("返回结果报文" + receive_str);
        } catch (ServiceException e) {
            LOG.error(e.getMessage(), e);
        } catch (RemoteException e) {
            LOG.error(e.getMessage(), e);
        }
        return receive_str;
    }

    public static Map<String, String> responseToObject(String response) {
        Map<String, String> result = new HashMap<String, String>();
        if (response == null) {
            return null;
        }
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(response);
        } catch (DocumentException e) {
            LOG.error(e.getMessage());
            return null;
        }
        Element root = doc.getRootElement().element("Result");
        Element rc = root.element("resultcode");
        result.put(rc.getName(), rc.getTextTrim());
        Element id = root.element("complainId");
        result.put(id.getName(), id.getTextTrim());
        Element errinfo = root.element("errinfo");
        result.put(errinfo.getName(), errinfo.getTextTrim());
        Element accessoryContents = root.element("AccessoryContents");
        if (accessoryContents != null) {
            @SuppressWarnings("unchecked")
            List<Element> eles = accessoryContents.elements();
            Iterator<Element> it = eles.iterator();
            Element ele;
            while (it.hasNext()) {
                ele = it.next();
                result.put(ele.getName(), ele.getText());
            }
        }
        return result;
    }

    public static String buildMessageTag(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version='1.0' encoding='GB2312'?>");
        sb.append("<Message>");
        sb.append(createAuthInfo());
        sb.append("<Detail>");
        sb.append("<ComplainId>");
        sb.append(map.get("complainId"));
        sb.append("</ComplainId>");
        sb.append("<State>");
        sb.append(map.get("state"));
        sb.append("</State>");
        sb.append("<Status>");
        sb.append(map.get("status"));
        sb.append("</Status>");
        sb.append("<RequestTime>");
        sb.append(new Date());
        sb.append("</RequestTime>");
        sb.append("<Result>");
        sb.append(map.get("result"));
        sb.append("</Result>");
        sb.append("</Detail>");
        sb.append("</Message>");

        return sb.toString();
    }

    private static String createAuthInfo() {
        return "<AuthInfo><UserName>sa</UserName>" +
                "<Password>CF79AE6ADDBA60AD018347359BD144D2</Password></AuthInfo>";
    }


    public static void main(String[] args) {

        sendToTrafficSys("1401","<?xml version=\"1.0\" encoding=\"GB2312\"?><Message xmlns=\"http://www.zwgl.wtkj.com/CustomerRequest1401\">\n" +
                "<AuthInfo><UserName>sa</UserName><Password>CF79AE6ADDBA60AD018347359BD144D2</Password>\n" +
                "</AuthInfo><Detail>\n" +
                "<ClientNo>600000001</ClientNo>\n" +
                "<Complainant>BBB</Complainant>\n" +
                "<ComplainantTel>13218191011</ComplainantTel>\n" +
                "<EtcCardType>ABC</EtcCardType>\n" +
                "<ClientType>0</ClientType>\n" +
                "<CarNumber>京A99999</CarNumber>\n" +
                "<VehColor>0</VehColor>\n" +
                "<HasObu>1</HasObu>\n" +
                "<ObuNumber>11111111111</ObuNumber>\n" +
                "<ComplainType>1</ComplainType>\n" +
                "<ComplainLevel>1</ComplainLevel>\n" +
                "<TollStationType>1</TollStationType>\n" +
                "<TollStation>111111</TollStation>\n" +
                "<CashPayment>1</CashPayment>\n" +
                "<InvoiceNumber>111111111</InvoiceNumber>\n" +
                "<OccurredOn>2019-10-02T16:26:46</OccurredOn>\n" +
                "<EtcCardNumber>1111111111</EtcCardNumber>\n" +
                "<Description>hhhhhhhhhhhh</Description>\n" +
                "<Type>1</Type>\n" +
                "<ComplainId>C055028B-D0DD-C3D2-1BB3-20AF4719C050</ComplainId>\n" +
                "</Detail></Message>");

    }
}
