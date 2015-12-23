package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.adapter.MySupplierAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MySupplierFragment extends BaseFragment {
	private View view;
	private ListView listView;
	private MySupplierAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_mysupplier, null);
		initView();
		initEvent();
		return view;
	}


	private void initView() {
		listView=(ListView) view.findViewById(R.id.listView);
	}
	
	private void initEvent() {
		List<String> list=new ArrayList<String>();
		list.add("a");
		list.add("b");
		adapter=new MySupplierAdapter(list, getActivity());
		listView.setAdapter(adapter);
	}
	public static Fragment getInstance(String params) {
		MySupplierFragment fragment = new MySupplierFragment();
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
