package com.nongziwang.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.nongziwang.fragment.GongsiBaseDetailFragment;
import com.nongziwang.fragment.GongsiRZDetailFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.DialogTips;
import com.nongziwang.view.HeadView.OnLeftClickListener;

public class GongsiDetailFragmentActivity extends BaseActivity implements OnClickListener{
	private String dianpuid;
	private String gongsiid;
	private TextView tv_jbxx,tv_rzxx;
	private int selectedColor, unSelectedColor;
	public static String tel_num=null;
	private Button btn_tel;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_gongsidetail);
		dianpuid=getIntent().getStringExtra("dianpuid");
		gongsiid=getIntent().getStringExtra("gongsiid");
		initViews();
		initEvent();
		addFragment(dianpuid, Style.BASE);
	}

	private void initViews() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "公司信息", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		tv_jbxx=(TextView) findViewById(R.id.tv_jbxx);
		tv_rzxx=(TextView) findViewById(R.id.tv_rzxx);
		btn_tel=(Button) findViewById(R.id.btn_tel);
		
	}

	private void initEvent() {
		selectedColor = getResources()
				.getColor(R.color.title_yellow_text_color);
		unSelectedColor = getResources().getColor(
				R.color.base_color_text_black);
		tv_jbxx.setOnClickListener(this);
		tv_rzxx.setOnClickListener(this);
		btn_tel.setOnClickListener(this);
	}
	
	enum Style{
		BASE,RENZHENG
	}
	
	public Fragment creatFragment(String params,Style style) {
		Fragment fragment=null;
		switch (style) {
		case BASE:
			fragment=GongsiBaseDetailFragment.getInstance(params);
			break;
		case RENZHENG:
			fragment=GongsiRZDetailFragment.getInstance(params);
			break;
		}
		return fragment;

	}

	public void addFragment(String params,Style style) {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragment_container);
		if (fragment != null) {
			fm.beginTransaction().remove(fragment).commit();
		}
		fragment = creatFragment(params,style);
		fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).add(R.id.fragment_container, fragment).commit();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_jbxx:
			setColor(0);
			addFragment(dianpuid, Style.BASE);
			break;
		case R.id.tv_rzxx:
			setColor(1);
			addFragment(gongsiid, Style.RENZHENG);
			break;
		case R.id.btn_tel:
			doTel();
			break;
		}
	}

	private void doTel() {
		if(!TextUtils.isEmpty(tel_num) && !"".equals(tel_num)){
			String num=null;
			if(tel_num.contains("-"))
				num = tel_num.replaceAll("-", "");
			else
				num = tel_num;
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ num));
			startActivity(intent);
		}else{
			DialogTips dialog = new DialogTips(GongsiDetailFragmentActivity.this,
					"电话号码为空！", "确定");
			dialog.show();
			dialog = null;
		}
	}

	private void setColor(int i) {
		resetColor();
		switch (i) {
		case 0:
			tv_jbxx.setTextColor(selectedColor);
			break;
		case 1:
			tv_rzxx.setTextColor(selectedColor);
			break;
		}
		
	}

	private void resetColor() {
		tv_jbxx.setTextColor(unSelectedColor);
		tv_rzxx.setTextColor(unSelectedColor);
		
	}
}
