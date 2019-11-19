package com.atu.controller.base;

import com.atu.commons.shiro.captcha.DreamCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class CommonsController extends BaseController{
    @Autowired
    private DreamCaptcha dreamCaptcha;

    /**
     * 图形验证码
     */
    @RequestMapping("captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        dreamCaptcha.generate(request, response);
    }

    @RequestMapping("/home")
    public String home() {
        return "jsp/home";
    }


    
    @RequestMapping(value="ckeditImg")
    public void ckeditImg(@RequestParam(value="upload",required=false)MultipartFile file,HttpServletRequest 
    		request,HttpServletResponse response) throws IOException {
    	String fileName=file.getOriginalFilename();
    	System.out.println("文件名称："+fileName);
    	//得到图像后的回调函数
    	String callback=request.getParameter("CKEditorFuncNum");
    	System.out.println(callback);
    	response.setCharacterEncoding("UTF-8");
    	PrintWriter out=response.getWriter();
    	if(!fileName.endsWith(".jpg")&&!fileName.endsWith(".JPG")
    			&&fileName.endsWith(".gif")&&fileName.endsWith(".GIF")
    			&&fileName.endsWith(".png")&&fileName.endsWith(".PNG")
    			&&fileName.endsWith(".bmp")&&fileName.endsWith(".BMP")
    			) {
    		out.println("<script type=\"text/javascript\">");
        	out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'文件格式不对(必须是.gif/.jpg/.png/.bmp等" + ")");
        	out.println("</script>");
    	}else {
    		String imageName=generatorFileName(fileName);
    		file.transferTo(new File(request.getServletContext().getRealPath("")+"/upload/"+imageName));
	    	out.println("<script type=\"text/javascript\">");
	    	out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'/upload/" + imageName + "',''" + ")");
	    	out.println("</script>");
    	}
    	out.flush();
    	out.close();
    }
    
    public String generatorFileName(String fileName) {
    	String fileExt=fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
    	SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
    	String newFileName=format.format(new Date())+fileExt;
    	return newFileName;
    }
    
}