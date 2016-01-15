package com.nongziwang.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.nongziwang.entity.UserBean;

/**
 * 
 * @title JsonUtils
 * @description:解析Json工具类
 * @author Mersens
 * @time 2016年1月8日
 */
public class JsonUtils {
	/**
	 * @author Mersens 得到返回码判断是否成功获取数据
	 * @param str
	 * @return
	 */
	public static String getCode(String str) {
		String result = null;
		try {
			JSONObject jsonObject = new JSONObject(str);
			result = jsonObject.getString("code");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * @author Mersens 解析Json数据，获取userid
	 * @param str
	 * @return
	 */
	public static String getUserId(String str) {
		String result = null;
		try {
			JSONObject jsonObject = new JSONObject(str);
			result = jsonObject.getString("userid");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * @author Mersens 解析Json数据，获取用户头像地址imgSrc
	 * @param str
	 * @return
	 */
	public static String getImgSrc(String str) {
		String result = null;
		try {
			JSONObject jsonObject = new JSONObject(str);
			result = jsonObject.getString("imgsrc");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public static UserBean getUserInfo(String str) throws JSONException {
		UserBean bean = new UserBean();
		JSONObject jsonObject = new JSONObject(str);
		bean.setUserid(jsonObject.getString("userid"));
		bean.setUsername(jsonObject.getString("username"));
		bean.setUserphone(jsonObject.getString("userphone"));
		bean.setTouxiang(jsonObject.getString("touxiang"));
		bean.setUserpwd("");
		bean.setQq("");
		bean.setXingming("");
		bean.setAddtime("");
		bean.setHtmlid("");
		bean.setCompanyid("");
		return bean;
	}

}
