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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nongziwang.fragment.MyShopsFragment;
import com.nongziwang.main.R;

public class MyShopsFragmentActivity extends BaseActivity implements
		OnClickListener {
	private ImageView img_menu_back;
    private ImageView img_barcode;
	private LinearLayout layout_dianpu, layout_shangpin;
	private ImageView image_shangpin, image_dianpu;
	private TextView tv_shangpin, tv_dianpu;
	private View view_shangpin, view_dianpu;
	private RelativeLayout layout_more;
	private LinearLayout layout_top;
	public static final String ACTION_SHOW="show";
	public static final String ACTION_HIDE="hide";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_myshops_main);
		registerBoradcastReceiver();
		initViews();
		initEvent();
		addFragment(null, false);
	}

	private void initViews() {
		layout_top=(LinearLayout) findViewById(R.id.layout_top);
		img_barcode=(ImageView) findViewById(R.id.img_barcode);
		layout_more=(RelativeLayout) findViewById(R.id.layout_more);
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
	}

	public Fragment creatFragment(String params) {
		return MyShopsFragment.getInstance(null);
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
			addFragment(null, true);
			break;
		case R.id.layout_shangpin:
			setTabs(1);
			addFragment(null, true);
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
			layout_more.setVisibility(View.VISIBLE);
			break;
		case 1:
			image_shangpin.setImageResource(R.drawable.icon_shangpin_pressed);
			tv_shangpin.setTextColor(getResources().getColor(
					R.color.title_yellow_text_color));
			view_shangpin.setVisibility(View.VISIBLE);
			layout_more.setVisibility(View.GONE);
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
            	layout_top.setVisibility(View.VISIBLE);
            } else if(action.equals(ACTION_HIDE)){  
            	layout_top.setVisibility(View.GONE);
            }
            System.out.println("action----------"+action);
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
    	// TODO Auto-generated method stub
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
