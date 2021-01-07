package com.atu.util;

import com.atu.model.Request1032Apply;
import com.atu.model.Request1033Confirm;
import com.atu.model.Response1032Apply;
import com.atu.model.Response1033Confirm;
import org.apache.commons.digester3.Digester;
import org.apache.commons.digester3.annotations.FromAnnotationsRuleModule;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.Properties;

import static org.apache.commons.digester3.binder.DigesterLoader.newLoader;

public class ETCClient {
    private final static Log LOGGER = LogFactory.getLog(ETCClient.class);

    public static String invokeMethod(String soapRequestData) {
        LOGGER.info("调用wsdl接口");
        Properties prop = new Properties();
        String url = "";
        try {
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            url = prop.getProperty("spring.webservices.path");
        } catch (IOException e) {

        }

        PostMethod postMethod = new PostMethod(url);
        byte[] b;
        try {
            b = soapRequestData.getBytes("utf-8");
            InputStream is = new ByteArrayInputStream(b, 0, b.length);
            RequestEntity re = new InputStreamRequestEntity(is, b.length,
                    "application/soap+xml; charset=utf-8");
            postMethod.setRequestEntity(re);
            HttpClient httpClient = new HttpClient();
            httpClient.getHttpConnectionManager().getParams()
                    .setConnectionTimeout(1200000);
            // response后处理数据timeout异常。
            httpClient.getHttpConnectionManager().getParams()
                    .setSoTimeout(1200000);
            int statusCode;
            statusCode = httpClient.executeMethod(postMethod);
            if (200 == statusCode) {
                InputStream inputStream = postMethod.getResponseBodyAsStream();
                SAXReader saxReadr = new SAXReader();
                Document doc;

                doc = saxReadr.read(inputStream);
                inputStream.close();
                String res = doc.getStringValue();
                return res;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            LOGGER.error("字符集转换" + e.getMessage());
            return null;
        } catch (DocumentException e) {
            LOGGER.error("文本解析错误" + e.getMessage());
            return null;
        } catch (HttpException e) {
            LOGGER.error("请求错误" + e.getMessage());
            return null;
        } catch (IOException e) {
            LOGGER.error("I/O错误" + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * @param businessType
     * @param requestContent
     * @return
     */
    private static String getSoapRequestData(String businessType,
                                             String requestContent) {
        String soapRequestData = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://com/wtkj/zwgl/agent/web/webservice\">\n"
                + "   <soapenv:Body>\n"
                + "      <web:RequestByClient>\n"
                + "         <web:Request_Type>"
                + businessType
                + "</web:Request_Type>\n"
                + "         <web:Request_Content><![CDATA["
                + requestContent
                + "]]></web:Request_Content>\n"
                + "      </web:RequestByClient>\n"
                + "   </soapenv:Body>\n"
                + "</soapenv:Envelope>";
        return soapRequestData;
    }


    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Response1032Apply exe1032(Request1032Apply request) throws Exception {
        String businessType = "1032";
        String soapRequestData = getSoapRequestData(businessType,
                request.toXML());
        String res = invokeMethod(soapRequestData);
        Response1032Apply pi = parseXml(res, Response1032Apply.class);
        return pi;
    }


    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Response1033Confirm exe1033(Request1033Confirm request) throws Exception {
        String businessType = "1033";
        String soapRequestData = getSoapRequestData(businessType,
                request.toXML());
        String res = invokeMethod(soapRequestData);
        Response1033Confirm pi = parseXml(res, Response1033Confirm.class);
        return pi;
    }

    /**
     * 将xml格式字符串解析为java对象
     *
     * @param xml
     * @param clazz
     * @return
     * @throws Exception
     */
    /**
     * 将xml格式字符串解析为java对象
     *
     * @param xml
     * @param clazz
     * @return
     * @throws Exception
     */
    private static <T> T parseXml(String xml, final Class<T> clazz) throws Exception {
        StringReader sr = null;
        try {
            LOGGER.debug("xml-----" + xml);
            Digester digester = newLoader(new FromAnnotationsRuleModule() {
                @Override
                protected void configureRules() {
                    bindRulesFrom(clazz);
                }
            }).newDigester();
            sr = new StringReader(xml);
            T pi = digester.parse(sr);
            digester.clear();
            return pi;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        } finally {
            if (sr != null) {
                sr.close();
            }
        }
    }
}
