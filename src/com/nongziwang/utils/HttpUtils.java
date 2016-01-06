package com.nongziwang.utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtils {
	private static AsyncHttpClient client = new AsyncHttpClient(); 
	static {
		client.setTimeout(11000); 
	}

	public static void doGet(String urlString, AsyncHttpResponseHandler res) 
	{
		client.get(urlString, res);
	}

	public static void doGet(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) 
	{
		client.get(urlString, params, res);
	}

	
	public static void doPost(String urlString, AsyncHttpResponseHandler res) 
	{
		client.post(urlString, res);
	}

	public static void doPost(String urlString, RequestParams params,
			AsyncHttpResponseHandler res) 
	{
		client.post(urlString, params, res);
	}
	
	public static AsyncHttpClient getClient() {
		return client;
	}
	
	public static void cancelRequest(Context context){
		client.cancelRequests(context, true);
	}
}
