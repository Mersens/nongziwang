package com.nongziwang.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
	private ImageView image_qq,image_weixin,image_sina;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);
		initViews();
		initEvent();
	}

	private void initViews() {
		spotsdialog=new SpotsDialog(LoginActivity.this,"ÕýÔÚµÇÂ¼",null,true,true);
		edt_name = (EditText) findViewById(R.id.edt_name);
		edt_psd = (EditText) findViewById(R.id.edt_psd);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_forget_psd = (TextView) findViewById(R.id.tv_forget_psd);
		tv_user_regist = (TextView) findViewById(R.id.tv_user_regist);
		image_qq=(ImageView) findViewById(R.id.image_qq);
		image_weixin=(ImageView) findViewById(R.id.image_weixin);
		image_sina=(ImageView) findViewById(R.id.image_sina);

	}

	private void initEvent() {
		image_qq.setOnClickListener(this);
		image_weixin.setOnClickListener(this);
		image_sina.setOnClickListener(this);
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
			ShowToast("µã»÷Íü¼ÇÃÜÂë");
			break;
		case R.id.tv_user_regist:
			intentAction(LoginActivity.this,RegisterActivity.class);
			break;
		case R.id.image_sina:
            ShowToast("µã»÷ÐÂÀËµÇÂ¼");
			break;
		case R.id.image_weixin:
			ShowToast("µã»÷Î¢ÐÅµÇÂ¼");
			break;
		case R.id.image_qq:
			ShowToast("µã»÷QQµÇÂ¼");
			break;
		}
	}

}
