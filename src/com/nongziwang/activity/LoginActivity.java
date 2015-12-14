package com.nongziwang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.SpotsDialog;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText edt_name;
	private EditText edt_psd;
	private Button btn_login;
	private TextView tv_forget_psd, tv_user_regist;
	private SpotsDialog spotsdialog;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);
		initViews();
		initEvent();

	}

	private void initViews() {
		spotsdialog=new SpotsDialog(LoginActivity.this);
		edt_name = (EditText) findViewById(R.id.edt_name);
		edt_psd = (EditText) findViewById(R.id.edt_psd);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_forget_psd = (TextView) findViewById(R.id.tv_forget_psd);
		tv_user_regist = (TextView) findViewById(R.id.tv_user_regist);

	}

	private void initEvent() {
		edt_name.setOnClickListener(this);
		edt_psd.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		tv_forget_psd.setOnClickListener(this);
		tv_user_regist.setOnClickListener(this);
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "ÓÃ»§µÇÂ¼", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edt_name:

			break;
		case R.id.edt_psd:

			break;
		case R.id.btn_login:
			spotsdialog.show();
			break;
		case R.id.tv_forget_psd:

			break;
		case R.id.tv_user_regist:
			intentAction(LoginActivity.this,RegisterActivity.class);
			break;
		}
	}

}
