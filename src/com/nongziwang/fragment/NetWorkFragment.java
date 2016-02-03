package com.nongziwang.fragment;

import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NetWorkFragment extends BaseFragment{
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_network, null);
		return view;
	}
	
	public static Fragment getInstance() {
		NetWorkFragment fragment = new NetWorkFragment();
		return fragment;
	}
	
	@Override
	protected void lazyLoad() {
		
	}

}
