package com.nongziwang.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nongziwang.adapter.MyArrayAdapter;
import com.nongziwang.main.R;
import com.nongziwang.view.DatePickerPopWindow;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.PopupWindow.OnDismissListener;

public class FbxjdActivity extends BaseActivity {
	private Spinner spinner_dw, spinner_gys_province, spinner_gys_city,
			spinner_address_province, spinner_address_city;
	private List<String> list;
	private MyArrayAdapter adapter;
	private EditText edt_time;
	public static final String ACTION_OK = "ok";
	public static final String ACTION_CANCEL = "cancel";
	private DatePickerPopWindow popWindow = null;
	private String[] types = { "种子", "农药", "化肥", "农机", "农膜" };
	private HorizontalScrollView scrollView_type;
	private RadioGroup radiogroup_type;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_fbxjd);
		registerBoradcastReceiver();
		initViews();
		initEvents();
		initType();
	}

	private void initType() {
		for (int i = 0; i < types.length; i++) {
			RadioButton rb = (RadioButton) LayoutInflater.from(
					FbxjdActivity.this).inflate(R.layout.layout_radiobutton,null);
					
			rb.setId(i);
			rb.setText(types[i]);
			RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
					RadioGroup.LayoutParams.WRAP_CONTENT,
					RadioGroup.LayoutParams.WRAP_CONTENT);
			radiogroup_type.addView(rb, params);
		}
	}

	private void initViews() {
		radiogroup_type = (RadioGroup) findViewById(R.id.radiogroup_type);
		scrollView_type = (HorizontalScrollView) findViewById(R.id.scrollView_type);
		edt_time = (EditText) findViewById(R.id.edt_time);
		spinner_dw = (Spinner) findViewById(R.id.spinner_dw);
		spinner_gys_province = (Spinner) findViewById(R.id.spinner_gys_province);
		spinner_gys_city = (Spinner) findViewById(R.id.spinner_gys_city);
		spinner_address_province = (Spinner) findViewById(R.id.spinner_address_province);
		spinner_address_city = (Spinner) findViewById(R.id.spinner_address_city);
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "发布询价单",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
		setHeadViewBg(R.color.white_color);
		list = new ArrayList<String>();
		list.add("请选择");
		list.add("北京");
		list.add("上海");
		list.add("深圳");
		list.add("广州");
		list.add("南京");
		list.add("郑州");
	}

	private void showPop() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		popWindow = new DatePickerPopWindow(FbxjdActivity.this,
				df.format(new Date()));
		popWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		popWindow.setTouchable(true);
		popWindow.setFocusable(true);
		popWindow.setOutsideTouchable(false);
		popWindow.setAnimationStyle(R.style.GrowFromBottom);
		popWindow.showAtLocation(findViewById(R.id.layout_popu_main),
				Gravity.BOTTOM, 0, 0);
	}

	private void initEvents() {
		edt_time.setKeyListener(null);
		edt_time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPop();
			}
		});

		adapter = new MyArrayAdapter(FbxjdActivity.this,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_dw.setAdapter(adapter);
		spinner_dw.setSelection(0, true);
		spinner_gys_province.setAdapter(adapter);
		spinner_gys_province.setSelection(0, true);
		spinner_gys_city.setAdapter(adapter);
		spinner_gys_city.setSelection(0, true);
		spinner_address_province.setAdapter(adapter);
		spinner_address_province.setSelection(0, true);
		spinner_address_city.setAdapter(adapter);
		spinner_address_city.setSelection(0, true);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_OK)) {
				String time = intent.getStringExtra("time");
				if (!TextUtils.isEmpty(time)) {
					edt_time.setText(time);
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
