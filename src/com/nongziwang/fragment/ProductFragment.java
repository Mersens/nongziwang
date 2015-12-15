package com.nongziwang.fragment;

import com.nongziwang.main.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProductFragment extends BaseFragment{
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.layout_fragment2, container,false);
		initViews();
		return view;
	}

	private void initViews() {
		setOnlyTileViewMethod(view,"ÕÒ²úÆ·");
		setHeadViewBg(R.color.actionbar_blue_color);
		setHeadViewTitleColor(getResources().getColor(R.color.white_color));
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
