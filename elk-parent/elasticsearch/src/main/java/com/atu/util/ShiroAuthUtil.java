package com.atu.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;

import com.atu.commons.shiro.ShiroDbRealm;

public class ShiroAuthUtil {
	/** 
	 *  
	* @Title: clearAuth  
	* @Description: 清空所有资源权限   
	* @return void    返回类型 
	 */  
	public static void clearAuth(){  
	    RealmSecurityManager rsm = (RealmSecurityManager)SecurityUtils.getSecurityManager();  
	    ShiroDbRealm realm = (ShiroDbRealm)rsm.getRealms().iterator().next();  
	    realm.clearAuthz();  
	}  
}
