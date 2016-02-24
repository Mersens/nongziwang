package com.nongziwang.fragment;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.RegisterActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.service.RegisterCodeTimerService;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.RegisterCodeTimer;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.utils.StringUtils;

public class GetCodeForNewPhoneNumberFragment extends BaseFragment {
	private View view;
	private Button btn_getcode, btn_ok;
	private EditText edt_new_tel, edt_code;
	private static final String TAG = "GetCodeForNewPhoneNumberFragment";
	private static final String UPDATEURL = AppConstants.SERVICE_ADDRESS
			+ "userinfo/updateUserPhone";
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "send/sendrandByPhone";
	private String userid = null;
	private Intent mIntent;
	private Map<String, String> map = new HashMap<String, String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_new_phone_number, container,
				false);
		initViews();
		initEvent();
		return view;
	}

	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
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
		edt_new_tel = (EditText) view.findViewById(R.id.edt_new_tel);
		edt_code = (EditText) view.findViewById(R.id.edt_code);
		btn_getcode = (Button) view.findViewById(R.id.btn_getcode);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);

	}

	private void initEvent() {
		mIntent = new Intent(getActivity(),
				RegisterCodeTimerService.class);
		userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserId();
		RegisterCodeTimerService.setHandler(mCodeHandler);
		btn_getcode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doGeoCode();

			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doFinish();
			}
		});

	}

	public void doGeoCode() {
		String num = edt_new_tel.getText().toString().trim();
		if (TextUtils.isEmpty(num)) {
			Toast.makeText(getActivity(), "电话号码不能为空！", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (!StringUtils.isMobileNum(num)) {
			Toast.makeText(getActivity(), "电话号码格式不正确！", Toast.LENGTH_LONG)
					.show();
			return;
		}

		map.put("num", num);

		getActivity().startService(mIntent);
		RequestParams params = new RequestParams();
		params.put("userphone", num);
		params.put("msgflag", "1");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@SuppressWarnings("deprecation")
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						Toast.makeText(getActivity(), "电话号码格式不正确！",
								Toast.LENGTH_LONG).show();
					} else if ("1".equals(code)) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg2);
							String yzm = jsonObject.getString("yanzhengma");
							if (!TextUtils.isEmpty(yzm)) {
								map.put("code", yzm);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else if ("2".equals(code)) {
						Toast.makeText(getActivity(), "该手机号码已被注册!",
								Toast.LENGTH_LONG).show();
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

	public void doFinish() {
		String num = edt_new_tel.getText().toString().trim();
		String code = edt_code.getText().toString().trim();
		if (TextUtils.isEmpty(code)) {
			Toast.makeText(getActivity(), "验证码不能为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if (!num.equals(map.get("num"))) {
			Toast.makeText(getActivity(), "电话号码不一致！", Toast.LENGTH_LONG).show();
			return;
		}

		if (!code.equals(map.get("code"))) {
			Toast.makeText(getActivity(), "验证码输入有误！", Toast.LENGTH_LONG).show();
			return;
		}
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("userphone", num);
		params.put("yanzhengma", code);
		HttpUtils.doPost(UPDATEURL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						Toast.makeText(getActivity(), "用户id或手机或验证码为空!",
								Toast.LENGTH_LONG).show();

					} else if ("1".equals(code)) {
						Toast.makeText(getActivity(), " 新手机绑定成功！",
								Toast.LENGTH_LONG).show();
						getActivity().stopService(mIntent);
						getActivity().finish();
						getActivity().overridePendingTransition(R.anim.right_in,
								R.anim.right_out);
					} else if ("2".equals(code)) {
						Toast.makeText(getActivity(), "用户信息不存在！",
								Toast.LENGTH_LONG).show();

					} else if ("3".equals(code)) {
						Toast.makeText(getActivity(), "手机号码格式不正确！",
								Toast.LENGTH_LONG).show();

					} else if ("4".equals(code)) {
						Toast.makeText(getActivity(), "该手机号码已被绑定！",
								Toast.LENGTH_LONG).show();

					} else if ("5".equals(code)) {
						Toast.makeText(getActivity(), "验证码输入不正确！",
								Toast.LENGTH_LONG).show();

					}
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg2 == null ? "" : arg2);
			}
		});
	}

	public static Fragment getInstance(String params) {
		GetCodeForNewPhoneNumberFragment fragment = new GetCodeForNewPhoneNumberFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {

	}

@Override
public void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	if(mIntent!=null){
		getActivity().stopService(mIntent);	
	}
	
}


}
