package com.nongziwang.fragment;

import com.nongziwang.activity.CartActivity;
import com.nongziwang.activity.CommonOrderFragmentActivity;
import com.nongziwang.activity.LoginActivity;
import com.nongziwang.main.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Mersens Âò¼Ò
 */
public class BuyerFragment extends BaseFragment implements OnClickListener {
	private View view;
	private TextView tv_buyer_switch;
	private ImageView image_user_head;
	private TextView tv_login;

	private RelativeLayout layout_ymdcp;
	private RelativeLayout layout_dqr;
	private RelativeLayout layout_dfk;
	private RelativeLayout layout_dsh;
	private RelativeLayout layout_jycg;
	private RelativeLayout layout_cart;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_buyer, null);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		tv_buyer_switch = (TextView) view.findViewById(R.id.tv_buyer_switch);
		image_user_head = (ImageView) view.findViewById(R.id.image_user_head);
		tv_login = (TextView) view.findViewById(R.id.tv_login);
		layout_ymdcp = (RelativeLayout) view.findViewById(R.id.layout_ymdcp);
		layout_dqr = (RelativeLayout) view.findViewById(R.id.layout_dqr);
		layout_dfk = (RelativeLayout) view.findViewById(R.id.layout_dfk);
		layout_dsh = (RelativeLayout) view.findViewById(R.id.layout_dsh);
		layout_jycg = (RelativeLayout) view.findViewById(R.id.layout_jycg);
		layout_cart=(RelativeLayout) view.findViewById(R.id.layout_cart);
	}

	private void initEvent() {
		tv_buyer_switch.setOnClickListener(this);
		image_user_head.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		layout_ymdcp.setOnClickListener(this);
		layout_dqr.setOnClickListener(this);
		layout_dfk.setOnClickListener(this);
		layout_dsh.setOnClickListener(this);
		layout_jycg.setOnClickListener(this);
		layout_cart.setOnClickListener(this);
	}

	public static Fragment getInstance(String params) {
		BuyerFragment fragment = new BuyerFragment();
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
		case R.id.tv_buyer_switch:
			Intent intent = new Intent();
			intent.setAction(UserCenterFragment.BROADCAST_ACTION_SELL);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.image_user_head:

			break;
		case R.id.tv_login:
			intentAction(getActivity(), LoginActivity.class);
			break;
		case R.id.layout_ymdcp:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.YMDCP);
			break;
		case R.id.layout_dqr:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.DQR);
			break;
		case R.id.layout_dfk:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.DFK);
			break;
		case R.id.layout_dsh:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.DSH);
			break;
		case R.id.layout_jycg:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.JYCG);
			break;
		case R.id.layout_cart:
			intentAction(getActivity(), CartActivity.class);
			break;
		}

	}

}
