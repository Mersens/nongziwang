package com.nongziwang.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nongziwang.application.AppConstants;
import com.nongziwang.fragment.MyShopsFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.CircleImageView;
import com.nongziwang.view.HeadView.OnLeftClickListener;

public class MyShopsFragmentActivity extends BaseActivity implements
		OnClickListener {
	private ImageView img_menu_back;
    private ImageView img_barcode;
    public static ImageView image_share;
    public static CircleImageView image_dp_logo;
    public static TextView tv_dp_name;
    public static RelativeLayout layout_loading;
    public static EditText search_edit;
	private LinearLayout layout_dianpu, layout_shangpin;
	private ImageView image_shangpin, image_dianpu;
	private TextView tv_shangpin, tv_dianpu;
	private View view_shangpin, view_dianpu;
	private LinearLayout layout_top;
	public static final String ACTION_SHOW="show";
	public static final String ACTION_HIDE="hide";
	private static final String URL=AppConstants.SERVICE_ADDRESS+"dianpu/gotoDianpuIndex";
	private static final String ALL_URL=AppConstants.SERVICE_ADDRESS+"dianpu/getAllDianpuChanpin";
	private String id;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_myshops_main);
		registerBoradcastReceiver();
		id=getIntent().getStringExtra("params");
		initViews();
		initEvent();
		addFragment(URL, false);
	}

	private void initViews() {
		search_edit=(EditText) findViewById(R.id.search_edit);
		layout_loading=(RelativeLayout) findViewById(R.id.layout_loading);
		tv_dp_name=(TextView) findViewById(R.id.tv_dp_name);
		image_dp_logo=(CircleImageView) findViewById(R.id.image_dp_logo);
		image_share=(ImageView) findViewById(R.id.image_share);
		layout_top=(LinearLayout) findViewById(R.id.layout_top);
		img_barcode=(ImageView) findViewById(R.id.img_barcode);
		layout_dianpu = (LinearLayout) findViewById(R.id.layout_dianpu);
		layout_shangpin = (LinearLayout) findViewById(R.id.layout_shangpin);
		image_shangpin = (ImageView) findViewById(R.id.image_shangpin);
		image_dianpu = (ImageView) findViewById(R.id.image_dianpu);
		tv_shangpin = (TextView) findViewById(R.id.tv_shangpin);
		tv_dianpu = (TextView) findViewById(R.id.tv_dianpu);
		view_shangpin = findViewById(R.id.view_shangpin);
		view_dianpu = findViewById(R.id.view_dianpu);
		img_menu_back = (ImageView) findViewById(R.id.img_menu_back);
	}

	private void initEvent() {
		img_barcode.setOnClickListener(this);
		layout_dianpu.setOnClickListener(this);
		layout_shangpin.setOnClickListener(this);
		img_menu_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "²é¿´µêÆÌ", new OnLeftClickListener() {
			
			@Override
			public void onClick() {
				finishActivity();				
			}
		});
	}

	public Fragment creatFragment(String params) {
		return MyShopsFragment.getInstance(params,id);
	}

	public void addFragment(String params, boolean isAnim) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(params);
		if (isAnim) {
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		}
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_container, fragment);
		ft.commit();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_dianpu:
			setTabs(0);
			addFragment(URL, true);
			break;
		case R.id.layout_shangpin:
			setTabs(1);
			addFragment(ALL_URL, true);
			break;
		case R.id.img_barcode:
			intentAction(MyShopsFragmentActivity.this, CaptureActivity.class);
			break;
		}
	}

	public void setTabs(int i) {
		resetColor();
		switch (i) {
		case 0:
			image_dianpu.setImageResource(R.drawable.icon_dianpu_pressed);
			tv_dianpu.setTextColor(getResources().getColor(
					R.color.title_yellow_text_color));
			view_dianpu.setVisibility(View.VISIBLE);

			break;
		case 1:
			image_shangpin.setImageResource(R.drawable.icon_shangpin_pressed);
			tv_shangpin.setTextColor(getResources().getColor(
					R.color.title_yellow_text_color));
			view_shangpin.setVisibility(View.VISIBLE);
			break;
		}
	}

	public void resetColor() {
		image_dianpu.setImageResource(R.drawable.icon_dianpu_normal);
		image_shangpin.setImageResource(R.drawable.icon_shangpin_normal);
		tv_shangpin.setTextColor(getResources().getColor(
				R.color.base_color_text_black));
		tv_dianpu.setTextColor(getResources().getColor(
				R.color.base_color_text_black));
		view_shangpin.setVisibility(View.GONE);
		view_dianpu.setVisibility(View.GONE);
	}
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();
            if(action.equals(ACTION_SHOW)){  
            	layout_top.startAnimation(AnimationUtils.loadAnimation(MyShopsFragmentActivity.this, R.anim.top_down));
            	layout_top.setVisibility(View.VISIBLE);
            } else if(action.equals(ACTION_HIDE)){  
            	layout_top.startAnimation(AnimationUtils.loadAnimation(MyShopsFragmentActivity.this, R.anim.top_up));
            	layout_top.setVisibility(View.GONE);
            }
        }  
    }; 
    public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(ACTION_SHOW);  
        myIntentFilter.addAction(ACTION_HIDE);    
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    } 
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterReceiver(mBroadcastReceiver);
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finishActivity();
			return true;
		} 
		return false;
	}
	
}
