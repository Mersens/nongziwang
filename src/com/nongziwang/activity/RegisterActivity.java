package com.nongziwang.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.service.RegisterCodeTimerService;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.RegisterCodeTimer;
import com.nongziwang.utils.StringUtils;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @title RegisterActivity
 * @description:用户注册
 * @author Mersens
 * @time 2016年1月18日
 */
public class RegisterActivity extends BaseActivity {
	private EditText et_tel_number;
	private EditText et_code;
	private Button btn_next;
	private Button btn_getcode;
	private Intent mIntent;
	private Map<String, String> map = new HashMap<String, String>();
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "send/sendrandByPhone";
	private static final String TAG = "RegisterActivity";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_register);
		RegisterCodeTimerService.setHandler(mCodeHandler);
		mIntent = new Intent(RegisterActivity.this,
				RegisterCodeTimerService.class);
		initViews();
		initEvent();
	}

	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
				btn_getcode.setText(msg.obj.toString());
				btn_getcode.setEnabled(false);
			} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
				btn_getcode.setEnabled(true);
				btn_getcode.setText(msg.obj.toString());
			}
		};
	};

	private void initViews() {
		et_tel_number = (EditText) findViewById(R.id.et_tel_number);
		et_code = (EditText) findViewById(R.id.et_code);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_getcode = (Button) findViewById(R.id.btn_getcode);
	}

	private void initEvent() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "用户注册",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doNext();
			}
		});
		btn_getcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doGetCode();
			}
		});
	}

	public void doNext() {
		String num = et_tel_number.getText().toString().trim();
		String code = et_code.getText().toString().trim();
		if (TextUtils.isEmpty(code)) {
			ShowToast("验证码不能为空！");
			return;
		}
		if (!num.equals(map.get("num"))) {
			ShowToast("电话号码不一致！");
			return;
		}

		if (!code.equals(map.get("code"))) {
			ShowToast("验证码输入有误！");
			return;
		}
		intentAction(RegisterActivity.this, RegisterSetInfoActivity.class,num);
		finish();
	}

	public void doGetCode() {
		String tel_number = et_tel_number.getText().toString().trim();
		if (TextUtils.isEmpty(tel_number)) {
			ShowToast("电话号码不能为空！");
			return;
		}
		if (!StringUtils.isMobileNum(tel_number)) {
			ShowToast("电话号码格式不正确！");
			return;
		}
		if (!isNetworkAvailable()) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		map.put("num", tel_number);
		startService(mIntent);
		RequestParams params = new RequestParams();
		params.put("userphone", tel_number);
		params.put("msgflag", "1");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@SuppressWarnings("deprecation")
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ShowToast("手机号码格式不正确!");
					} else if ("1".equals(code)) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg2);
							String yzm = jsonObject.getString("yanzhengma");
							if (!TextUtils.isEmpty(yzm)) {
								map.put("code", yzm);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else if ("2".equals(code)) {
						ShowToast("该手机号码已被注册!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				    Log.e(TAG, arg2==null?"":arg2);
			}

		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
	}

}
