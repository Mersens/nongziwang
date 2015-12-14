package com.nongziwang.activity;

import com.nongziwang.main.R;
import com.nongziwang.service.RegisterCodeTimerService;
import com.nongziwang.utils.RegisterCodeTimer;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends BaseActivity {
	private EditText et_tel_number;
	private EditText et_code;
	private Button btn_next;
	private Button btn_getcode;
	private Intent mIntent;
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
	
	Handler mCodeHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
				btn_getcode.setText(msg.obj.toString());
				btn_getcode.setClickable(false);
				btn_getcode.setBackgroundResource(R.drawable.btn_gray_bg);
			} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
				btn_getcode.setClickable(true);
				btn_getcode.setText(msg.obj.toString());
				btn_getcode.setBackgroundResource(R.drawable.btn_bg);
			}			
		};
	};
	
	
	private void initViews() {
		et_tel_number=(EditText) findViewById(R.id.et_tel_number);
		et_code=(EditText) findViewById(R.id.et_code);
		btn_next=(Button) findViewById(R.id.btn_next);
		btn_getcode=(Button) findViewById(R.id.btn_getcode);
	}
	private void initEvent() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "用户注册", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		btn_next.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				intentAction(RegisterActivity.this,RegisterSetInfoActivity.class);
				
			}
		});
		btn_getcode.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startService(mIntent);
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
	}
	

}
