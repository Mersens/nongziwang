package com.nongziwang.activity;

import com.nongziwang.fragment.SearchResultsFragment;
import com.nongziwang.main.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchResultsFragmentActivity extends BaseActivity implements
		OnClickListener {
	private String params;
	private ImageView image_back, image_search;
	private Spinner spinner;
	private TextView tv_moren, tv_pinpai, tv_yongtu, tv_jinghanliang, tv_jiage;
	private static final String types[] = { "产品", "公司" };
	private LayoutInflater mInflater;
	private MyArrayAdapter adapter;
	

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_search_result);
		params = getIntent().getStringExtra("params");
		mInflater = LayoutInflater.from(this);
		initViews();
		initEvent();
		initDatas();
	}

	private void initViews() {
		image_back = (ImageView) findViewById(R.id.image_back);
		image_search = (ImageView) findViewById(R.id.image_search);
		spinner = (Spinner) findViewById(R.id.spinner);
		tv_moren = (TextView) findViewById(R.id.tv_moren);
		tv_pinpai = (TextView) findViewById(R.id.tv_pinpai);
		tv_yongtu = (TextView) findViewById(R.id.tv_yongtu);
		tv_jinghanliang = (TextView) findViewById(R.id.tv_jinghanliang);
		tv_jiage = (TextView) findViewById(R.id.tv_jiage);
	}

	private void initEvent() {
		image_back.setOnClickListener(this);
		image_search.setOnClickListener(this);
		tv_moren.setOnClickListener(this);
		tv_pinpai.setOnClickListener(this);
		tv_yongtu.setOnClickListener(this);
		tv_jinghanliang.setOnClickListener(this);
		tv_jiage.setOnClickListener(this);
	}

	private void initDatas() {

		adapter = new MyArrayAdapter(SearchResultsFragmentActivity.this,
				android.R.layout.simple_spinner_item, types);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(0, true);
		image_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
				overridePendingTransition(0, R.anim.bottom_close);
			}
		});
		image_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SearchResultsFragmentActivity.this,
						SearchFragmentActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.bottom_open, 0);
				finish();
			}
		});
		addFragment(params);
	}

	public Fragment creatFragment(String params) {

		return SearchResultsFragment.getInstance(params);

	}

	public void addFragment(String params) {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment != null) {
			fm.beginTransaction().remove(fragment).commit();
		}
		fragment = creatFragment(params);
		fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_moren:

			setColor(0);
			addFragment(params);
			break;
		case R.id.tv_pinpai:
	
			setColor(1);
			break;
		case R.id.tv_yongtu:
		
			setColor(2);
			break;
		case R.id.tv_jinghanliang:
		
			setColor(3);
			break;
		case R.id.tv_jiage:
			
			setColor(4);
			addFragment(params);
			break;

		}

	}

	public void setColor(int i){
		resetColor();
		switch (i) {
		case 0:
			tv_moren.setTextColor(getResources().getColor(R.color.title_yellow_text_color));
			break;
		case 1:
			tv_pinpai.setTextColor(getResources().getColor(R.color.title_yellow_text_color));
			break;
		case 2:
			tv_yongtu.setTextColor(getResources().getColor(R.color.title_yellow_text_color));
			break;
		case 3:
			tv_jinghanliang.setTextColor(getResources().getColor(R.color.title_yellow_text_color));
			break;
		case 4:
			tv_jiage.setTextColor(getResources().getColor(R.color.title_yellow_text_color));
			break;

		}
		
	}
	
	
	public void resetColor(){
		tv_moren.setTextColor(getResources().getColor(R.color.gray_text_color));
		tv_pinpai.setTextColor(getResources().getColor(R.color.gray_text_color));
		tv_yongtu.setTextColor(getResources().getColor(R.color.gray_text_color));
		tv_jinghanliang.setTextColor(getResources().getColor(R.color.gray_text_color));
		tv_jiage.setTextColor(getResources().getColor(R.color.gray_text_color));
	}
	
	class MyArrayAdapter extends ArrayAdapter<String> {

		public MyArrayAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);

		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = mInflater.inflate(R.layout.layout_spinner_item, parent,
					false);
			TextView spinner_name = (TextView) view
					.findViewById(R.id.spinner_name);
			spinner_name.setText(types[position]);
			return view;
		}

	}
}
