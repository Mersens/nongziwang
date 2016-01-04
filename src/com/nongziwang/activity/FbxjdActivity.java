package com.nongziwang.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.nongziwang.adapter.MyArrayAdapter;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.MyRegion;
import com.nongziwang.main.R;
import com.nongziwang.view.DatePickerPopWindow;
import com.nongziwang.view.HeadView.OnLeftClickListener;

public class FbxjdActivity extends BaseActivity {
	private Spinner spinner_dw, spinner_gys_province, spinner_gys_city,
			spinner_address_province, spinner_address_city;
	private List<MyRegion> provinces;
	private List<MyRegion> address_citys;
	private List<MyRegion> gys_citys;
	private MyArrayAdapter adapter;
	private MyArrayAdapter address_citys_adapter;
	private MyArrayAdapter gys_citys_adapter;
	private EditText edt_time;
	public static final String ACTION_OK = "ok";
	public static final String ACTION_CANCEL = "cancel";
	private DatePickerPopWindow popWindow = null;
	private String[] types = { "种子", "农药", "化肥", "农机", "农膜" };
	private HorizontalScrollView scrollView_type;
	private RadioGroup radiogroup_type;
	private NongziDao dao;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_fbxjd);
		registerBoradcastReceiver();
		dao=new NongziDaoImpl(this);
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
			MarginLayoutParams lp = new MarginLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			lp.rightMargin = 4;
			radiogroup_type.addView(rb, lp);
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
		
	}

	private void showPop() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		popWindow = new DatePickerPopWindow(FbxjdActivity.this,
				df.format(new Date()),0);
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
		address_citys=new ArrayList<MyRegion>();
		address_citys.add(new MyRegion("1", "请选择", ""));
		gys_citys=new ArrayList<MyRegion>();
		gys_citys.add(new MyRegion("1", "请选择", ""));
		provinces=dao.findAllProvinces();
		edt_time.setKeyListener(null);
		edt_time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPop();
			}
		});
		
		spinner_gys_province.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0){
					String parintid=provinces.get(position).getId();
					gys_citys.clear();
					gys_citys=dao.findAllCitysByProvincesId(parintid);
					gys_citys_adapter.clear();
					gys_citys_adapter.addAll(gys_citys);
					gys_citys_adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

		
		spinner_address_province.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0){
					String parintid=provinces.get(position).getId();
					address_citys.clear();
					address_citys=dao.findAllCitysByProvincesId(parintid);
					address_citys_adapter.clear();
					address_citys_adapter.addAll(address_citys);
					address_citys_adapter.notifyDataSetChanged();
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		adapter = new MyArrayAdapter(FbxjdActivity.this,
				android.R.layout.simple_spinner_item, provinces);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		address_citys_adapter=new MyArrayAdapter(FbxjdActivity.this,
				android.R.layout.simple_spinner_item, address_citys);
		address_citys_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gys_citys_adapter=new MyArrayAdapter(FbxjdActivity.this,
				android.R.layout.simple_spinner_item, gys_citys);
		gys_citys_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_dw.setAdapter(adapter);
		spinner_dw.setSelection(0, true);
		spinner_gys_province.setAdapter(adapter);
		spinner_gys_city.setAdapter(gys_citys_adapter);
		spinner_gys_city.setSelection(0, true);
		spinner_address_province.setAdapter(adapter);
		spinner_address_city.setAdapter(address_citys_adapter);
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
