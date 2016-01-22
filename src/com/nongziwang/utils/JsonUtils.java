package com.nongziwang.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nongziwang.entity.CompanyBean;
import com.nongziwang.entity.LeiMuBean;
import com.nongziwang.entity.NewsBean;
import com.nongziwang.entity.NewsDatialBean;
import com.nongziwang.entity.PinPaiBean;
import com.nongziwang.entity.ProductBean;
import com.nongziwang.entity.UserBean;
import com.nongziwang.entity.UsesBean;
import com.nongziwang.entity.ZhuanTiBean;

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
		if (jsonObject.has("qq"))
			bean.setQq(jsonObject.getString("qq"));
		else
			bean.setQq("");
		if (jsonObject.has("xingming"))
			bean.setXingming(jsonObject.getString("xingming"));
		else
			bean.setXingming("");

		if (jsonObject.has("addtime"))
			bean.setAddtime(jsonObject.getString("addtime"));
		else
			bean.setAddtime("");

		if (jsonObject.has("gongsiid"))
			bean.setCompanyid(jsonObject.getString("gongsiid"));
		else
			bean.setCompanyid("");

		bean.setHtmlid("");

		return bean;
	}

	public static List<LeiMuBean> getLeiMuByInfo(String str)
			throws JSONException {
		List<LeiMuBean> list = new ArrayList<LeiMuBean>();
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("leimulist"));
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			list.add(new LeiMuBean(obj.getString("leimuid"), obj
					.getString("name"), obj.getString("parentid")));
		}

		return list;

	}

	public static List<NewsBean> getNewsInfo(String str) throws JSONException {
		List<NewsBean> list = new ArrayList<NewsBean>();
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("newslist"));
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			NewsBean bean = new NewsBean();
			bean.setNewsid(obj.getString("newsid"));
			bean.setTitle(obj.getString("title"));
			bean.setCenter(obj.getString("center"));
			bean.setImgsrc(obj.getString("imgsrc"));
			bean.setAddtime(obj.getString("addtime"));
			list.add(bean);
		}

		return list;

	}

	public static List<NewsBean> getZhuanTiInfo(String str)
			throws JSONException {
		List<NewsBean> list = new ArrayList<NewsBean>();
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("zhuantilist"));
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			NewsBean bean = new NewsBean();

			bean.setNewsid(obj.getString("zhuantiid"));
			bean.setTitle(obj.getString("zhuantiname"));
			bean.setCenter(obj.getString("center"));
			bean.setImgsrc(obj.getString("imgsrc"));
			bean.setAddtime(obj.getString("addtime"));
			list.add(bean);
		}
		return list;
	}

	public static List<String> getKeyWordInfo(String str) throws JSONException {
		List<String> list = new ArrayList<String>();
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("keywordslist"));
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			list.add(obj.getString("keywords"));
		}
		return list;

	}

	public static NewsDatialBean getNewsDatialInfo(String str)
			throws JSONException {
		JSONObject jsonObject = new JSONObject(str);
		NewsDatialBean bean = new NewsDatialBean();
		bean.setNewsid(jsonObject.getString("newsid"));
		bean.setTitle(jsonObject.getString("title"));
		bean.setCenter(jsonObject.getString("center"));
		bean.setNewsimg(jsonObject.getString("newsimg"));
		bean.setAddtime(jsonObject.getString("addtime"));
		return bean;
	}

	public static ZhuanTiBean getZhuanTiDatialInfo(String str)
			throws JSONException {
		JSONObject jsonObject = new JSONObject(str);
		ZhuanTiBean bean = new ZhuanTiBean();
		bean.setZhuantiid(jsonObject.getString("zhuantiid"));
		bean.setZhuantiname(jsonObject.getString("zhuantiname"));
		bean.setKeywords(jsonObject.getString("keywords"));
		bean.setCenter(jsonObject.getString("center"));
		bean.setAddtime(jsonObject.getString("addtime"));
		bean.setImgsrc(jsonObject.getString("imgsrc"));
		return bean;
	}

	public static Map<String, List<String>> getIndexImg(String str)
			throws JSONException {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		List<String> bannerlist = new ArrayList<String>();
		List<String> huafeilist = new ArrayList<String>();
		List<String> nongyaolist = new ArrayList<String>();
		List<String> zhongzilist = new ArrayList<String>();
		// banner数据
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("bannerlist"));
		for (int i = 0; i < 3; i++) {
			JSONObject obj = (JSONObject) array.get(0);
			bannerlist.add(obj.getString("banner"));
		}
		map.put("banner", bannerlist);
		// 化肥数据
		JSONArray huafeiarray = new JSONArray(
				jsonObject.getString("huafeilist"));
		for (int i = 0; i < huafeiarray.length(); i++) {
			JSONObject obj = (JSONObject) huafeiarray.get(i);
			huafeilist.add(obj.getString("huafei"));
		}
		map.put("huafei", huafeilist);

		// 农药数据
		JSONArray nongyaoarray = new JSONArray(
				jsonObject.getString("nongyaolist"));
		for (int i = 0; i < nongyaoarray.length(); i++) {
			JSONObject obj = (JSONObject) nongyaoarray.get(i);
			nongyaolist.add(obj.getString("nongyao"));
		}
		map.put("nongyao", nongyaolist);
		// 种子数据
		JSONArray zhongziarray = new JSONArray(
				jsonObject.getString("zhongzilist"));
		for (int i = 0; i < zhongziarray.length(); i++) {
			JSONObject obj = (JSONObject) zhongziarray.get(i);
			zhongzilist.add(obj.getString("zhongzi"));
		}
		map.put("zhongzi", zhongzilist);
		return map;
	}

	public static List<ProductBean> getProductInfo(String str)
			throws JSONException {
		List<ProductBean> list = new ArrayList<ProductBean>();
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("chanpinlist"));
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			ProductBean bean = new ProductBean();
			bean.setChanpinid(obj.getString("chanpinid"));
			bean.setTitle(obj.getString("title"));
			bean.setChanpinimg(obj.getString("chanpinimg"));
			bean.setCityname(obj.getString("cityname"));
			bean.setJiage(obj.getString("jiage"));
			bean.setProvince(obj.getString("province"));
			list.add(bean);
		}
		JSONArray array1 = new JSONArray(
				jsonObject.getString("tuijianpinpailist"));
		for (int i = 0; i < array1.length(); i++) {
			JSONObject obj = (JSONObject) array1.get(i);
			System.out.println(obj.getString("pinpainame"));
		}
		return list;
	}

	public static List<UsesBean> getUsesInfo(String str) throws JSONException {
		List<UsesBean> list = new ArrayList<UsesBean>();
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("yongtulist"));
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			UsesBean bean = new UsesBean();
			bean.setUsesid(obj.getString("yongtuid"));
			bean.setUsesname(obj.getString("yongtuname"));
			list.add(bean);

		}

		return list;

	}

	public static List<PinPaiBean> getPinPaiInfo(String str)
			throws JSONException {
		List<PinPaiBean> list = new ArrayList<PinPaiBean>();
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("pinpailist"));
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			PinPaiBean bean = new PinPaiBean();
			bean.setPinpaiid(obj.getString("pinpaiid"));
			bean.setPinpainame(obj.getString("pinpainame"));
			list.add(bean);

		}
		return list;
	}
	
	public static List<CompanyBean> getCompanyInfo(String str) throws JSONException{
		List<CompanyBean> list = new ArrayList<CompanyBean>();
		JSONObject jsonObject = new JSONObject(str);
		JSONArray array = new JSONArray(jsonObject.getString("gongsilist"));
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			CompanyBean bean=new CompanyBean();
			bean.setChanpinsum(obj.getString("chanpinsum"));
			bean.setDianpuid(obj.getString("dianpuid"));
			bean.setDianputype(obj.getString("dianputype"));
			bean.setGongsiid(obj.getString("gongsiid"));
			bean.setGongsiname(obj.getString("gongsiname"));
			bean.setZhuyingchanpin(obj.getString("zhuyingchanpin"));
			list.add(bean);
		}
		
		return list;
		
	}
	
}
