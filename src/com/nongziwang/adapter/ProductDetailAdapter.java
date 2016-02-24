package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;

@SuppressLint("SetJavaScriptEnabled")
public class ProductDetailAdapter extends BaseListAdapter<String>{
	private List<String> list;

	public ProductDetailAdapter(List<String> list, Context context) {
		super(list, context);
        this.list=list;
}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View view=mInflater.inflate(R.layout.layout_product_detail_item, parent,false);
		WebView mWebView=(WebView) view.findViewById(R.id.mWebView);
		mWebView.getSettings().setJavaScriptEnabled(true); 
		mWebView.getSettings().setUseWideViewPort(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setBlockNetworkImage(false);
		mWebView.getSettings().setTextZoom(240);
		mWebView.loadDataWithBaseURL(null,list.get(position), "text/html",  "utf-8", null);
		return view;
	}

}
