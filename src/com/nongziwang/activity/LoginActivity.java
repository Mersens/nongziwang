package com.nongziwang.activity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.UserBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.MD5Util;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.utils.StringUtils;
import com.nongziwang.view.DialogWaiting;
import com.nongziwang.view.HeadView.OnLeftClickListener;


public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText edt_name;
	private EditText edt_psd;
	private Button btn_login;
	private TextView tv_forget_psd, tv_user_regist;
	private DialogWaiting dialog;
	private ImageView image_qq, image_weixin, image_sina;

	private static final String LOGIN_URL = AppConstants.SERVICE_ADDRESS
			+ "login/gotoLogin";
	private static final String USERINFO_URL = AppConstants.SERVICE_ADDRESS
			+ "userinfo/getUserInfoById";
	private static final String TAG = "LoginActivity";
	private NongziDao dao;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);
		dao = new NongziDaoImpl(this);
		initViews();
		initEvent();
	}

	private void initViews() {
		edt_name = (EditText) findViewById(R.id.edt_name);
		edt_psd = (EditText) findViewById(R.id.edt_psd);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_forget_psd = (TextView) findViewById(R.id.tv_forget_psd);
		tv_user_regist = (TextView) findViewById(R.id.tv_user_regist);
		image_qq = (ImageView) findViewById(R.id.image_qq);
		image_weixin = (ImageView) findViewById(R.id.image_weixin);
		image_sina = (ImageView) findViewById(R.id.image_sina);
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "用户登录",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			// 登录操作
			doLogin();
			break;
		case R.id.tv_forget_psd:
			intentAction(LoginActivity.this, ForgetPasswordActivity.class);
			break;
		case R.id.tv_user_regist:
			intentAction(LoginActivity.this, RegisterActivity.class);
			finish();
			break;
		case R.id.image_sina:

			break;
		case R.id.image_weixin:
			
			break;
		case R.id.image_qq:

			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		HttpUtils.cancelRequest(this);
	}

	public void doLogin() {
		final String name = edt_name.getText().toString();
		final String psd = edt_psd.getText().toString();
		if (TextUtils.isEmpty(name)) {
			ShowToast("账号不能为空！");
			return;
		}
		if (StringUtils.isContainsSpace(name)) {
			ShowToast("账号包含非法字符！");
			return;
		}
		if (TextUtils.isEmpty(psd)) {
			ShowToast("密码不能为空！");
			return;
		}
		if (StringUtils.isContainsSpace(psd)) {
			ShowToast("密码包含非法字符！");
			return;
		}
		if (!StringUtils.isLeanth(psd)) {
			ShowToast("密码长度至少6位！");
			return;
		}

		if (!StringUtils.isLeanth(name)) {
			ShowToast("账号长度至少6位！");
			return;
		}
		if (!isNetworkAvailable()) {
			ShowToast("没有可用网络，请检查网络设置!");
			return;
		}

		RequestParams params = new RequestParams();
		params.put("zhanghao", name);
		params.put("userpwd", psd);
		HttpUtils.doPost(LOGIN_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				dialog =new DialogWaiting(LoginActivity.this);
				dialog.show();
				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ShowToast("账户或密码为空!");
					} else if ("1".equals(code)) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg2);
							String userid = jsonObject.getString("userid");
							getUserInfo(userid, name, psd);
						} catch (JSONException e) {
							e.printStackTrace();
							dialogDismiss();
							 ShowToast("登录失败！");
						}
					} else if ("2".equals(code)) {
						ShowToast("账户名不存在!");
						dialogDismiss();
					} else if ("3".equals(code)) {
						ShowToast("用户密码输入错误!");
						dialogDismiss();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				    Log.e(TAG, arg2 == null ? "" : arg2);
				    ShowToast("登录失败！");
				    dialogDismiss();
			}
		});
	}

	public void getUserInfo(final String userid, final String name,
			final String psd) {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		HttpUtils.doPost(USERINFO_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("1".equals(code)) {
						try {
							ShowToast("登录成功！"); 
							SharePreferenceUtil.getInstance(
									getApplicationContext()).setUserId(userid);
							SharePreferenceUtil.getInstance(
									getApplicationContext()).setUserName(name);
							SharePreferenceUtil.getInstance(
									getApplicationContext()).setUserPsd(
									MD5Util.MD5(psd));
							UserBean user = JsonUtils.getUserInfo(arg2);
							user.setUserpwd(MD5Util.MD5(psd));
							dao.addUserInfo(user);
							finishActivity();
						} catch (JSONException e) {
							e.printStackTrace();
							dialogDismiss();
						}
					}else{
					    ShowToast("登录失败！");
					    dialogDismiss();
					}
				}
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				    Log.e(TAG, arg2 == null ? "" : arg2);
				    ShowToast("登录失败！");
				    dialogDismiss();
			}
			
			@Override
			public void onFinish() {
				super.onFinish();
				dialogDismiss();
			}
		});
	}

	public void dialogDismiss() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
