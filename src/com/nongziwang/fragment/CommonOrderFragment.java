package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.activity.CommonOrderFragmentActivity;
import com.nongziwang.adapter.CommonOrderAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class CommonOrderFragment extends BaseFragment {
	private View view;
	private ListView listView;
	private List<String> list;
	private LinearLayout layout_search;
	private String type=null;
	private EditText search_edit;
	private ImageView img_clear;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_common_order, container, false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		listView = (ListView) view.findViewById(R.id.listView);
		layout_search=(LinearLayout) view.findViewById(R.id.layout_search);
		type=getArguments().getString("params");
		if(type.equals(CommonOrderFragmentActivity.YMDCP)){
			layout_search.setVisibility(View.VISIBLE);
		}
		search_edit = (EditText) view.findViewById(R.id.search_edit);
		img_clear = (ImageView) view.findViewById(R.id.img_clear);
	}

	private void initEvent() {
		list = new ArrayList<String>();
		for (int i = 0; i < 6; i++) {
			list.add(i + "");
		}
		listView.setAdapter(new CommonOrderAdapter(list, getActivity()));
		img_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				search_edit.setText("");
				img_clear.setVisibility(View.INVISIBLE);

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

		search_edit.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                	//ËÑË÷
                }
                return false;
            }

        });
	}

	public static Fragment getInstance(String params) {
		CommonOrderFragment fragment = new CommonOrderFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}
}
