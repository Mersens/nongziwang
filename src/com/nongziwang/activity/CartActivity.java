package com.nongziwang.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nongziwang.adapter.CartAdapter;
import com.nongziwang.main.R;

public class CartActivity extends BaseActivity implements OnClickListener {
	private ListView listView;
	private ImageView image_back;
	private RelativeLayout layout_pay_bar;
	private RelativeLayout layout_del_bar;
	private TextView tv_editor;
	private int count = 0;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_cart);
		initViews();
		initEvents();
	}

	private void initViews() {
		image_back = (ImageView) findViewById(R.id.image_back);
		listView = (ListView) findViewById(R.id.listView);
		layout_pay_bar = (RelativeLayout) findViewById(R.id.layout_pay_bar);
		layout_del_bar = (RelativeLayout) findViewById(R.id.layout_del_bar);
		tv_editor = (TextView) findViewById(R.id.tv_editor);
	}

	private void initEvents() {
		image_back.setOnClickListener(this);
		tv_editor.setOnClickListener(this);
		List<String> list = new ArrayList<String>();
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
		case R.id.tv_editor:
			showLayoutBar();
			break;
		}
	}

	private void showLayoutBar() {
		if(count % 2==0){
			tv_editor.setText("Íê³É");
			layout_pay_bar.setVisibility(View.GONE);
			layout_del_bar.setVisibility(View.VISIBLE);
		}else{
			tv_editor.setText("±à¼­");
			layout_pay_bar.setVisibility(View.VISIBLE);
			layout_del_bar.setVisibility(View.GONE);
		}
		count++;

	}

}
