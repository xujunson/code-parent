package cn.objectspace.taillog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Author: Tom
 * @Date: 2022/1/23 16:24
 * @Description:
 */
@Slf4j
@Service
public class WebSocketService {

    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    public void sendMessage() {
        // 通过Process和Runtime执行linux命令
        String cmd = "tail -f /usr/local/software/tomcat/apache-tomcat-8.5.75/logs/catalina.2022-01-23.log";
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            //需要另外启动线程进行读取，防止输入流阻塞当前线程
            Thread inputThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(process.getInputStream(), "UTF-8"));

                        StringBuffer line = new StringBuffer();
                        String lineOne = null;
                        int count = 0;
                        int lineNum = 1;
                        while ((lineOne = br.readLine()) != null) {
                            log.info("br.readLine()0 ==> {}", br.readLine());
                            if (count == 5) {

                                simpMessageSendingOperations.convertAndSend("/topic/greeting", line.toString());
                                line = new StringBuffer();
                                count = 0;
                                //控制线程执行速度 防止推送过快 导致浏览器卡屏
                                Thread.sleep(10);
                            } else {
                                line.append(lineOne + "</br>");
                                count++;
                                lineNum++;
                            }
                        }
                        log.info("convertAndSend0 ==>{}", line.toString());
                        simpMessageSendingOperations.convertAndSend("/topic/greeting", line.toString());
                        br.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            inputThread.start();

            //主线程读取错误输出流数据
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));
            StringBuffer line = new StringBuffer();
            String lineOne = null;
            int count = 0;
            int lineNum = 1;
            log.info("br.readLine()1 ==> {}", br.readLine());
            while ((lineOne = br.readLine()) != null) {
                if (count == 2) {
                    this.simpMessageSendingOperations.convertAndSend("/topic/greeting", line.toString());
                    line = new StringBuffer();
                    count = 0;
                } else {
                    line.append(lineOne + "</br>");
                    count++;
                    lineNum++;
                }
            }
            log.info("convertAndSend1 ==>{}", line.toString());
            this.simpMessageSendingOperations.convertAndSend("/topic/greeting", line.toString());
            //等待正常输出流线程读取完成后，统一销毁进程
            inputThread.join();
            // 返回码 0 表示正常退出 1表示异常退出
            int extValue = process.waitFor();
            if (0 == extValue) {
                log.info("Exit Success!");
                br.close();
                process.destroy();
            } else {
                log.info("Exit failure！");
                br.close();
                process.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("execute shell fail！", e);
            log.info("Exit failure！");
        }
    }
}
