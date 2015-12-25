package com.nongziwang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nongziwang.main.R;

public class GetCodeForNewPhoneNumberFragment extends BaseFragment{
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_new_phone_number, container,false);
		initViews();
		initEvent();
		return view;
	}
	
	
	private void initViews() {

	}
	
	private void initEvent() {

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
	
}
