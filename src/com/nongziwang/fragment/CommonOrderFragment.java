package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.adapter.CommonOrderAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CommonOrderFragment extends BaseFragment {
	private View view;
	private ListView listView;
	private List<String> list;

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
	}

	private void initEvent() {
		list = new ArrayList<String>();
		for (int i = 0; i < 6; i++) {
			list.add(i + "");
		}
		listView.setAdapter(new CommonOrderAdapter(list, getActivity()));
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
