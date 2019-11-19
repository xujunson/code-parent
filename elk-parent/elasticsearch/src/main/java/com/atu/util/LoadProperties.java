package com.atu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 装配加载properties文件
 */
public class LoadProperties {

	Logger log =LoggerFactory.getLogger(LoadProperties.class);

	private Properties prop = new Properties();

	public LoadProperties(String propertiesName) {
		prop = loadProperty(propertiesName);
	}

	/**
	 * 载入配置文件
	 * 
	 * @return
	 */
	public Properties loadProperty(String propertiesName) {
		try {
			if (propertiesName != null || !"".equals(propertiesName)) {
				InputStream is = LoadProperties.class.getClassLoader().getResourceAsStream(propertiesName);
				if (is == null) {
					is = LoadProperties.class.getClassLoader().getResourceAsStream("/" + propertiesName);
				}
				prop.load(is);
				is.close();
				log.info("载入配置文件:[" + propertiesName + "]成功!");
			}
		} catch (IOException e) {
			log.error("载入配置文件:[" + propertiesName + "]失败!");
			e.printStackTrace();
		}
		return prop;
	}

	/**
	 * 根据key值，获取key对应的value
	 * 
	 * @param key
	 *            key值
	 * @param defaultv
	 *            key对应的value
	 * @return
	 */
	public String getValue(String key, String defaultv) {
		return prop.getProperty(key, defaultv);
	}

	/**
	 * 根据key值，获取key对应的value
	 * 
	 * @param key
	 *            key值
	 * @return
	 */
	public String getValue(String key) {
		return prop.getProperty(key);
	}

	public static void main(String[] args) {
		String proPath = "bean.cfg.properties";
		LoadProperties loadProperties = new LoadProperties(proPath);
		String value = loadProperties.getValue("ANNOTATION_AUTHOR_NAME");
		System.out.println(value);
	}

}
