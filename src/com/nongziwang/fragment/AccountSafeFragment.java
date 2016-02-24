package com.nongziwang.fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.nongziwang.activity.ResetPasswordFragmentActivity;
import com.nongziwang.activity.ResetPhoneNumberFragmentActivity;
import com.nongziwang.main.R;

public class AccountSafeFragment extends BaseFragment {
	private View view;
	private Button btn_reset_password, btn_reset_phonenumber;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_account_safe, container, false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		btn_reset_password = (Button) view
				.findViewById(R.id.btn_reset_password);
		btn_reset_phonenumber = (Button) view
				.findViewById(R.id.btn_reset_phonenumber);
	}

	private void initEvent() {
		btn_reset_password.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentAction(getActivity(),ResetPasswordFragmentActivity.class);
			}
		});
		btn_reset_phonenumber.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentAction(getActivity(),ResetPhoneNumberFragmentActivity.class);
			}
		});
	}

	public static Fragment getInstance(String params) {
		AccountSafeFragment fragment = new AccountSafeFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	protected void lazyLoad() {

	}



}
