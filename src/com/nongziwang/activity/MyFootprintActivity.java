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

import com.nongziwang.adapter.MyFootprintAdapter;
import com.nongziwang.main.R;

public class MyFootprintActivity extends BaseActivity implements OnClickListener{
	private ImageView image_back;
	private ImageView image_del;
	private TextView tv_editor,tv_title;
	private ListView listView;
	private RelativeLayout layout_del;
	private MyFootprintAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_myfootprint);
		initViews();
		initEvent();
		
	}

	private void initViews() {
		tv_title=(TextView) findViewById(R.id.tv_title);
		image_back=(ImageView) findViewById(R.id.image_back);
		image_del=(ImageView) findViewById(R.id.image_del);
		tv_editor=(TextView) findViewById(R.id.tv_editor);
		layout_del=(RelativeLayout) findViewById(R.id.layout_del);
		listView=(ListView) findViewById(R.id.listView);
	}

	private void initEvent() {
		image_back.setOnClickListener(this);
		image_del.setOnClickListener(this);
		tv_editor.setOnClickListener(this);
		List<String> list=new ArrayList<String>();
		list.add("1");
		list.add("2");
		adapter=new MyFootprintAdapter(list, MyFootprintActivity.this);
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_back:
			finishActivity();
			break;
		case R.id.image_del:
			do_DelDetail();
			break;
		case R.id.tv_editor:
			do_CancelDetail();
			break;
		}

	}
	public void do_DelDetail(){
		tv_title.setText("批量删除");
		layout_del.setVisibility(View.VISIBLE);
		tv_editor.setEnabled(true);
		tv_editor.setVisibility(View.VISIBLE);
		image_del.setVisibility(View.GONE);
		
	}
	
	public void do_CancelDetail(){
		tv_title.setText("我的足迹");
		layout_del.setVisibility(View.GONE);
		tv_editor.setEnabled(false);
		tv_editor.setVisibility(View.GONE);
		image_del.setVisibility(View.VISIBLE);
	}

}
