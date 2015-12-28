package com.nongziwang.fragment;

import com.nongziwang.activity.CommonOrderFragmentActivity;
import com.nongziwang.activity.JyzwActivity;
import com.nongziwang.activity.LoginActivity;
import com.nongziwang.activity.MyAccountSellerFragmentActivity;
import com.nongziwang.activity.SettingActivity;
import com.nongziwang.activity.TqhkActivity;
import com.nongziwang.main.R;

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
 * @author Mersens Âô¼Ò
 */
public class SellerFragment extends BaseFragment implements OnClickListener{
	private View view;
	private TextView tv_seller_switch;
	private TextView tv_my_dianpu;
	private ImageView image_user_head;
	private TextView tv_login;
	
	private RelativeLayout layout_ymdcp;
	private RelativeLayout layout_dqr;
	private RelativeLayout layout_dfk;
	private RelativeLayout layout_yfh;
	private RelativeLayout layout_jycg;
	private RelativeLayout layout_jyzw;
	private RelativeLayout layout_tqck;
	private RelativeLayout layout_myaccount;
	private RelativeLayout layout_setting;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_seller, null);
		initViews();
		initEvent();
		return view;
	}
	private void initViews() {
		layout_myaccount=(RelativeLayout) view.findViewById(R.id.layout_myaccount);
		layout_setting=(RelativeLayout) view.findViewById(R.id.layout_setting);
		layout_tqck=(RelativeLayout) view.findViewById(R.id.layout_tqck);
		layout_jyzw=(RelativeLayout) view.findViewById(R.id.layout_jyzw);
		tv_seller_switch=(TextView) view.findViewById(R.id.tv_seller_switch);
		tv_my_dianpu=(TextView) view.findViewById(R.id.tv_my_dianpu);
		image_user_head=(ImageView) view.findViewById(R.id.image_user_head);
		tv_login=(TextView) view.findViewById(R.id.tv_login);
		layout_ymdcp = (RelativeLayout) view.findViewById(R.id.layout_ymdcp);
		layout_dqr = (RelativeLayout) view.findViewById(R.id.layout_dqr);
		layout_dfk = (RelativeLayout) view.findViewById(R.id.layout_dfk);
		layout_yfh = (RelativeLayout) view.findViewById(R.id.layout_yfh);
		layout_jycg = (RelativeLayout) view.findViewById(R.id.layout_jycg);
	}
	private void initEvent() {
		layout_myaccount.setOnClickListener(this);
		layout_setting.setOnClickListener(this);
		layout_tqck.setOnClickListener(this);
		layout_jyzw.setOnClickListener(this);
		tv_seller_switch.setOnClickListener(this);
		tv_my_dianpu.setOnClickListener(this);
		image_user_head.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		layout_ymdcp.setOnClickListener(this);
		layout_dqr.setOnClickListener(this);
		layout_dfk.setOnClickListener(this);
		layout_yfh.setOnClickListener(this);
		layout_jycg.setOnClickListener(this);

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
		case R.id.layout_ymdcp:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.YMCCP);
			break;
		case R.id.layout_dqr:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.DQR);
			break;
		case R.id.layout_dfk:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.DFK);
			break;
		case R.id.layout_yfh:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.YFH);
			break;
		case R.id.layout_jycg:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.JYCG);
			break;
		case R.id.layout_tqck:
			intentAction(getActivity(),TqhkActivity.class);
			break;
		case R.id.layout_jyzw:
			intentAction(getActivity(),JyzwActivity.class);
			break;
		case R.id.layout_myaccount:
			intentAction(getActivity(),MyAccountSellerFragmentActivity.class);
			break;
		case R.id.layout_setting:
			intentAction(getActivity(),SettingActivity.class);
			break;
		}
		
	}

}
