package com.nongziwang.activity;

import org.apache.http.Header;
import org.json.JSONException;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.NewsDatialBean;
import com.nongziwang.entity.ZhuanTiBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.view.FlowTextView;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class IndustryInfoDetialActivity extends BaseActivity {
	private boolean isNews = false;
	private String newsid = null;
	private static final String NEWS_URL = AppConstants.SERVICE_ADDRESS
			+ "news/gotoNewsXiangqing";
	private static final String ZHUANTI_URL = AppConstants.SERVICE_ADDRESS
			+ "news/gotoZhuantiXiangqing";
	private TextView title, tv_time,tv_from;
	private FlowTextView ftv;
	private NewsDatialBean newsdatialbean = null;
	private ZhuanTiBean zhuantibean = null;
	private RelativeLayout layout_loading;
	private static final String TAG = "IndustryInfoDetialActivity";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_industry_info_detial);
		isNews = getIntent().getBooleanExtra("isNews", false);
		newsid = getIntent().getStringExtra("newsid");
		initViews();
		initEvent();

	}

	private void initViews() {
		tv_from=(TextView) findViewById(R.id.tv_from);
		layout_loading = (RelativeLayout) findViewById(R.id.layout_loading);
		title = (TextView) findViewById(R.id.title);
		tv_time = (TextView) findViewById(R.id.tv_time);
		ftv = (FlowTextView) findViewById(R.id.ftv);
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "行业资讯", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		setHeadViewBg(R.color.white_color);

	}

	private void initEvent() {
		if (isNews)
			doSearchNewsDetial();

		else
			doSearchZhuanTiDetial();

	}

	public void doSearchNewsDetial() {
		RequestParams params = new RequestParams();
		params.put("newsid", newsid);
		HttpUtils.doPost(NEWS_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ShowToast("newsid为空!");
					} else if ("1".equals(code)) {
						try {
							newsdatialbean = JsonUtils.getNewsDatialInfo(arg2);
							initDatas();
						} catch (JSONException e) {
							e.printStackTrace();
						}

					} else if ("2".equals(code)) {
						ShowToast("该新闻不存在!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ShowToast("请求数据失败!");

			}

			@Override
			public void onFinish() {
				layout_loading.setVisibility(View.GONE);
				super.onFinish();
			}
		});

	}

	public void doSearchZhuanTiDetial() {
		RequestParams params = new RequestParams();
		params.put("zhuantiid", newsid);
		HttpUtils.doPost(ZHUANTI_URL, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ShowToast("zhuantiid为空!");
					} else if ("1".equals(code)) {
						try {
							zhuantibean = JsonUtils.getZhuanTiDatialInfo(arg2);
							initDatas();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else if ("2".equals(code)) {
						ShowToast("没有查询到 该专题信息!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ShowToast("请求数据失败!");

			}

			@Override
			public void onFinish() {
				layout_loading.setVisibility(View.GONE);
				super.onFinish();
			}
		});

	}
	public void initDatas() {
		if (isNews) {
			if (newsdatialbean != null) {
				title.setText(newsdatialbean.getTitle());
				tv_time.setText(newsdatialbean.getAddtime());
				ftv.setText(newsdatialbean.getCenter());
				tv_from.setText("101农资网");
			}
		} else {
			if (zhuantibean != null) {
				title.setText(zhuantibean.getZhuantiname());
				tv_time.setText(zhuantibean.getAddtime());
				ftv.setText(zhuantibean.getCenter());
				tv_from.setText("101农资网");
			}
		}
	}
	
}
