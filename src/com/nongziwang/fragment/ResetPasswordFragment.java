package com.nongziwang.fragment;

import org.apache.http.Header;

import android.os.Bundle;
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
import com.nongziwang.activity.LoginActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.MD5Util;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.utils.StringUtils;
import com.nongziwang.view.DialogWaiting;

public class ResetPasswordFragment extends BaseFragment {
	private View view;
	private Button btn_ok;
	private EditText edt_old_psd, edt_new_psd, edt_new_psd_again;
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "userinfo/gotoUpdateUserPassword";
	private static final String TAG = "ResetPasswordFragment";
	private DialogWaiting dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.layout_resetpassword, container, false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		edt_old_psd = (EditText) view.findViewById(R.id.edt_old_psd);
		edt_new_psd = (EditText) view.findViewById(R.id.edt_new_psd);
		edt_new_psd_again = (EditText) view
				.findViewById(R.id.edt_new_psd_again);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
	}

	private void initEvent() {

		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doFinish();
			}
		});
	}

	public static Fragment getInstance(String params) {
		ResetPasswordFragment fragment = new ResetPasswordFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	public void doFinish() {
		final String psd = edt_old_psd.getText().toString().trim();
		String new_psd = edt_new_psd.getText().toString().trim();
		String new_psd_again = edt_new_psd_again.getText().toString().trim();
		if (TextUtils.isEmpty(psd) || TextUtils.isEmpty(new_psd_again)
				|| TextUtils.isEmpty(new_psd)) {
			Toast.makeText(getActivity(), "输入数据存在空值！", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (StringUtils.isContainsSpace(psd)
				|| StringUtils.isContainsSpace(new_psd_again)
				|| StringUtils.isContainsSpace(new_psd)) {
			Toast.makeText(getActivity(), "密码存在非法字符！", Toast.LENGTH_SHORT)
					.show();
			return;

		}


		if (!new_psd.equals(new_psd_again)) {
			Toast.makeText(getActivity(), "两次密码输入不一致！", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		String userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserId();
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("oldpwd", psd);
		params.put("newpwd", new_psd);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			public void onStart() {
				dialog =new DialogWaiting(getActivity());
				dialog.show();
				super.onStart();
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(getActivity(), "用户id或用户旧密码或用户新密码为空！", Toast.LENGTH_SHORT)
					.show();
				} else if ("1".equals(code)) {
					Toast.makeText(getActivity(), "密码修改成功！", Toast.LENGTH_SHORT)
					.show();
					SharePreferenceUtil.getInstance(
							getActivity().getApplicationContext()).setUserPsd(
							MD5Util.MD5(psd));
					getActivity().finish();
					getActivity().overridePendingTransition(R.anim.right_in,
							R.anim.right_out);
				} else if ("2".equals(code)) {
					Toast.makeText(getActivity(), "用户信息不存在！", Toast.LENGTH_SHORT)
					.show();
				} else if ("3".equals(code)) {
					Toast.makeText(getActivity(), "用户旧密码输入不正确！", Toast.LENGTH_SHORT)
					.show();
				}else if ("4".equals(code)) {
					Toast.makeText(getActivity(), "用户新密码输入格式不正确！", Toast.LENGTH_SHORT)
					.show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2==null?"":arg2);
				Toast.makeText(getActivity(), "修改失败！", Toast.LENGTH_LONG).show();

			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				dialog.dismiss();
			}
		});
	}

	@Override
	protected void lazyLoad() {

	}


}
