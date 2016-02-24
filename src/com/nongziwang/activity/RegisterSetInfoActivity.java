package com.nongziwang.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.StringUtils;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RegisterSetInfoActivity extends BaseActivity {
	private EditText edt_nick, edt_psd, edt_psd_again;
	private Button btn_finish;
	private String userphone = null;
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "register/gotoRegSecond";
	private static final String USERINFO_URL = AppConstants.SERVICE_ADDRESS
			+ "userinfo/getUserInfoById";

	private static final String TAG = "RegisterSetInfoActivity";
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_register_setinfo);
		userphone = getIntent().getStringExtra("params");
		initViews();
		initEvent();
	}

	private void initViews() {
		edt_nick = (EditText) findViewById(R.id.edt_nick);
		edt_psd = (EditText) findViewById(R.id.edt_psd);
		edt_psd_again = (EditText) findViewById(R.id.edt_psd_again);
		btn_finish = (Button) findViewById(R.id.btn_finish);
	}

	private void initEvent() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "�û�ע��",
				new OnLeftClickListener() {
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
	}

	public void doFinish() {
		String nick = edt_nick.getText().toString().trim();
		String psd = edt_psd.getText().toString().trim();
		String psd_again = edt_psd_again.getText().toString().trim();
		if (TextUtils.isEmpty(nick) || TextUtils.isEmpty(psd)
				|| TextUtils.isEmpty(psd_again)) {
			ShowToast("��Ϣ��д�����ƣ�");
			return;
		}
		if (StringUtils.isContainsSpace(nick)
				|| StringUtils.isContainsSpace(psd)
				|| StringUtils.isContainsSpace(psd_again)) {
			ShowToast("��Ϣ��д���ڷǷ��ַ���");
			return;
		}
		if (!psd.equals(psd_again)) {
			ShowToast("�����������벻һ�£�");
			return;
		}
		RequestParams params = new RequestParams();
		params.put("username", nick);
		params.put("userpwd", psd);
		params.put("userphone", userphone);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ShowToast("�û���������Ϊ��!");
					} else if ("1".equals(code)) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg2);
							String userid = jsonObject.getString("userid");
							getUserInfo(userid);
							ShowToast("ע��ɹ���");
							finishActivity();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else if ("2".equals(code)) {
						ShowToast("�û�����ʽ���벻��ȷ!");
					} else if ("3".equals(code)) {
						ShowToast("���û����ѱ�ע��");
					} else if ("4".equals(code)) {
						ShowToast("���볤�Ȳ���ȷ!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2);
			}
		});
	}

	public void getUserInfo(String userid) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		HttpUtils.doPost(USERINFO_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("1".equals(code)) {
					// ��������ɹ���
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2);
			}

			@Override
			public void onFinish() {

				super.onFinish();
			}
		});

	}

}
