package com.atu.realtimelog.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.atu.realtimelog.component.WebSocketServer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAppender extends AppenderBase<ILoggingEvent> {


    private WebSocketServer webSocketServer;

    public MyAppender(WebSocketServer webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    /**
     * 添加日志
     *
     * @param iLoggingEvent
     */
    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        try {
            webSocketServer.sendMessage(doLayout(iLoggingEvent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 格式化日志
     *
     * @param event
     * @return
     */
    public String doLayout(ILoggingEvent event) {
        StringBuilder sbuf = new StringBuilder();
        if (null != event && null != event.getMDCPropertyMap()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

            sbuf.append(simpleDateFormat.format(new Date(event.getTimeStamp())));
            sbuf.append("\t");

            sbuf.append(event.getLevel());
            sbuf.append("\t");

            sbuf.append(event.getThreadName());
            sbuf.append("\t");

            sbuf.append(event.getLoggerName());
            sbuf.append("\t");

            sbuf.append(event.getFormattedMessage().replace("\"", "\\\""));
            sbuf.append("\t");
        }

        return sbuf.toString();
    }
}
