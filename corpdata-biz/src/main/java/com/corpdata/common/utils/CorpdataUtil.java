package com.corpdata.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * 元霆工具类
 * @author zealon
 *
 */
public class CorpdataUtil {

	
	/**
	 * 获取32位随机字符，可做ID
	 * @return
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * 获取select Json
	 * @param list
	 * @return
	 */
	public static String getComboxJson(List<Map<String,Object>> list){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		int i = 0;
		for(Map<String,Object> obj : list){
			if(i>0){sb.append(",");}
			sb.append("{\"id\":\"").append(obj.get("id")).append("\",\"text\":\"").append(obj.get("text")).append("\"}");
			i++;
		}
		sb.append("]");
		return sb.toString();
	}
	
	/**
	 * 获取组织部门select Json
	 * @param list
	 * @return
	 */
	public static String getOrgDeptComboxJson(List<Map<String,Object>> list,boolean hasRoot){
		StringBuffer sb = new StringBuffer();
		int j = 0;
		sb.append("[");
		if(hasRoot){
			sb.append("{\"id\":\"").append("root").append("\",\"text\":\"").append("所有").append("\"}");
			j++;
		}
		for(Map<String,Object> obj : list){
			String folderid = (String)obj.get("folderid");
			String prefix = "";
			for(int i=0;i<folderid.length();i++){
				prefix+="-";
			}
			if(j>0){sb.append(",");}
			sb.append("{\"id\":\"").append(obj.get("id")).append("\",\"text\":\"").append(prefix+obj.get("text")).append("\"}");
			j++;
		}
		sb.append("]");
		return sb.toString();
	}
	/**
	 * 格式化时间
	 * @param sdate
	 * @return
	 */
	public static Date getDate(String sdate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			return sdf.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 格式化时间
	 * @param sdate
	 * @return
	 */
	public static Date getDateTime(String sdate){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		sdf.setLenient(false);
		try {
			return sdf.parse(sdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	// 获取当天时间   日期格式
    public static String getNowTime(String dateformat) {  
        Date now = new Date();  
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式  
        String hehe = dateFormat.format(now);  
        return hehe;  
    }  
    
    /**
     * 比较两个时间，1大于2返回1 
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compare_date(String DATE1, String DATE2) {
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
    /**
     * 根据时间增加月份
     * @param validatetime需要增加的时间
     * @param renewalsdata增加的月数
     * @return
     * @throws Exception
     */
    public static String addMonth(String validatetime,int renewalsdata) throws Exception {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	Date now = sdf.parse(validatetime);
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(now);
    	System.out.println(sdf.format(calendar.getTime()));
    	calendar.add(Calendar.MONTH, renewalsdata);
    	return sdf.format(calendar.getTime());
    }
    /**
     * 计算两个时间
     * @param fromDate
     * @param toDate
     * @param type 1:返回相差年  2：返回相差月 3：返回相差天
     * @return
     */
    public static int dayCompare(Date fromDate,Date toDate,int type){
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);
        //只要年月
        int fromYear = from.get(Calendar.YEAR);
        int fromMonth = from.get(Calendar.MONTH);
        int toYear = to.get(Calendar.YEAR);
        int toMonth = to.get(Calendar.MONTH);
        int year = toYear  -  fromYear;
        int month = toYear *  12  + toMonth  -  (fromYear  *  12  +  fromMonth);
        int day = (int) ((to.getTimeInMillis()  -  from.getTimeInMillis())  /  (24  *  3600  *  1000));
        if(type==1){
        	return year;
        }else if(type==2){
        	return month;
        }else if(type==3){
        	return day;
        }else{
        	return 0;
        }
        
        
    }
    
    /**
     * @author cy
     * 把字符串切分为字LinkedHashSet集合对像同时会去掉重复值
     * @param str
     * @param key
     * @return
     */
    public static LinkedHashSet<String> splitAsLinkedSet(String str, String key){
      String[] strArray = StringUtils.split(str, key);
      LinkedHashSet<String> set = new LinkedHashSet<String>(strArray.length);
      for (String item : strArray) {
        set.add(item);
      }
      return set;
    }
    
	/**
	 * Set转字符串同时去掉空值
	 *
	 * @param set
	 * @param key 分隔字符串
	 * @return 返回逗号分隔的字符串
	 */
    public static String join(Set<String> set, String key)
    {
      StringBuilder fdNameList = new StringBuilder();
      int i = 0;
      set.remove("");
      for (String item : set) {
        if (i == 0) {
          fdNameList.append(item);
          i = 1;
        } else {
          fdNameList.append(key + item);
        }
      }
      return fdNameList.toString();
    }
    
  //去除重复值
    public static String getRemoveDuplicateVals (String strs){
        LinkedHashSet<String> set = null;
        set = splitAsLinkedSet(strs,",");
        String newstrs = join(set,",");
        return newstrs;
    }
    
	private static boolean isEmpty(String string) {
		return (string == null) || (string.equals("null"))
				|| (string.length() == 0);
	}

	private static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	public static boolean isBlank(String string) {
		return (isEmpty(string)) || (string.trim().length() == 0);
	}

	public static boolean isNotBlank(String string) {
		return !isBlank(string);
	}
    
	public static String httpGet(String url, String charset) throws Exception {
		if (isBlank(charset)) {
			charset = "UTF-8";
		}

		HttpGet httpget = new HttpGet(url);
		CloseableHttpClient httpclient = HttpClients.custom().build();
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String str = streamToString(instream, charset);
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			response.close();
		}
		response.close();

		return "";
	}

	public static String httpGet(String url) {
		try {
			return httpGet(url, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String httpPost(String url, String params) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpclient = HttpClients.custom().build();
		StringEntity myEntity = new StringEntity(params,
				ContentType.APPLICATION_JSON);
		httpPost.setEntity(myEntity);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {
			HttpEntity rsEntity = response.getEntity();
			if (rsEntity != null) {
				InputStream instream = rsEntity.getContent();
				String str = streamToString(instream, "UTF-8");
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			response.close();
		}
		response.close();

		return "";
	}

	public static String httpPost(String url, HashMap<String, String> params)
			throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpclient = HttpClients.custom().build();
		List<BasicNameValuePair> formParams = new ArrayList<BasicNameValuePair>();
		for (String keyName : params.keySet()) {
			formParams.add(new BasicNameValuePair(keyName, (String) params
					.get(keyName)));
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams,
				"UTF-8");
		httpPost.setEntity(entity);

		CloseableHttpResponse response = httpclient.execute(httpPost);
		try {
			HttpEntity rsEntity = response.getEntity();
			if (rsEntity != null) {
				InputStream instream = rsEntity.getContent();
				String str = streamToString(instream, "UTF-8");
				return str;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			response.close();
		}
		response.close();

		return "";
	}

	public static String streamToString(InputStream is, String charset)
			throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				charset));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null)
				sb.append(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	//字符串转为map
	public static HashMap<String, String> jsonStr2Map(String jsonStr) {
		JSONObject jsonobj = JSON.parseObject(jsonStr);
		HashMap<String, String> map = new HashMap<String, String>(jsonobj
				.size());
		for (String fdName : jsonobj.keySet()) {
			map.put(fdName, jsonobj.getString(fdName));
		}
		return map;
	}
}
