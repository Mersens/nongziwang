package com.nongziwang.activity;

import org.apache.http.Header;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.view.DialogTips;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingPasswordActivity extends BaseActivity {
	private EditText edt_psd, edt_psd_again;
	private Button btn_finish;
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "findpwd/gotoUpdatePassword";
	private static final String TAG="SettingPasswordActivity";
	private String userid;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_setting_psd);
		userid=getIntent().getStringExtra("params");
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
		String psd = edt_psd.getText().toString().trim();
		String psd_again = edt_psd_again.getText().toString().trim();
		if (TextUtils.isEmpty(psd)) {
			ShowToast("密码不能为空！");
			return;
		}
		if (!psd.equals(psd_again)) {
			ShowToast("两次密码不一致！");
			return;
		}
		RequestParams params = new RequestParams();
		params.put("userid",userid);
		params.put("userpwd", psd);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ShowToast("密码输入不正确!");
					} else if ("1".equals(code)) {
						DialogTips dialog = new DialogTips(SettingPasswordActivity.this,
								"新密码设置成功!", "确定");
						dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finishActivity();
							}							
						});
						dialog.show();
						dialog = null;
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				ShowToast("设置失败！");
				Log.e(TAG, arg2==null?"":arg2);
			}
		});

	}

}
