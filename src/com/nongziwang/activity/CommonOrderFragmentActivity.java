package com.nongziwang.activity;

import com.nongziwang.fragment.CommonOrderFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.HeadView.OnRightClickListener;

import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class CommonOrderFragmentActivity extends BaseActivity {
	public static final String YMDCP = "已买到的产品";
	public static final String YMCCP = "已卖出的产品";
	public static final String DQR = "待确认订单";
	public static final String DFK = "待付款订单";
	public static final String DSH = "待收货订单";
	public static final String YFH = "已发货订单";
	public static final String JYCG = "交易成功";
	private String type;
	private FrameLayout fragment_ordercontainer;
	private PopupWindow popupwindow;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_common_order);
		type = getIntent().getStringExtra("params");
		initViews();
	}

	private void initViews() {
		fragment_ordercontainer = (FrameLayout) findViewById(R.id.fragment_ordercontainer);
		setDefaultViewMethod(R.drawable.ic_menu_back, type,
				R.drawable.icon_more, new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				}, new OnRightClickListener() {

					@Override
					public void onClick() {
						// TODO Auto-generated method stub
						showPop();
					}
				});
		setHeadViewBg(R.color.white_color);
		addFragment(type);
	}

	public Fragment creatFragment(String params) {
		return CommonOrderFragment.getInstance(params);
	}

	public void addFragment(String params) {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm.findFragmentById(R.id.fragment_ordercontainer);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(params);
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_ordercontainer, fragment);
		ft.commit();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finishActivity();
		}
		return false;
	}

	public void showPop() {
		Rect frame = new Rect();
		getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		View view = LayoutInflater.from(this).inflate(R.layout.pop_show, null);
		RelativeLayout layout_home = (RelativeLayout) view
				.findViewById(R.id.layout_home);
		layout_home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		popupwindow = new PopupWindow(view, getScreenWidth(), 600);
		popupwindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					popupwindow.dismiss();
					return true;
				}
				return false;
			}
		});
		popupwindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		int mHeight=getScreenHeight()-getHeadViewHeight()-statusBarHeight;
		popupwindow.setHeight(mHeight);
		popupwindow.setTouchable(true);
		popupwindow.setFocusable(true);
		popupwindow.setOutsideTouchable(true);
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
		popupwindow.setAnimationStyle(R.style.AnimationFade);
		popupwindow.showAtLocation(fragment_ordercontainer, Gravity.TOP, 0, getHeadViewHeight()+statusBarHeight);
	}

}
