package com.nongziwang.fragment;

import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AccountVipFragment extends BaseFragment {
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_account_vip, container,false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		
	}


	private void initEvent() {
		
	}


	public static Fragment getInstance(String params) {
		AccountVipFragment fragment = new AccountVipFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
