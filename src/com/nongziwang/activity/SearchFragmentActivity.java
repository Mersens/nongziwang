package com.nongziwang.activity;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nongziwang.adapter.HotSearchAdapter;
import com.nongziwang.adapter.SearchHistoryAdapter;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.main.R;

public class SearchFragmentActivity extends BaseActivity {
	private GridView mGridView;
	private List<String> hotsearchlist;
	private Spinner spinner;
	private static final String types[] = { "产品", "公司" };
	private MyArrayAdapter adapter;
	private ImageView img_menu_back;
	private LayoutInflater mInflater;
	private ListView search_history_listView;
	private EditText search_edit;
	private ImageView img_clear;
	private TextView tv_clear;
	private Button btn_search;
	private NongziDao dao;
	private SearchHistoryAdapter shAdapter;
	private List<String> search_history_list;
	private boolean isFromHistory = false;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_search);
		mInflater = LayoutInflater.from(this);
		dao = new NongziDaoImpl(this);
		initViews();
		initEvent();
	}

	private void initViews() {
		tv_clear = (TextView) findViewById(R.id.tv_clear);
		mGridView = (GridView) findViewById(R.id.gridView);
		spinner = (Spinner) findViewById(R.id.spinner);
		img_menu_back = (ImageView) findViewById(R.id.img_menu_back);
		search_history_listView = (ListView) findViewById(R.id.search_history_listView);
		search_edit = (EditText) findViewById(R.id.search_edit);
		img_clear = (ImageView) findViewById(R.id.img_clear);
		btn_search = (Button) findViewById(R.id.btn_search);
	}

	private void initEvent() {
		hotsearchlist = new ArrayList<String>();
		hotsearchlist.add("复合肥");
		hotsearchlist.add("农机");
		hotsearchlist.add("农膜");
		hotsearchlist.add("硝铵酸");
		hotsearchlist.add("磷肥");
		hotsearchlist.add("尿素");
		mGridView.setAdapter(new HotSearchAdapter(hotsearchlist,
				SearchFragmentActivity.this));
		search_history_list = dao.selectAllHistory("1");
		if (search_history_list != null && search_history_list.size() > 0) {
			shAdapter = new SearchHistoryAdapter(search_history_list,
					SearchFragmentActivity.this);
			search_history_listView.setAdapter(shAdapter);
			tv_clear.setVisibility(View.VISIBLE);

		}
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
				isFromHistory = false;
				doSearch();
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

		search_edit
				.setOnEditorActionListener(new EditText.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_SEARCH) {
							isFromHistory = false;
							doSearch();
						}
						return false;
					}

				});
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ShowToast("点击了" + hotsearchlist.get(position));
				search_edit.setText(hotsearchlist.get(position));
				isFromHistory = false;
				doSearch();

			}
		});

		tv_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dao.delAllHistory("1");
				search_history_listView.setVisibility(View.GONE);
				tv_clear.setVisibility(View.GONE);

			}
		});

		search_history_listView
				.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						search_edit.setText(search_history_list.get(position));
						isFromHistory = true;
						doSearch();
					}
				});
	}

	public void doSearch() {
		String edit_value = search_edit.getText().toString();
		if (TextUtils.isEmpty(edit_value)) {
			ShowToast("搜索内容不能为空！");
			return;

		}
		if (!isFromHistory) {
			if(!dao.findHistoryIsExist(edit_value)){
				dao.addSearchHistory("1", edit_value);
			}
		}
		Intent intent = new Intent(SearchFragmentActivity.this,
				SearchResultsFragmentActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.bottom_open, 0);
		finish();
	}

	class MyArrayAdapter extends ArrayAdapter<String> {
		private String str[];

		public MyArrayAdapter(Context context, int resource, String[] objects) {
			super(context, resource, objects);
			str = objects;
		}

		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = mInflater.inflate(R.layout.layout_spinner_item, parent,
					false);
			TextView spinner_name = (TextView) view
					.findViewById(R.id.spinner_name);
			spinner_name.setText(str[position]);
			return view;
		}
	}

}
