package com.corpdata.app;

import java.util.HashMap;
import java.util.Map;

import com.corpdata.common.utils.OkHttpUtil;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://localhost:8080/system/org/role/listdata";
		Map<String,String> params = new HashMap<String,String>();
		params.put("limit", "1");
		params.put("page", "1");
		String response = OkHttpUtil.post(url, params);
		System.out.println(response);
	}

}
