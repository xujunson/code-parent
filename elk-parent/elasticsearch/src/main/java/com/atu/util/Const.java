package com.atu.util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Const {
	public static LoadProperties loadProperties = new LoadProperties("config.properties");
	// 附件上传地址
	public static final String BASE_UPLOAD_PATH = loadProperties.getValue("BASE_UPLOAD_PATH");
	// 附件访问地址
	public static final String ATTACH_URL = loadProperties.getValue("ATTACH_URL");
	public static final String LOCALHOST = loadProperties.getValue("localhost");
	public static final int PORT = Integer.valueOf(loadProperties.getValue("port"));

	public static final String DOMAIN = loadProperties.getValue("domain");
	// 返回序列号
	public static String getSerialNumber() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date()) + new Random().nextInt(10000);
	}

}
