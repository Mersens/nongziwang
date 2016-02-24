package com.nongziwang.main;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nongziwang.activity.BaseActivity;
import com.nongziwang.application.CustomApplcation;
import com.nongziwang.fragment.BuyerFragment;
import com.nongziwang.fragment.HomeFragment;
import com.nongziwang.fragment.InfoFragment;
import com.nongziwang.fragment.ProductFragment;
import com.nongziwang.fragment.UserCenterFragment;
import com.nongziwang.utils.NetUtils;
import com.nongziwang.view.DialogTips;

/**
 * 
 * @title MainActivity
 * @description:MainActivity
 * @author Mersens
 * @time 2016年1月8日
 */
public class MainActivity extends BaseActivity implements OnClickListener {
	private Fragment f1, f2, f3, f4;
	private Fragment[] fragments;

	private ImageView img_home;
	private ImageView img_product;
	private ImageView img_info;
	private ImageView img_user;
	private RelativeLayout layout_home;
	private RelativeLayout layout_product;
	private RelativeLayout layout_info;
	private RelativeLayout layout_user;
	private RelativeLayout[] mTabs;

	private int index;
	private int currentTabIndex;
	public static boolean isConnected = false;
	public static final String ACTION_NETWORK_TRUE = "action_network_true";
	public static final String ACTION_NETWORK_FALSE = "action_network_false";
	private NetReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mReceiver = new NetReceiver();
		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mReceiver, mFilter);
		initViews();
		initEvents();
	}

	private void initViews() {
		f1 = new HomeFragment();
		f2 = new ProductFragment();
		f3 = new InfoFragment();
		f4 = new UserCenterFragment();
		fragments = new Fragment[] { f1, f2, f3, f4 };

		mTabs = new RelativeLayout[4];
		layout_home = (RelativeLayout) findViewById(R.id.layout_home);
		layout_product = (RelativeLayout) findViewById(R.id.layout_product);
		layout_info = (RelativeLayout) findViewById(R.id.layout_info);
		layout_user = (RelativeLayout) findViewById(R.id.layout_user);
		img_home = (ImageView) findViewById(R.id.img_home);
		img_product = (ImageView) findViewById(R.id.img_product);
		img_info = (ImageView) findViewById(R.id.img_info);
		img_user = (ImageView) findViewById(R.id.img_user);

		mTabs[0] = layout_home;
		mTabs[1] = layout_product;
		mTabs[2] = layout_info;
		mTabs[3] = layout_user;
		getSupportFragmentManager().beginTransaction()
				.add(R.id.fragment_container, fragments[0]).show(fragments[0])
				.commit();
	}

	private void initEvents() {
		layout_home.setOnClickListener(this);
		layout_product.setOnClickListener(this);
		layout_info.setOnClickListener(this);
		layout_user.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_home:
			index = 0;
			setTab(0);
			break;
		case R.id.layout_product:
			index = 1;
			setTab(1);
			break;
		case R.id.layout_info:
			index = 2;
			setTab(2);
			break;
		case R.id.layout_user:
			index = 3;
			setTab(3);
			break;
		}
		/**
		 * replace()方法来替换Fragment,每次切换的时候Fragment都会重新实列化，重新加载一次数据，
		 * 这样做会非常消耗性能用用户的流量。add()方法可以实现多个Fragment切换不重新实例化
		 */
		if (currentTabIndex != index) {
			FragmentTransaction trx = getSupportFragmentManager()
					.beginTransaction();
			trx.hide(fragments[currentTabIndex]);
			if (!fragments[index].isAdded()) {
				trx.add(R.id.fragment_container, fragments[index]);
			}
			trx.show(fragments[index]).commit();
		}
		mTabs[currentTabIndex].setSelected(false);
		mTabs[index].setSelected(true);
		currentTabIndex = index;
	}

	private void setTab(int i) {
		resetImgs();
		switch (i) {
		case 0:
			img_home.setImageResource(R.drawable.icon_shouye_pressed);
			break;
		case 1:
			img_product.setImageResource(R.drawable.icon_chanpin_pressed);
			break;
		case 2:
			img_info.setImageResource(R.drawable.icon_zixun_pressed);
			break;
		case 3:
			img_user.setImageResource(R.drawable.icon_geren_pressed);
			break;
		}
	}

	private void resetImgs() {
		img_home.setImageResource(R.drawable.icon_shouye_normal);
		img_product.setImageResource(R.drawable.icon_chanpin_normal);
		img_info.setImageResource(R.drawable.icon_zixun_normal);
		img_user.setImageResource(R.drawable.icon_geren_normal);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			this.confirmExit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	public void confirmExit() {
		DialogTips dialog = new DialogTips(MainActivity.this, "退出", "是否退出软件？",
				"确定", true, true);
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int userId) {
				CustomApplcation.getInstance().exit();
				finish();
			}
		});
		dialog.show();
		dialog = null;
	}

	class NetReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
				boolean isConnected = NetUtils.isNetworkConnected(context);
		        if (isConnected) {

		        } else {
		        	//showDialogTips();
		        }
			}
		}
	}

/*private	void showDialogTips(){
	DialogTips dialog = new DialogTips(getApplicationContext(), "温馨提示",
			"没有网络连接，请检查网络设置!", "确定", true, true);
	dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialogInterface, int userId) {
			Intent intent=null;
            //判断手机系统的版本  即API大于10 就是3.0或以上版本 
            if(android.os.Build.VERSION.SDK_INT>10){
                intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            }else{
                intent = new Intent();
                ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                intent.setComponent(component);
                intent.setAction("android.intent.action.VIEW");
            }
            startActivity(intent);
		}
	});
	dialog.show();
	dialog = null;
	}*/
	// 发送广播，更新用户信息
	@Override
	protected void onStart() {
		super.onStart();
		Intent mIntent = new Intent(BuyerFragment.ACTION_UPDATE);
		sendBroadcast(mIntent);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
}
