package com.nongziwang.activity;

import com.nongziwang.main.R;
import com.nongziwang.utils.StringUtils;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterSetInfoActivity extends BaseActivity{
	private EditText edt_nick,edt_psd,edt_psd_again;
	private Button btn_finish;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_register_setinfo);
		initViews();
		initEvent();
	}

	private void initViews() {
		edt_nick=(EditText) findViewById(R.id.edt_nick);
		edt_psd=(EditText) findViewById(R.id.edt_psd);
		edt_psd_again=(EditText) findViewById(R.id.edt_psd_again);
		btn_finish=(Button) findViewById(R.id.btn_finish);
	}

	private void initEvent() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "用户注册", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		btn_finish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				doFinish();
			}
		});
		edt_psd_again.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String nick=edt_nick.getText().toString();
				String psd1=edt_psd.getText().toString();
				String psd2=edt_psd_again.getText().toString();
				if(!TextUtils.isEmpty(psd1) && !TextUtils.isEmpty(psd2) && !TextUtils.isEmpty(nick)){
					btn_finish.setEnabled(true);
				}else{
					btn_finish.setEnabled(false);
				}
			}
		});
	}
	public void doFinish(){
		String nick=edt_nick.getText().toString().trim();
		String psd=edt_psd.getText().toString().trim();
		String psd_again=edt_psd_again.getText().toString().trim();
		if(TextUtils.isEmpty(nick) || TextUtils.isEmpty(psd) || TextUtils.isEmpty(psd_again)){
			ShowToast("信息填写不完善！");
			return;
		}
		if(StringUtils.isContainsSpace(nick)|| StringUtils.isContainsSpace(psd)||StringUtils.isContainsSpace(psd_again)){
			ShowToast("信息填写存在非法字符！");
			return;
		}
		if(!psd.equals(psd_again)){
			ShowToast("两次密码输入不一致！");
			return;
		}
		

	}
	
}
