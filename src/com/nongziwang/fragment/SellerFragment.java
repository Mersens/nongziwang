package com.nongziwang.fragment;

import com.nongziwang.activity.LoginActivity;
import com.nongziwang.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Mersens Âò¼Ò
 */
public class SellerFragment extends BaseFragment implements OnClickListener{
	private View view;
	private TextView tv_seller_switch;
	private TextView tv_my_dianpu;
	private ImageView image_user_head;
	private TextView tv_login;
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
		tv_my_dianpu=(TextView) view.findViewById(R.id.tv_my_dianpu);
		image_user_head=(ImageView) view.findViewById(R.id.image_user_head);
		tv_login=(TextView) view.findViewById(R.id.tv_login);
	}
	private void initEvent() {
		tv_seller_switch.setOnClickListener(this);
		tv_my_dianpu.setOnClickListener(this);
		image_user_head.setOnClickListener(this);
		tv_login.setOnClickListener(this);

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
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_seller_switch:
			Intent intent = new Intent();
			intent.setAction(UserCenterFragment.BROADCAST_ACTION_BUY);
			getActivity().sendBroadcast(intent);	
			break;
		case R.id.tv_my_dianpu:
			
			break;
		case R.id.image_user_head:
			
			break;
		case R.id.tv_login:
			intentAction(getActivity(), LoginActivity.class);
			break;
		}
		
	}

}
