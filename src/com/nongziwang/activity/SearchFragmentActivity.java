package com.nongziwang.activity;
import java.util.ArrayList;
import java.util.List;

import com.nongziwang.adapter.HotSearchAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;


public class SearchFragmentActivity extends BaseActivity {
	private GridView mGridView;
	private List<String> list;
	private Spinner spinner;
	private static final String types[]={"产品","公司"};
	private ArrayAdapter<String> adapter; 
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_search);
		initViews();
		initEvent();
	}

	private void initEvent() {
		list=new ArrayList<String>();
		list.add("复合肥");
		list.add("农机");
		list.add("农膜");
		list.add("硝铵酸");
		mGridView.setAdapter(new HotSearchAdapter(list, SearchFragmentActivity.this));
		adapter=new ArrayAdapter<String>(SearchFragmentActivity.this, android.R.layout.simple_spinner_item,types);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(SearchFragmentActivity.this, types[position], Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});

		
	}

	private void initViews() {
		mGridView=(GridView) findViewById(R.id.gridView);
		spinner=(Spinner) findViewById(R.id.spinner);
	}

}
