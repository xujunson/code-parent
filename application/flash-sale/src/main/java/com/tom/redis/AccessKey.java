package com.tom.redis;

/**
 * 访问控制
 */
public class AccessKey extends BasePrefix{

	private AccessKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static AccessKey withExpire(int expireSeconds) {
		return new AccessKey(expireSeconds, "access");
	}
	
}
