package com.nongziwang.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 
 * @title HttpUtils
 * @description:AsyncHttpClient工具类，执行post,get请求
 * @author Mersens
 * @time 2016年1月6日
 */
public class HttpUtils {
	private static AsyncHttpClient client = new AsyncHttpClient();
	static {
		client.setTimeout(11000); // 设置请求超时
	}

	/**
	 * 
	 * @Title: doGet
	 * @Description: 不带参数的get请求
	 * @author Mersens
	 * @param url
	 * @param res
	 * @throws
	 */
	public static void doGet(String url, AsyncHttpResponseHandler res) {
		client.get(url, res);
	}

	/**
	 * 
	 * @Title: doGet
	 * @Description: 带参数的get请求
	 * @author Mersens
	 * @param url
	 * @param params
	 * @param res
	 * @throws
	 */
	public static void doGet(String url, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.get(url, params, res);
	}

	/**
	 * 
	 * @Title: doPost
	 * @Description: 不带参数的post请求
	 * @author Mersens
	 * @param url
	 * @param params
	 * @param res
	 * @throws
	 */
	public static void doPost(String url, AsyncHttpResponseHandler res) {
		client.post(url, res);
	}

	/**
	 * 
	 * @Title: doPost
	 * @Description: 带参数的post请求
	 * @author Mersens
	 * @param url
	 * @param params
	 * @param res
	 * @throws
	 */
	public static void doPost(String url, RequestParams params,
			AsyncHttpResponseHandler res) {
		client.post(url, params, res);
	}

	/**
	 * 
	 * @Title: cancelRequest
	 * @Description: 取消请求
	 * @author Mersens
	 * @param context
	 * @throws
	 */
	public static void cancelRequest(Context context) {
		client.cancelRequests(context, true);
	}

	/**
	 * 
	 * @Title: getClient 
	 * @Description: 获取AsyncHttpClient实例
	 * @author Mersens
	 * @return client
	 * @throws
	 */
	public static AsyncHttpClient getClient() {
		return client;
	}
}
