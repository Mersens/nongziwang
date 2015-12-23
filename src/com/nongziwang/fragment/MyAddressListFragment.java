package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nongziwang.adapter.MyAddressListAdapter;
import com.nongziwang.main.R;

public class MyAddressListFragment extends BaseFragment {
	private View view;
	private ListView listView;
	private MyAddressListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_myaddresslist, container,false);
		initView();
		initEvent();
		return view;
	}

	
	private void initView() {
		listView=(ListView) view.findViewById(R.id.listView);
		
	}


	private void initEvent() {
		List<String> list=new ArrayList<String>();
		list.add("1");
		list.add("2");
		adapter=new MyAddressListAdapter(list, getActivity());
		listView.setAdapter(adapter);
		
	}


	public static Fragment getInstance(String params) {
		MyAddressListFragment fragment = new MyAddressListFragment();
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
