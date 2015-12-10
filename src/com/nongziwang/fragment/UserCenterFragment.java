package com.nongziwang.fragment;

import com.nongziwang.main.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserCenterFragment extends BaseFragment{
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.layout_fragment4, container,false);
		initViews();
		return view;
	}

	private void initViews() {
		setOnlyTileViewMethod(view,"Fragment4");
	}
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
