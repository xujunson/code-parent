package com.atu.controller.upload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Date;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.atu.util.DateUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atu.controller.base.BaseController;
import com.atu.controller.upload.config.Configurations;
import com.atu.controller.upload.servlet.Range;
import com.atu.controller.upload.servlet.StreamException;
import com.atu.controller.upload.util.IoUtil;
import com.atu.controller.upload.util.TokenUtil;
import com.atu.entity.Attach;
import com.atu.service.AttachService;
/**
 * stream插件上传
 */
@Controller
@RequestMapping("/streamUpload")
public class StreamUploadController extends BaseController{
	private static final Logger log=LoggerFactory.getLogger(StreamUploadController.class);
	@Autowired
	private AttachService attachService;
	static final String FILE_NAME_FIELD = "name";
	static final String FILE_SIZE_FIELD = "size";
	static final String TOKEN_FIELD = "token";
	static final String SERVER_FIELD = "server";
	static final String SUCCESS = "success";
	static final String MESSAGE = "message";
	static final int BUFFER_LENGTH = 10240;
	static final String START_FIELD = "start";
	public static final String CONTENT_RANGE_HEADER = "content-range";
	/**
	 * 跳转到上传页面
	 * @return
	 */
	@RequestMapping("/toUpload")
	public String toUpload(Model model,String method) {
		model.addAttribute("method", method);
		return "jsp/upload/upload1";
	}
	@RequestMapping("/toUpload2")
	public String toUpload2(Model model,String domId,String domIdDisp) {
		model.addAttribute("domId", domId);
		model.addAttribute("domIdDisp", domIdDisp);
		return "jsp/upload/upload2";
	}

	/**
	 * doc 上传
	 * @param model
	 * @param pid
	 * @return
	 */
	@RequestMapping("/toUpload3")
	public String toUpload2(Model model,String pid) {
		model.addAttribute("pid", pid);
		return "jsp/upload/upload3";
	}

	@RequestMapping("/tk")
	public void doToken(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String name = req.getParameter(FILE_NAME_FIELD);
		String size = req.getParameter(FILE_SIZE_FIELD);
		String token = TokenUtil.generateToken(name, size);
		
		PrintWriter writer = resp.getWriter();
		
		JSONObject json = new JSONObject();
		try {
			json.put(TOKEN_FIELD, token);
			if (Configurations.isCrossed())
				json.put(SERVER_FIELD, Configurations.getCrossServer());
			json.put(SUCCESS, true);
			json.put(MESSAGE, "");
		} catch (JSONException e) {
		}
		/** TODO: save the token. */
		
		writer.write(json.toString());
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.GET)
	public void doUploadGET(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		doOptions(req, resp);
		
		final String token = req.getParameter(TOKEN_FIELD);
		final String size = req.getParameter(FILE_SIZE_FIELD);
		final String fileName = req.getParameter(FILE_NAME_FIELD);
		final PrintWriter writer = resp.getWriter();
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		try {
			File f = IoUtil.getTokenedFile(token);
			start = f.length();
			/** file size is 0 bytes. */
			if (token.endsWith("_0") && "0".equals(size) && 0 == start)
				f.renameTo(IoUtil.getFile(fileName));
		} catch (FileNotFoundException fne) {
			message = "Error: " + fne.getMessage();
			success = false;
		} finally {
			try {
				if (success)
					json.put(START_FIELD, start);
				json.put(SUCCESS, success);
				json.put(MESSAGE, message);
			} catch (JSONException e) {}
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}
	
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public void doUploadPOST(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
		doOptions(req, resp);
		final String size = req.getParameter(FILE_SIZE_FIELD);
		final String token = req.getParameter(TOKEN_FIELD);
		final String fileName = req.getParameter(FILE_NAME_FIELD);
		String fileExt = fileName.substring(
                fileName.lastIndexOf(".") + 1).toLowerCase();
		Range range = IoUtil.parseRange(req);
		String attachId="";
		OutputStream out = null;
		InputStream content = null;
		final PrintWriter writer = resp.getWriter();
		
		/** TODO: validate your token. */
		
		JSONObject json = new JSONObject();
		long start = 0;
		boolean success = true;
		String message = "";
		File f = IoUtil.getTokenedFile(token);
		try {
			if (f.length() != range.getFrom()) {
				/** drop this uploaded data */
				throw new StreamException(StreamException.ERROR_FILE_RANGE_START);
			}
			
			out = new FileOutputStream(f, true);
			content = req.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[BUFFER_LENGTH];
			while ((read = content.read(bytes)) != -1)
				out.write(bytes, 0, read);

			start = f.length();
		} catch (StreamException se) {
			success = StreamException.ERROR_FILE_RANGE_START == se.getCode();
			message = "Code: " + se.getCode();
		} catch (FileNotFoundException fne) {
			message = "Code: " + StreamException.ERROR_FILE_NOT_EXIST;
			success = false;
		} catch (IOException io) {
			message = "IO Error: " + io.getMessage();
			success = false;
		} finally {
			IoUtil.close(out);
			IoUtil.close(content);

			/** rename the file */
			if (range.getSize() == start) {
				/** fix the `renameTo` bug */
//				File dst = IoUtil.getFile(fileName);
//				dst.delete();
				// TODO: f.renameTo(dst); 重命名在Windows平台下可能会失败，stackoverflow建议使用下面这句
				try {
					// 先删除
					IoUtil.getFile(fileName).delete();
					attachId=UUID.randomUUID().toString();
					Files.move(f.toPath(), f.toPath().resolveSibling(attachId+fileName));
					System.out.println("TK: `" + token + "`, NE: `" + fileName + "`");
					log.info("TK: `" + token + "`, NE: `" + fileName + "`");
					 saveAttach(fileName,attachId,size);
					/** if `STREAM_DELETE_FINISH`, then delete it. */
					if (Configurations.isDeleteFinished()) {
						IoUtil.getFile(fileName).delete();
					}
				} catch (IOException e) {
					success = false;
					message = "Rename file error: " + e.getMessage();
				}
				
			}
			try {
				if (success) {
					json.put(START_FIELD, start);
				}
				json.put(SUCCESS, success);
				json.put(MESSAGE, message);
				json.put("attachId", attachId);
				json.put("fileType", fileExt);
				json.put("fileName", fileName);
			} catch (JSONException e) {}
			
			writer.write(json.toString());
			IoUtil.close(writer);
		}
	}
	/**
	 * 保存附件
	 * @param fileName
	 */
	protected void saveAttach(String fileName,String attachId,String size) {
		String fileExt = fileName.substring(
                fileName.lastIndexOf(".") + 1).toLowerCase();
        Attach attach=new Attach();
        attach.setId(attachId);
        attach.setCreateTime(new Date());
        attach.setName(fileName);
        attach.setType(fileExt);
        attach.setPath(File.separator+ DateUtil.date2Str2(new Date())+File.separator+attachId+fileName);
        attach.setSize(size);
        attachService.save(attach);
	}
	
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Range,Content-Type");
		resp.setHeader("Access-Control-Allow-Origin", Configurations.getCrossOrigins());
		resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
	}
}
