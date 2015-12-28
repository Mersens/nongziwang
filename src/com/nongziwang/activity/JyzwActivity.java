package com.nongziwang.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nongziwang.main.R;
import com.nongziwang.view.DatePickerPopWindow;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class JyzwActivity extends BaseActivity{
	public static final String ACTION_OK = "ok";
	public static final String ACTION_CANCEL = "cancel";
	private EditText edt_date_q;
	private EditText edt_date_z;
	private Button btn_tqck,btn_select;
	private DatePickerPopWindow popWindow = null;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_jyzw);
		registerBoradcastReceiver();
		initViews();
		initEvent();
	}

	private void initViews() {
		btn_tqck=(Button) findViewById(R.id.btn_tqck);
		btn_select=(Button) findViewById(R.id.btn_select);
		edt_date_q=(EditText) findViewById(R.id.edt_date_q);
		edt_date_q.setKeyListener(null);
		edt_date_z=(EditText) findViewById(R.id.edt_date_z);
		edt_date_z.setKeyListener(null);
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "½»Ò×ÕËÎñ",new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
	}

	private void initEvent() {
		edt_date_q.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPop(edt_date_q.getId());
			}
		});
		edt_date_z.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPop(edt_date_z.getId());
			}
		});
		btn_tqck.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowToast(btn_tqck.getText().toString());
			}
		});
		btn_select.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShowToast(btn_select.getText().toString());
			}
		});
	}
	private void showPop(int viewID) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		popWindow = new DatePickerPopWindow(JyzwActivity.this,
				df.format(new Date()),viewID);
		popWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		popWindow.setTouchable(true);
		popWindow.setFocusable(true);
		popWindow.setOutsideTouchable(false);
		popWindow.setAnimationStyle(R.style.GrowFromBottom);
		popWindow.showAtLocation(findViewById(R.id.layout_jyzw),
				Gravity.BOTTOM, 0, 0);
	}
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_OK)) {
				String time = intent.getStringExtra("time");
				int viewID=intent.getIntExtra("viewID",0);
				if (!TextUtils.isEmpty(time)) {
					if(viewID==edt_date_q.getId()){
						edt_date_q.setText(time);
					}else if(viewID==edt_date_z.getId()){
						edt_date_z.setText(time);
					}
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
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}
}
