package com.nongziwang.fragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nongziwang.activity.JyzwActivity;
import com.nongziwang.activity.ReleaseProductFragmentActivity;
import com.nongziwang.main.R;
import com.nongziwang.view.DatePickerPopWindow;

public class ReleaseProductMsgFragment extends BaseFragment {
	private View view;
	private Button btn_next;
	private EditText edt_send_goods_time;
	private DatePickerPopWindow popWindow = null;
	private LayoutInflater mInflater;
	public static final String ACTION_OK = "ok";
	public static final String ACTION_CANCEL = "cancel";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_rele_pro_msg, null);
		registerBoradcastReceiver();
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		mInflater=LayoutInflater.from(getActivity());
		btn_next = (Button) view.findViewById(R.id.btn_next);
		edt_send_goods_time=(EditText) view.findViewById(R.id.edt_send_goods_time);
	}

	private void initEvent() {
		edt_send_goods_time.setKeyListener(null);
		edt_send_goods_time.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				showPop(v.getId());
			}
		});
		btn_next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(
						ReleaseProductFragmentActivity.ACTION_PRODUCT_JYXX);
				getActivity().sendBroadcast(mIntent);

			}
		});
	}

	
	private void showPop(int viewID) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		popWindow = new DatePickerPopWindow(getActivity(),
				df.format(new Date()),viewID);
		popWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		popWindow.setTouchable(true);
		popWindow.setFocusable(true);
		popWindow.setOutsideTouchable(false);
		popWindow.setAnimationStyle(R.style.GrowFromBottom);
		View v=mInflater.inflate(R.layout.layout_rele_pro_msg, null);
		popWindow.showAtLocation(v,Gravity.BOTTOM, 0, 0);
				
	}
	
	public static Fragment getInstance(String params) {
		ReleaseProductMsgFragment fragment = new ReleaseProductMsgFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_OK)) {
				String time = intent.getStringExtra("time");
				if (!TextUtils.isEmpty(time)) {
					edt_send_goods_time.setText(time);
				}
				popWindow.dismiss();
			} else if (action.equals(ACTION_CANCEL)) {
				popWindow.dismiss();
			}
		}
	};

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_OK);
		myIntentFilter.addAction(ACTION_CANCEL);
		getActivity().registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		getActivity().unregisterReceiver(mBroadcastReceiver);
	}
	
}