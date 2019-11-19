package com.atu.controller.login;

import com.atu.commons.shiro.PasswordHash;
import com.atu.commons.shiro.captcha.DreamCaptcha;
import com.atu.controller.base.BaseController;
import com.atu.service.CommonService;
import com.atu.service.sys.UserService;
import com.atu.util.DesUtil;
import com.atu.util.ShiroAuthUtil;
import com.atu.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Random;
import java.util.UUID;

@Controller
public class LoginController extends BaseController {
	@Autowired
	private DreamCaptcha dreamCaptcha;
	@Autowired
	private UserService userService;
	 @Autowired
	 private PasswordHash passwordHash;
	 @Autowired
	 private CommonService commonService;
	 /**
     * 首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "redirect:/index";
    }

    /**
     * 首页
     *
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String index(Model model) {
        return "jsp/index";
    }

    /**
     * GET 登录
     * @return {String}
     */
    @RequestMapping("/login")
    /*@CsrfToken(create = true)*/
    public String login(HttpSession session) {
        logger.info("GET请求登录");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }else{
            session.setAttribute("encryption_key", UUID.randomUUID().toString());
        }
        return "jsp/login";
    }

    /**
     * POST 登录 shiro 写法
     *
     * @param username 用户名
     * @param password 密码
     * @return {Object}
     */
    /*  @CsrfToken(remove = true)*/
    @RequestMapping(value="/login",method=RequestMethod.POST)
    @ResponseBody
    public Object loginPost(HttpServletRequest request, HttpServletResponse response,
            String username, String password, String captcha, 
            @RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe)throws Exception {
        logger.info("POST请求登录");
        // 改为全部抛出异常，避免ajax csrf token被刷新
        if (StringUtils.isBlank(username)) {
            throw new RuntimeException("用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new RuntimeException("密码不能为空");
        }
        if (StringUtils.isBlank(captcha)) {
            throw new RuntimeException("验证码不能为空");
        }
        if (!dreamCaptcha.validate(request, response, captcha)) {
            throw new RuntimeException("验证码错误");
        }

        HttpSession session=request.getSession();
        String key=(String)session.getAttribute("encryption_key");
        password= DesUtil.decrypt(password,key);
        username=DesUtil.decrypt(username,key);
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 设置记住密码
        //token.setRememberMe(1 == rememberMe);
        token.setRememberMe(true);
        try {
            user.login(token);
            logger.info("登录成功！");
            return renderSuccess("登录成功！");
        } catch (UnknownAccountException e) {
            throw new RuntimeException("账号不存在！", e);
        } catch (DisabledAccountException e) {
            throw new RuntimeException("账号未启用！", e);
        } catch (IncorrectCredentialsException e) {
            throw new RuntimeException("密码错误！", e);
        } catch (Throwable e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 未授权
     * @return {String}
     */
    @RequestMapping("/unauth")
    public String unauth() {
        if (SecurityUtils.getSubject().isAuthenticated() == false) {
            return "redirect:/login";
        }
        return "unauth";
    }

    /**
     * 退出
     * @return {Result}
     */
    @RequestMapping(value="/logout",method=RequestMethod.POST)
    @ResponseBody
    public Object logout(HttpSession session) {
        logger.info("登出");
        //移除加密盐
        session.removeAttribute("encryption_key");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        commonService.clearQueryCache();
        return renderSuccess();
    }
    
    /**
     * 更新shiro缓存
     * @param projectid
     * @param projectName
     * @return
     */
    @ResponseBody
    @RequestMapping("clearAuth")
    public Object clearAuth(Long projectid,String projectName) {
    	ShiroAuthUtil.clearAuth();
    	logger.info("projectid={}",projectid);
    	logger.info("projectName={}",projectName);
    	return renderSuccess();
    }
    /**
     * 随机生成6位数字组合作为短信验证码
     * @return
     */
    private String getSMSCode() {
        StringBuffer num = new StringBuffer("0123456789");
        StringBuffer sb = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < 6; i ++) {
            sb.append(num.charAt(r.nextInt(num.length())));
        }
        return sb.toString();
    }
    
  
  
}