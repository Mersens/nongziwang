package com.nongziwang.activity;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nongziwang.fragment.BrandFragment;
import com.nongziwang.fragment.NetcontentFragment;
import com.nongziwang.fragment.SearchCompanyFragment;
import com.nongziwang.fragment.SearchResultsByJiaGeFragment;
import com.nongziwang.fragment.SearchResultsFragment;
import com.nongziwang.fragment.UsesFragment;
import com.nongziwang.main.R;

public class SearchResultsFragmentActivity extends BaseActivity implements
		OnClickListener {
	private String params=null;
	private ImageView image_back, image_search;
	private Spinner spinner;
	private TextView tv_moren, tv_pinpai, tv_yongtu, tv_jiage;
	private static final String types[] = { "产品", "公司" };
	private LayoutInflater mInflater;
	private MyArrayAdapter adapter;
	private int index = 0;
	private LinearLayout layout_product;
	private int sp_pos=0;
	public static String pinpaiid=null;
	public static String yongtuid=null;
	public static final String ACTION_PINPAIID="action_pinpaiid";
	public static final String ACTION_YONGTUID="action_yongtuid";
	public static String leimuid=null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_search_result);
		registerBoradcastReceiver();
		params = getIntent().getStringExtra("params");
		leimuid=getIntent().getStringExtra("leimuid");
		sp_pos=getIntent().getIntExtra("sp_pos",0);
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
		tv_jiage = (TextView) findViewById(R.id.tv_jiage);
		layout_product=(LinearLayout) findViewById(R.id.layout_product);

	}

	private void initEvent() {
		image_back.setOnClickListener(this);
		image_search.setOnClickListener(this);
		tv_moren.setOnClickListener(this);
		tv_pinpai.setOnClickListener(this);
		tv_yongtu.setOnClickListener(this);
		tv_jiage.setOnClickListener(this);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position==0){
					layout_product.setVisibility(View.VISIBLE);
					addFragment(Style.MOREN, params);
				} else if(position==1){
					layout_product.setVisibility(View.GONE);
					addFragment(Style.COMPANY, params);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
	}

	private void initDatas() {
		adapter = new MyArrayAdapter(SearchResultsFragmentActivity.this,
				android.R.layout.simple_spinner_item, types);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(sp_pos, true);
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

	}
	 class MyArrayAdapter extends ArrayAdapter<String>{
		 private String str[];
		public MyArrayAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
			str=objects;
		}
		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = mInflater.inflate(R.layout.layout_spinner_item, parent,
					false);
			TextView spinner_name = (TextView) view
					.findViewById(R.id.spinner_name);
			spinner_name.setText(str[position]);
			return view;
		}
	 }
	public enum Style {
		MOREN, PINPAI, YONGTU, JINGHANLIANG, COMPANY,JIAGE;
	}
	
	public Fragment creatFragment(Style style, String params) {
		Fragment fragment = null;
		switch (style) {
		case MOREN:
			fragment = SearchResultsFragment.getInstance(params);
			break;
		case PINPAI:
			fragment = BrandFragment.getInstance(params);
			break;
		case YONGTU:
			fragment = UsesFragment.getInstance(params);
			break;
		case JINGHANLIANG:
			fragment = NetcontentFragment.getInstance(params);
			break;
		case JIAGE:
			int sorttype=0;
			if(index % 2 == 0){
				sorttype=1;
			}else{
				sorttype=0;
			}
			fragment = SearchResultsByJiaGeFragment.getInstance(params,sorttype);
			break;
		case COMPANY:
			fragment=SearchCompanyFragment.getInstance(params);
			break;
		}
		
		return fragment;

	}

	public void addFragment(Style style, String params) {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment != null) {
			fm.beginTransaction().remove(fragment).commit();
		}
		fragment = creatFragment(style, params);
		fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.fragmentContainer, fragment).commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_moren:
			index = 0;
			setColor(0);
			addFragment(Style.MOREN, params);
			break;
		case R.id.tv_pinpai:
			index = 0;
			setColor(1);
			addFragment(Style.PINPAI, params);
			break;
		case R.id.tv_yongtu:
			index = 0;
			setColor(2);
			addFragment(Style.YONGTU, params);
			break;
		case R.id.tv_jiage:
			index = index + 1;
			setColor(4);
			addFragment(Style.JIAGE, params);
			break;
		}

		if (index == 0) {
			tv_jiage.setCompoundDrawables(null, null,
					getDrawableById(R.drawable.icon_money_normal), null);
		} else {
			if (index % 2 == 0) {
				tv_jiage.setCompoundDrawables(null, null,
						getDrawableById(R.drawable.ic_high_to_low_money), null);
			} else {
				tv_jiage.setCompoundDrawables(null, null,
						getDrawableById(R.drawable.ic_low_to_high_money), null);
			}
		}

	}

	public Drawable getDrawableById(int id) {
		Drawable drawable = getResources().getDrawable(id);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		return drawable;
	}

	public void setColor(int i) {
		resetColor(i);
		switch (i) {
		case 0:
			tv_moren.setTextColor(getResources().getColor(
					R.color.title_yellow_text_color));
			break;
		case 1:
			tv_pinpai.setTextColor(getResources().getColor(
					R.color.title_yellow_text_color));
			tv_pinpai.setBackgroundResource(R.drawable.spinner_ab_default_holo_yello_am);
			break;
		case 2:
			tv_yongtu.setTextColor(getResources().getColor(
					R.color.title_yellow_text_color));
			tv_yongtu.setBackgroundResource(R.drawable.spinner_ab_default_holo_yello_am);
			break;
		case 4:
			tv_jiage.setTextColor(getResources().getColor(
					R.color.title_yellow_text_color));
			break;

		}
	}

	public void resetColor(int i) {

		tv_moren.setTextColor(getResources().getColor(R.color.base_color_text_black));
		tv_pinpai
				.setTextColor(getResources().getColor(R.color.base_color_text_black));
		tv_pinpai.setBackgroundResource(R.drawable.spinner_ab_default_holo_light_am);
		tv_yongtu
				.setTextColor(getResources().getColor(R.color.base_color_text_black));
		tv_yongtu.setBackgroundResource(R.drawable.spinner_ab_default_holo_light_am);
		tv_jiage.setTextColor(getResources().getColor(R.color.base_color_text_black));
	}
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if(action.equals(ACTION_PINPAIID)){  
            	pinpaiid=intent.getStringExtra("pinpaiid");
    			index = 0;
    			setColor(0);
            	addFragment(Style.MOREN, params);
            } 
            if(action.equals(ACTION_YONGTUID)){
            	yongtuid=intent.getStringExtra("yongtuid");
    			index = 0;
    			setColor(0);
            	addFragment(Style.MOREN, params);
            }
        }  
          
    };  
    public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(ACTION_PINPAIID);  
        myIntentFilter.addAction(ACTION_YONGTUID);       
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterReceiver(mBroadcastReceiver);
    	resetDatas();
    }
    
    public void resetDatas(){
    	pinpaiid=null;
    	yongtuid=null;
        leimuid=null;
    }
}
