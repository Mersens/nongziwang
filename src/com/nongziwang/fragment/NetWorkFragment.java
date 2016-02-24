package com.nongziwang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nongziwang.main.R;

public class NetWorkFragment extends BaseFragment{
	private View view;
	private TextView tv_refresh;
	private String params;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_network, null);
		tv_refresh=(TextView) view.findViewById(R.id.tv_refresh);
		params=getArguments().getString("params");
		tv_refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 Intent mIntent = new Intent(params);  
                 getActivity().sendBroadcast(mIntent);  
			}
		});
		return view;
	}
	
	public static Fragment getInstance(String params) {
		NetWorkFragment fragment = new NetWorkFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	protected void lazyLoad() {
		
	}

}
