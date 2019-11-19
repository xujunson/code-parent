package com.atu.commons.shiro;

import com.atu.util.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.atu.util.DigestUtils;

import java.util.UUID;


/**
 * shiro密码加密配置
 *
 */
public class PasswordHash implements InitializingBean {
	private String algorithmName;
	private int hashIterations;

	public String getAlgorithmName() {
		return algorithmName;
	}
	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}
	public int getHashIterations() {
		return hashIterations;
	}
	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasLength(algorithmName, "algorithmName mast be MD5、SHA-1、SHA-256、SHA-384、SHA-512");
	}
	
	public static String toHex(Object source, Object salt) {
		return DigestUtils.hashByShiro("MD5", source, salt, 1);
	}

	public static void main(String[] args) {
		String str = StringUtils.getUUId();
		System.out.println(toHex("12345",str)+"---------"+str);
	}
}

