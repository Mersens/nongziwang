package com.nongziwang.activity;

import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends BaseActivity {
	private WebView mWebView;
	private ProgressBar progress;
	private String path;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_webview);
		path = getIntent().getStringExtra("params");
		initViews();
		initEvents();
	}

	private void initViews() {
		mWebView = (WebView) findViewById(R.id.webView);
		progress = (ProgressBar) findViewById(R.id.progressBar);
		setLeftViewMethod(R.drawable.ic_menu_back, new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		setHeadViewBg(R.color.base_color_text_gray);

	}

	private void initEvents() {

		WebSettings settings = mWebView.getSettings();
		settings.setSupportZoom(true);
		settings.setUseWideViewPort(true);
		settings.setLoadWithOverviewMode(true);
		settings.setJavaScriptEnabled(true);
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		mWebView.loadUrl(path);
		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

		});
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress == 100) {
					progress.setVisibility(View.GONE);
				}
				progress.setProgress(newProgress);
			}
		});

	}

}
