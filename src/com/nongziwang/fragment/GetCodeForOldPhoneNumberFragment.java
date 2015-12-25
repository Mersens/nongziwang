package com.nongziwang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.nongziwang.activity.ResetPhoneNumberFragmentActivity;
import com.nongziwang.main.R;

public class GetCodeForOldPhoneNumberFragment extends BaseFragment {
	private View view;
	private Button btn_ok;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_old_phone_number, container,
				false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
	}

	private void initEvent() {
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(
						ResetPhoneNumberFragmentActivity.ACTION_GETNEWCODE);

				getActivity().sendBroadcast(mIntent);

			}
		});
	}

	public static Fragment getInstance(String params) {
		GetCodeForOldPhoneNumberFragment fragment = new GetCodeForOldPhoneNumberFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {

	}

}
