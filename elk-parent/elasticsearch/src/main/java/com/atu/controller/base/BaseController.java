package com.atu.controller.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.atu.commons.shiro.ShiroUser;
import com.atu.util.Charsets;
import com.atu.util.StringEscapeEditor;
import com.atu.util.URLUtils;
import com.atu.util.result.Result;

public abstract class BaseController {
	/**
	 * Logger for this class
	 */
	protected Logger logger = LogManager.getLogger(getClass());
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder) {
		//自动转换日期字段
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
		//防止XSS攻击
		binder.registerCustomEditor(String.class, new StringEscapeEditor());
	}
	
	 /**
     * 获取当前登录用户对象
     * @return {ShiroUser}
     */
    public ShiroUser getShiroUser() {
        return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登录用户id
     * @return {Long}
     */
    public String getUserId() {
        return this.getShiroUser().getId();
    }

    /**
     * 获取当前登录用户名
     * @return {String}
     */
    public String getStaffName() {
        return this.getShiroUser().getName();
    }

	/**
	 * ajax失败
	 * @param msg 失败消息
	 * @return
	 */
	public Object renderError(String msg) {
		Result result=new Result();
		result.setMsg(msg);
		result.setSuccess(false);
		return result;
	}
	/**
	 * ajax成功
	 * @param msg 成功消息
	 * @return
	 */
	public Object renderSuccess(String msg) {
		Result result=new Result();
		result.setMsg(msg);
		result.setSuccess(true);
		return result;
	}
	/**
	 * ajax 成功
	 * @return
	 */
	public Object renderSuccess() {
		Result result=new Result();
		result.setSuccess(true);
		return result;
	}
	/**
	 * ajax 失败
	 * @return
	 */
	public Object renderError() {
		Result result=new Result();
		result.setSuccess(false);
		return result;
	}
	/**
	 * ajax成功
	 * @param obj 成功对象
	 * @return
	 */
	public Object renderSuccess(Object obj) {
		Result result=new Result();
		result.setSuccess(true);
		result.setObj(obj);
		return result;
	}
	
	public Object renderSuccess(String msg,Object obj) {
		Result result=new Result();
		result.setSuccess(true);
		result.setMsg(msg);
		result.setObj(obj);
		return result;
	}
	/**
	 * redirect跳转
	 * @param url 目标url
	 * @return
	 */
	public String redirect(String url) {
		return new StringBuilder("redirect:").append(url).toString();
	}
	/**
	 * 下载文件
	 * @param file 文件
	 */
	public ResponseEntity<Resource> download(File file) {
		String fileName = file.getName();
		return download(file, fileName);
	}
	
	/**
	 * 下载
	 * @param file 文件
	 * @param fileName 生成的文件名
	 * @return {ResponseEntity}
	 */
	public ResponseEntity<Resource> download(File file, String fileName) {
		Resource resource = new FileSystemResource(file);
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String header = request.getHeader("User-Agent");
		// 避免空指针
		header = header == null ? "" : header.toUpperCase();
		HttpStatus status;
		if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
			fileName = URLUtils.encodeURL(fileName, Charsets.UTF_8);
			status = HttpStatus.OK;
		} else {
			fileName = new String(fileName.getBytes(Charsets.UTF_8), Charsets.ISO_8859_1);
			status = HttpStatus.CREATED;
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", fileName);
		return new ResponseEntity<Resource>(resource, headers, status);
	}
	/**
	 * 
	 * @param request
	 * @param downFileName 下载文件名
	 * @param path 文件所在的基路径
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<byte[]>download(HttpServletRequest request,String downFileName,String path) throws IOException{
		//转化编码
		downFileName=URLDecoder.decode(downFileName,"UTF-8");
		System.out.println();
		//获取下载文件所在的文件夹
		File file=new File(path,downFileName);
		//与Response相关的响应信息头部
		HttpHeaders headers=new HttpHeaders();
			 headers.setContentDispositionFormData("attachment",processFileName(request,downFileName));
			 headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.OK);
	}
	/**
	 * 
	 * @param request
	 * @param path 文件所在的基路径
	 * @return
	 * @throws IOException
	 */
	public ResponseEntity<byte[]>download(HttpServletRequest request,String path) throws IOException{
		String downFileName=request.getParameter("downFileName");
		System.out.println(downFileName);
		//转化编码
		downFileName=URLDecoder.decode(downFileName,"UTF-8");
		System.out.println(downFileName);
		//获取下载文件所在的文件夹
		File file=new File(path,downFileName);
		//与Response相关的响应信息头部
		HttpHeaders headers=new HttpHeaders();
			 headers.setContentDispositionFormData("attachment",processFileName(request,downFileName));
			 headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.OK);
	}
	//解决中文请求参数传入不同浏览器编码不同的问题
	public static String processFileName(HttpServletRequest request, String fileNames) {  
	       String codedfilename = null;  
	       try {  
	           String agent = request.getHeader("USER-AGENT");  
	           if (null != agent && -1 != agent.indexOf("MSIE") || null != agent  
	                   && -1 != agent.indexOf("Trident")) {// ie  
	  
	               String name = java.net.URLEncoder.encode(fileNames, "UTF8");  
	  
	               codedfilename = name;  
	           } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等  
	  
	  
	               codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");  
	           }  
	       } catch (Exception e) {  
	           e.printStackTrace();  
	       }  
	       return codedfilename;  
	   }  
	
	
	
	
	public  void download(String fileName, String filePath,
    		HttpServletRequest request, HttpServletResponse response) 
    		throws Exception {
    		    //设置响应头和客户端保存文件名
    		    response.setCharacterEncoding("utf-8");
    		    response.setContentType("multipart/form-data");
    		    response.setHeader("Content-Disposition", "attachment;fileName=" + processFileName(request,fileName));
    		    //用于记录以完成的下载的数据量，单位是byte
    		    long downloadedLength = 0L;
    		    try {
    		        //打开本地文件流
    		        InputStream inputStream = new FileInputStream(filePath);
    		        //激活下载操作
    		        OutputStream os = response.getOutputStream();

    		        //循环写入输出流
    		        byte[] b = new byte[2048];
    		        int length;
    		        while ((length = inputStream.read(b)) > 0) {
    		            os.write(b, 0, length);
    		            downloadedLength += b.length;
						os.flush();
    		        }
    		        // 这里主要关闭。
    		        os.close();
    		        inputStream.close();
    		    } catch (Exception e){
    		        throw e;
    		    }
    		}
	
}
