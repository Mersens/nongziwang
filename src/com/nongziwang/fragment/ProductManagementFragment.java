package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nongziwang.adapter.MyProductAdapter;
import com.nongziwang.main.R;

public class ProductManagementFragment extends BaseFragment {
	private View view;
	private ListView listView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.listview_pro_manage,container,false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		listView=(ListView) view.findViewById(R.id.listView);
	}

	private void initEvent() {
		List<String> list=new ArrayList<String>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("d");
		listView.setAdapter(new MyProductAdapter(list, getActivity()));

	}

	@Override
	protected void lazyLoad() {

	}
	public static Fragment getInstance(String params) {
		ProductManagementFragment fragment = new ProductManagementFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}
}
