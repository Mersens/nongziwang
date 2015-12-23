package com.nongziwang.activity;

import com.nongziwang.fragment.MyProductFragment;
import com.nongziwang.fragment.MySupplierFragment;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Mersens Œ“µƒ ’≤ÿ
 */
public class MyCollectionFragmentActivity extends BaseActivity implements
		OnClickListener {
	private ImageView image_back;
	private TextView tv_editor, tv_product, tv_gys;
	private RelativeLayout layout_btn_del;
	private RelativeLayout layout_tv_remove;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_mycollection);
		initViews();
		initEvent();
		addFragment(Style.PRODUCT, null, false);
	}

	private void initViews() {
		layout_tv_remove=(RelativeLayout) findViewById(R.id.layout_tv_remove);
		layout_btn_del=(RelativeLayout) findViewById(R.id.layout_btn_del);
		tv_gys = (TextView) findViewById(R.id.tv_gys);
		tv_product = (TextView) findViewById(R.id.tv_product);
		tv_editor = (TextView) findViewById(R.id.tv_editor);
		image_back = (ImageView) findViewById(R.id.image_back);
	}

	private void initEvent() {
		tv_editor.setOnClickListener(this);
		tv_product.setOnClickListener(this);
		tv_gys.setOnClickListener(this);
		image_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finishActivity();
			}
		});

	}

	public enum Style {
		PRODUCT, SUPPLIER;
	}

	public Fragment creatFragment(Style style, String params) {
		Fragment fragment = null;
		switch (style) {
		case PRODUCT:
			fragment = MyProductFragment.getInstance(params);
			break;
		case SUPPLIER:
			fragment = MySupplierFragment.getInstance(params);
			break;
		}
		return fragment;
	}

	public void setBottomTips(Style style){
		switch (style) {
		case PRODUCT:
			layout_btn_del.setVisibility(View.VISIBLE);
			layout_tv_remove.setVisibility(View.GONE);
			break;
		case SUPPLIER:
			layout_btn_del.setVisibility(View.GONE);
			layout_tv_remove.setVisibility(View.VISIBLE);
			break;
		}
	}
	public void addFragment(Style style, String params, boolean isAnim) {

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm
				.findFragmentById(R.id.fragment_mycollection_container);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(style, params);
		if (isAnim) {
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		}
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_mycollection_container, fragment);
		ft.commit();
		setBottomTips(style);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_editor:

			break;
		case R.id.tv_product:
			setColors(0);
			addFragment(Style.PRODUCT, null, true);
			break;
		case R.id.tv_gys:
			setColors(1);
			addFragment(Style.SUPPLIER, null, true);
			break;
		}
	}

	public void setColors(int i) {
		resetColor(); 
		switch (i) {
		case 0:
			tv_product.setTextColor(getResources().getColor(
					R.color.red_text_color));
			break;
		case 1:
			tv_gys.setTextColor(getResources().getColor(R.color.red_text_color));
			break;
		}
	}

	public void resetColor() {
		tv_product.setTextColor(getResources().getColor(
				R.color.base_color_text_black));
		tv_gys.setTextColor(getResources().getColor(
				R.color.base_color_text_black));
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
