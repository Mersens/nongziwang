package com.nongziwang.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.nongziwang.adapter.CartAdapter;
import com.nongziwang.main.R;

public class CartActivity extends BaseActivity implements OnClickListener{
	private ListView listView;
	private ImageView image_back;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_cart);
		initViews();
		initEvents();
	}
	private void initViews() {
		image_back=(ImageView) findViewById(R.id.image_back);
		listView=(ListView) findViewById(R.id.listView);
	}
	private void initEvents() {
		image_back.setOnClickListener(this);
		List<String> list=new ArrayList<String>();
		list.add("1");
		list.add("2");
		listView.setAdapter(new CartAdapter(list, CartActivity.this));
		
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.image_back:
			finishActivity();
			break;
		}
	}


}
