package com.nongziwang.activity;


import com.nongziwang.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public abstract class BaseFragmentActivity extends BaseActivity {
	private ImageView menu_back;
	private LinearLayout layout_search;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_container);
		initViews();
		initEvents();
		
	}
	private void initViews() {
		menu_back=(ImageView) findViewById(R.id.menu_back);
		layout_search=(LinearLayout) findViewById(R.id.layout_search);
		FragmentManager fm=getSupportFragmentManager();
		Fragment fragment=fm.findFragmentById(R.id.fragmentContainer);
		if(fragment==null){
			fragment=creatFragment();
			fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
		}
	}
	

	private void initEvents() {
		menu_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finishActivity();
				
			}
		});
		layout_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseFragmentActivity.this,
						SearchFragmentActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.bottom_open, 0);	
				finish();
			}
		});
	}
	protected abstract Fragment creatFragment();
}
