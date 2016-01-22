package com.nongziwang.activity;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
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

import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.SearchHistoryAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.view.FlowLayout;

public class SearchFragmentActivity extends BaseActivity {
	private FlowLayout flowlayout;
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
	private String userid = null;
	private static final String HOTSEARCH_URL = AppConstants.SERVICE_ADDRESS
			+ "chanpinsousuo/getSousuoKeywords";

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_search);
		mInflater = LayoutInflater.from(this);
		dao = new NongziDaoImpl(this);
		initViews();
		initEvent();
		initDatas();
	}

	private void initDatas() {
		HttpUtils.doPost(HOTSEARCH_URL, new TextHttpResponseHandler() {
			@SuppressLint("NewApi")
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				try {
					MarginLayoutParams lp = new MarginLayoutParams(new LayoutParams(
							LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
					lp.bottomMargin = 8;
					lp.leftMargin = 8;
					lp.rightMargin = 8;
					lp.topMargin = 8;
					hotsearchlist = JsonUtils.getKeyWordInfo(arg2);
					for (int i = 0; i < hotsearchlist.size(); i++) {
						TextView tv = new TextView(SearchFragmentActivity.this);
						tv.setText(hotsearchlist.get(i));
						tv.setId(i);
						tv.setOnClickListener(toolsItemListener);
						tv.setBackground(getResources().getDrawable(R.drawable.tv_hotsearch_bg));
						tv.setTextColor(getResources().getColor(R.color.black_text_color));
						tv.setTextSize(14);
						flowlayout.addView(tv,lp);
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
			}

		});

	}
	
	private View.OnClickListener toolsItemListener =new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			search_edit.setText(hotsearchlist.get(v.getId()));
			isFromHistory = false;
			doSearch();
		}
	};
	private void initViews() {
		flowlayout = (FlowLayout) findViewById(R.id.flowlayout);
		tv_clear = (TextView) findViewById(R.id.tv_clear);
		spinner = (Spinner) findViewById(R.id.spinner);
		img_menu_back = (ImageView) findViewById(R.id.img_menu_back);
		search_history_listView = (ListView) findViewById(R.id.search_history_listView);
		search_edit = (EditText) findViewById(R.id.search_edit);
		img_clear = (ImageView) findViewById(R.id.img_clear);
		btn_search = (Button) findViewById(R.id.btn_search);
	}

	private void initEvent() {

		userid = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUserId();
		if (TextUtils.isEmpty(userid)) {
			search_history_list = dao.selectAllHistory("1");
		} else {
			search_history_list = dao.selectAllHistory(userid);
		}
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
		


		tv_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (TextUtils.isEmpty(userid)) {
					dao.delAllHistory("1");
				} else {
					dao.delAllHistory(userid);
				}
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
			if (!dao.findHistoryIsExist(edit_value)) {
				if (TextUtils.isEmpty(userid)) {
					dao.addSearchHistory("1", edit_value);
				} else {
					dao.addSearchHistory(userid, edit_value);
				}

			}
		}

		int sp_pos = spinner.getSelectedItemPosition();
		Intent intent = new Intent(SearchFragmentActivity.this,
				SearchResultsFragmentActivity.class);
		intent.putExtra("params", edit_value);
		intent.putExtra("sp_pos", sp_pos);
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
