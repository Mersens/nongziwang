package com.nongziwang.activity;

import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingPasswordActivity extends BaseActivity{
	private EditText edt_psd,edt_psd_again;
	private Button btn_finish;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_setting_psd);
		initViews();
		initEvent();
	}
	private void initViews() {
		edt_psd=(EditText) findViewById(R.id.edt_psd);
		edt_psd_again=(EditText) findViewById(R.id.edt_psd_again);
		btn_finish=(Button) findViewById(R.id.btn_finish);		
	}
	private void initEvent() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "÷ÿ÷√√‹¬Î", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		btn_finish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowToast("ÕÍ≥…");
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
				
				String psd1=edt_psd.getText().toString();
				String psd2=edt_psd_again.getText().toString();
				if(!TextUtils.isEmpty(psd1) && !TextUtils.isEmpty(psd2)){
					btn_finish.setEnabled(true);
				}else{
					btn_finish.setEnabled(false);
				}
			}
		});
	}

}
