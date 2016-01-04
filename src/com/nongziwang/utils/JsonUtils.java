package com.nongziwang.utils;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * 
 * @author
 *  Mersens 解析Json工具类
 */
public class JsonUtils {
	/**
	 * @author 
	 * Mersens 
	 * 得到返回码判断是否成功获取数据
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
	 * @author
	 *  Mersens 
	 *  解析Json数据，获取userid
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


}
