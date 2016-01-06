package com.nongziwang.activity;

import org.apache.http.Header;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.StringUtils;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.SpotsDialog;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends BaseActivity implements OnClickListener {
	private EditText edt_name;
	private EditText edt_psd;
	private Button btn_login;
	private TextView tv_forget_psd, tv_user_regist;
	private SpotsDialog spotsdialog;
	private ImageView image_qq, image_weixin, image_sina;
	public static Tencent mTencent;
	private SsoHandler mSsoHandler;
	private AuthInfo mAuthInfo;
	private Oauth2AccessToken mAccessToken;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_login);
		mTencent = Tencent.createInstance(AppConstants.APP_ID, this);
		mAuthInfo = new AuthInfo(this, AppConstants.APP_KEY,
				AppConstants.REDIRECT_URL, AppConstants.SCOPE);
		mSsoHandler = new SsoHandler(this, mAuthInfo);
		initViews();
		initEvent();
	}

	private void initViews() {
		spotsdialog = new SpotsDialog(LoginActivity.this, "正在登录", null, true,
				true);
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

		edt_psd.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String name=edt_name.getText().toString();
				String psd=edt_psd.getText().toString();
				if(!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(name)){
					btn_login.setEnabled(true);
				}else{
					btn_login.setEnabled(false);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			//登录操作
			doLogin();
			break;
		case R.id.tv_forget_psd:
			intentAction(LoginActivity.this, ForgetPasswordActivity.class);
			break;
		case R.id.tv_user_regist:
			intentAction(LoginActivity.this, RegisterActivity.class);
			break;
		case R.id.image_sina:
			ShowToast("点击新浪登录");
			onClickSinaLogin();
			break;
		case R.id.image_weixin:
			ShowToast("点击微信登录");
			break;
		case R.id.image_qq:
			// 点击QQ登录
			onClickQQLogin();
			break;
		}
	}

	
	public void doLogin(){

		String name=edt_name.getText().toString();
		String psd=edt_psd.getText().toString();
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
		spotsdialog.show();

		
	}
	
	
	
	public void onClickQQLogin() {
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", loginListener);
		}
	}

	public void onClickSinaLogin() {
		mSsoHandler.authorizeClientSso(new AuthListener());
	}

	class AuthListener implements WeiboAuthListener {

		@Override
		public void onComplete(Bundle values) {
			mAccessToken = Oauth2AccessToken.parseAccessToken(values); // 从
																		// Bundle
																		// 中解析
																		// Token
			if (mAccessToken.isSessionValid()) {

			} else {
				// 当您注册的应用程序签名不正确时，就会收到错误Code，请确保签名正确
				String code = values.getString("code", "");
				ShowToast(code);

			}
		}

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * qq授权登录
	 * 
	 * @param jsonObject
	 */
	public static void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
					&& !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
			}
		} catch (Exception e) {
		}
	}

	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			initOpenidAndToken(values);
		}
	};

	/**
	 * qq登录回调接口
	 * 
	 * @author Mersens
	 *
	 */
	private class BaseUiListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
		}

		@Override
		public void onComplete(Object arg0) {
			if (null == arg0) {
				ShowToast("返回为null");
				return;
			}
			JSONObject jsonResponse = (JSONObject) arg0;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				ShowToast("返回为空");
				return;
			}
			ShowToast(arg0.toString());
			doComplete((JSONObject) arg0);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * 防止登录成功后无法成功回传数据,此方法必须添加
	 * 
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d("TAG", "-->onActivityResult " + requestCode + " resultCode="
				+ resultCode);
		if (requestCode == Constants.REQUEST_LOGIN
				|| requestCode == Constants.REQUEST_APPBAR) {
			Tencent.onActivityResultData(requestCode, resultCode, data,
					loginListener);
		}

		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
}
