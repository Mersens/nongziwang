package com.nongziwang.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyAddressEditorFragment extends BaseFragment {
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return view;
	}

	
	public static Fragment getInstance(String params) {
		MyAddressEditorFragment fragment = new MyAddressEditorFragment();
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
