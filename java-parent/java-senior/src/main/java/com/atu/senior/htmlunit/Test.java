package com.atu.senior.htmlunit;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.net.MalformedURLException;
import java.io.IOException;


public class Test {
    public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
        // TODO Auto-generated method stub
        final WebClient mWebClient = new WebClient();
        final HtmlPage mHtmlPage = mWebClient.getPage("http://www.baidu.com");
        System.out.println(mHtmlPage.asText());
        //mWebClient.closeAllWindows();
    }
}
