package com.nongziwang.activity;

import com.nongziwang.main.R;
import com.nongziwang.service.RegisterCodeTimerService;
import com.nongziwang.utils.RegisterCodeTimer;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
		
		et_tel_number.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String email=et_tel_number.getText().toString();
				if(!TextUtils.isEmpty(email)){
					btn_getcode.setEnabled(true);
				}else{
					btn_getcode.setEnabled(false);
				}
			}
		});
		
		et_code.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				String number=et_tel_number.getText().toString();
				String code=et_code.getText().toString();
				if(!TextUtils.isEmpty(number) && !TextUtils.isEmpty(code)){
					btn_next.setEnabled(true);
				}else{
					btn_next.setEnabled(false);
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		stopService(mIntent);
	}
	

}
