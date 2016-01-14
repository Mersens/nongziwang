package com.nongziwang.activity;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.main.R;
import com.nongziwang.service.RegisterCodeTimerService;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.RegisterCodeTimer;
import com.nongziwang.utils.StringUtils;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ForgetPasswordActivity extends BaseActivity{
	private EditText et_tel_or_email;
	private EditText et_code;
	private Button btn_next;
	private Button btn_getcode;
	private Intent mIntent;
	private Map<String,String> map=new HashMap<String,String>();
	private static final String URL="";
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
		et_tel_or_email=(EditText) findViewById(R.id.et_tel_or_email);
		et_code=(EditText) findViewById(R.id.et_code);
		btn_next=(Button) findViewById(R.id.btn_next);
		btn_getcode=(Button) findViewById(R.id.btn_getcode);
	}
	private void initEvent() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "忘记密码", new OnLeftClickListener() {
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
	public void doNext(){
		String num=et_tel_or_email.getText().toString().trim();
		String code=et_code.getText().toString().trim();
		if(!num.equals(map.get("num"))){
			ShowToast("电话号码不一致！");
			return;
		}
		if(TextUtils.isEmpty(code)){
			ShowToast("验证码不能为空！");
			return;
		}
		if(!code.equals(map.get("code"))){
			ShowToast("验证码输入有误！");
			return;
		}
		intentAction(ForgetPasswordActivity.this,SettingPasswordActivity.class);
	}
	
	
	public void doGetCode(){
		String tel_number=et_tel_or_email.getText().toString().trim();
		if (TextUtils.isEmpty(tel_number)) {
			ShowToast("电话号码或邮箱不能为空！");
			return;
		}
		if(!StringUtils.isMobileNum(tel_number) || !StringUtils.isEmail(tel_number)){
			ShowToast("电话号码或邮箱格式不正确！");
			return;
		}
		if (!isNetworkAvailable()) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}
		map.put("num", tel_number);
		startService(mIntent);
		RequestParams params=new RequestParams();
		params.put("", "");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
	}
	
	
	
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler(){
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
