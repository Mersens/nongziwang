package com.nongziwang.fragment;

import com.nongziwang.main.R;
import com.nongziwang.utils.NetUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UserCenterFragment extends BaseFragment {
	private View view;
	private BroadcastReceiver mBroadcastReceiver;
	public static final String BROADCAST_ACTION_BUY = "com.nongziwang.fragment.BuyerFragment";
	public static final String BROADCAST_ACTION_SELL = "com.nongziwang.fragment.SellerFragment";
	public static final String ACTION_IUSERCENTERFRAGMENT = "usercenterfragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_usercenter, container, false);
		initEvent();
		if (NetUtils.isNetworkConnected(getActivity())) {
			addFragment(Style.BUYER, null,false);
		}else{
			addFragment(Style.NO_NET, null,false);
		}
		return view;
	}

	private void initEvent() {
		mBroadcastReceiver = new MyBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BROADCAST_ACTION_BUY);
		intentFilter.addAction(BROADCAST_ACTION_SELL);
		intentFilter.addAction(ACTION_IUSERCENTERFRAGMENT);
		getActivity().registerReceiver(mBroadcastReceiver, intentFilter);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}

	public static Fragment getInstance(String params) {
		UserCenterFragment fragment = new UserCenterFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	public enum Style {
		NO_NET,BUYER, SELLER;
	}

	public Fragment creatFragment(Style style, String params) {
		Fragment fragment = null;
		switch (style) {
		case BUYER:
			fragment = BuyerFragment.getInstance(params);
			break;
		case SELLER:
			fragment = SellerFragment.getInstance(params);
			break;
		case NO_NET:
			fragment = NetWorkFragment.getInstance(ACTION_IUSERCENTERFRAGMENT);
			break;
		}

		return fragment;
	}

	public void addFragment(Style style, String params,boolean isAnim) {
		FragmentManager fm = getFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentById(R.id.fragment_usercontainer);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(style, params);
		if(isAnim){
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		}
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_usercontainer, fragment);
		ft.commit();
	}

	class MyBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(BROADCAST_ACTION_BUY)) {
				addFragment(Style.BUYER, null,true);
			} else if (action.equals(BROADCAST_ACTION_SELL)) {
				addFragment(Style.SELLER, null,true);
			}else if(action.equals(ACTION_IUSERCENTERFRAGMENT)){
				if (NetUtils.isNetworkConnected(getActivity())) {
					addFragment(Style.BUYER, null,false);
				}
			}
		}
	}

	@Override
	protected void lazyLoad() {
	}

}
