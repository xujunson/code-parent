package cn.objectspace.taillog;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Tom
 * @Date: 2022/1/23 16:24
 * @Description:
 */
@Slf4j
@Controller
public class GreetingController {

    @Autowired
    private WebSocketService webSocketService;

    @RequestMapping(value = "/taillog", method = RequestMethod.GET)
    public String taillog() throws Exception {
        log.info("==生成日誌==");
        webSocketService.sendMessage();
        log.info("==頁面打印==");
        return "taillog";
    }

}
