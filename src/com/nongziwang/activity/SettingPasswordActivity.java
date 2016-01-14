package com.nongziwang.activity;

import org.apache.http.Header;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingPasswordActivity extends BaseActivity {
	private EditText edt_psd, edt_psd_again;
	private Button btn_finish;
	private static final String URL="";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_setting_psd);
		initViews();
		initEvent();
	}

	private void initViews() {
		edt_psd = (EditText) findViewById(R.id.edt_psd);
		edt_psd_again = (EditText) findViewById(R.id.edt_psd_again);
		btn_finish = (Button) findViewById(R.id.btn_finish);
	}

	private void initEvent() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "重置密码",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
		btn_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doFinish();
			}
		});

	}

	public void doFinish() {
		String psd=edt_psd.getText().toString().trim();
		String psd_again=edt_psd_again.getText().toString().trim();
		if(TextUtils.isEmpty(psd) || TextUtils.isEmpty(psd_again)){
			ShowToast("密码不能为空！");
			return;
		}
		if(!psd.equals(psd_again)){
			ShowToast("两次密码不一致！");
			return;
		}
		RequestParams params=new RequestParams();
		params.put("", "");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				
			}
		});
		
	}

}
