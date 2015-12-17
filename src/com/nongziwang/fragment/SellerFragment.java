package com.nongziwang.fragment;

import com.nongziwang.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 
 * @author Mersens Âò¼Ò
 */
public class SellerFragment extends BaseFragment {
	private View view;
	private TextView tv_seller_switch;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_seller, null);
		initViews();
		initEvent();
		return view;
	}
	private void initViews() {

		tv_seller_switch=(TextView) view.findViewById(R.id.tv_seller_switch);
	}
	private void initEvent() {

		tv_seller_switch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(UserCenterFragment.BROADCAST_ACTION_BUY);
				getActivity().sendBroadcast(intent);	
				
			}
		});
	}

	public static Fragment getInstance(String params) {
		SellerFragment fragment = new SellerFragment();
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
