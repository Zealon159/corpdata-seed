package com.corpdata.system.security.service;

import java.util.Map;

public interface LoginService {
	public Map<String,Object> doLogin(String uid,String pwd);
}
