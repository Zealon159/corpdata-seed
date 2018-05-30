package com.corpdata.system.security.shiro.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import com.corpdata.system.org.entity.OrgUser;

public class UserUtil {
	
	/**
	 * 获取当前登录用户对象
	 * @return
	 */
	public static OrgUser getCurrentOrgUser(){
		OrgUser user = null;
		Subject subject = SecurityUtils.getSubject();
		if(subject!=null){
			 user = (OrgUser)subject.getPrincipal();
		}
		return user;
	}
	
	/**
	 * 获取当前登录用户ID
	 * @return
	 */
	public static String getCurrentUserid(){
		String userId = "";
		Subject subject = SecurityUtils.getSubject();
		if(subject!=null){
			userId = ((OrgUser)subject.getPrincipal()).getUserid();
		}
		return userId;
	}
	
	/**
	 * 获取当前登录用户名称
	 * @return
	 */
	public static String getCurrentUserName(){
		String userName = "";
		Subject subject = SecurityUtils.getSubject();
		if(subject!=null){
			userName = ((OrgUser)subject.getPrincipal()).getUserName();
		}
		return userName;
	}
}
