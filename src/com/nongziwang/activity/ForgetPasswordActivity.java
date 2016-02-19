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

public class ForgetPasswordActivity extends BaseActivity {
	private EditText et_tel_or_email;
	private EditText et_code;
	private Button btn_next;
	private Button btn_getcode;
	private Intent mIntent;
	private Map<String, String> map = new HashMap<String, String>();
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "send/sendrandByPhone";
	private static final String FIRSTURL = AppConstants.SERVICE_ADDRESS
			+ "findpwd/gotoFindPassword";
	private static final String TAG = "ForgetPasswordActivity";
	private String userid=null;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_forget_psd);
		RegisterCodeTimerService.setHandler(mCodeHandler);
		mIntent = new Intent(ForgetPasswordActivity.this,
				RegisterCodeTimerService.class);
		initViews();
		initEvent();

	}

	private void initViews() {
		et_tel_or_email = (EditText) findViewById(R.id.et_tel_or_email);
		et_code = (EditText) findViewById(R.id.et_code);
		btn_next = (Button) findViewById(R.id.btn_next);
		btn_getcode = (Button) findViewById(R.id.btn_getcode);
	}

	private void initEvent() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "忘记密码",
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
		String num = et_tel_or_email.getText().toString().trim();
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
		if(TextUtils.isEmpty(userid)){
			return;
		}
		intentAction(ForgetPasswordActivity.this, SettingPasswordActivity.class,userid);
		finish();
	}

	public void doGetCode() {
		final String tel_number = et_tel_or_email.getText().toString().trim();
		if (TextUtils.isEmpty(tel_number)) {
			ShowToast("电话号码或邮箱不能为空！");
			return;
		}
		if (!StringUtils.isMobileNum(tel_number)) {
			ShowToast("电话号码不正确！");
			return;
		}

		map.put("num", tel_number);
		startService(mIntent);
		RequestParams params = new RequestParams();
		params.put("userphone", tel_number);
		params.put("msgflag", "2");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
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
								getUsetid(tel_number);
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
				    ShowToast("获取验证码失败！");
				    Log.e(TAG, arg2==null?"":arg2);
			}

		});

		
	}

	public void getUsetid(String tel_number){
		RequestParams params1 = new RequestParams();
		params1.put("userphone", tel_number);
		params1.put("yanzhengma", map.get("code"));
		HttpUtils.doPost(FIRSTURL, params1, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				System.out.println(arg2);
				String code=JsonUtils.getCode(arg2);
				if(code.equals("1")){
					try {
						JSONObject jsonObject = new JSONObject(arg2);
						userid = jsonObject.getString("userid");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
			    Log.e(TAG, arg2==null?"":arg2);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
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
}
