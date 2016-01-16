package com.nongziwang.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 
 * @title HttpUtils
 * @description:AsyncHttpClient�����ִ࣬��post,get����
 * @author Mersens
 * @time 2016��1��6��
 */
public class HttpUtils {
	private static AsyncHttpClient client = new AsyncHttpClient();
	static {
		client.setTimeout(11000); // ��������ʱ
	}

	/**
	 * 
	 * @Title: doGet
	 * @Description: ����������get����
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
	 * @Description: ��������get����
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
	 * @Description: ����������post����
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
	 * @Description: ��������post����
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
	 * @Description: ȡ������
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
	 * @Description: ��ȡAsyncHttpClientʵ��
	 * @author Mersens
	 * @return client
	 * @throws
	 */
	public static AsyncHttpClient getClient() {
		return client;
	}
}
