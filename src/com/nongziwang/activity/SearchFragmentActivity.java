package com.nongziwang.activity;

import java.util.ArrayList;
import java.util.List;
import com.nongziwang.adapter.HotSearchAdapter;
import com.nongziwang.adapter.SearchHistoryAdapter;
import com.nongziwang.main.R;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SearchFragmentActivity extends BaseActivity {
	private GridView mGridView;
	private List<String> list;
	private Spinner spinner;
	private static final String types[] = { "产品", "公司" };
	private MyArrayAdapter adapter;
	private ImageView img_menu_back;
	private LayoutInflater mInflater;
	private ListView search_history_listView;
	private EditText search_edit;
	private ImageView img_clear;
	private Button btn_search;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_search);
		mInflater = LayoutInflater.from(this);
		initViews();
		initEvent();
	}

	private void initViews() {
		mGridView = (GridView) findViewById(R.id.gridView);
		spinner = (Spinner) findViewById(R.id.spinner);
		img_menu_back = (ImageView) findViewById(R.id.img_menu_back);
		search_history_listView = (ListView) findViewById(R.id.search_history_listView);
		search_edit = (EditText) findViewById(R.id.search_edit);
		img_clear = (ImageView) findViewById(R.id.img_clear);
		btn_search = (Button) findViewById(R.id.btn_search);
	}

	private void initEvent() {
		list = new ArrayList<String>();
		list.add("复合肥");
		list.add("农机");
		list.add("农膜");
		list.add("硝铵酸");

		mGridView.setAdapter(new HotSearchAdapter(list,
				SearchFragmentActivity.this));
		search_history_listView.setAdapter(new SearchHistoryAdapter(list,
				SearchFragmentActivity.this));
		adapter = new MyArrayAdapter(SearchFragmentActivity.this,
				android.R.layout.simple_spinner_item, types);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		spinner.setSelection(0, true);
		img_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				search_edit.setText("");
				img_clear.setVisibility(View.INVISIBLE);

			}
		});

		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String edit_value = search_edit.getText().toString();
				if (TextUtils.isEmpty(edit_value)) {
					Toast.makeText(SearchFragmentActivity.this, "搜索内容不能为空！",
							Toast.LENGTH_SHORT).show();
					return;

				}

			}
		});
		img_menu_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(0, R.anim.bottom_close);
			}
		});

		search_edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int len = search_edit.getText().toString().length();
				if (len > 0)
					img_clear.setVisibility(View.VISIBLE);
				else
					img_clear.setVisibility(View.INVISIBLE);
			}
		});

	}

	class MyArrayAdapter extends ArrayAdapter<String> {

		public MyArrayAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = mInflater.inflate(R.layout.layout_spinner_item, parent,
					false);
			TextView spinner_name = (TextView) view
					.findViewById(R.id.spinner_name);
			spinner_name.setText(types[position]);
			return view;
		}

	}
}
