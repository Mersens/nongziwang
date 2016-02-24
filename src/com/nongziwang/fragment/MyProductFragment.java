package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.adapter.GridViewAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class MyProductFragment extends BaseFragment {
	private View view;
	private List<String> list;
	private GridView gridView;
	private GridViewAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_myproduct, container,false);
		initViews();
		initEvent();
		return view;
	}
	
	private void initViews() {
		gridView= (GridView) view.findViewById(R.id.gridView);
	}

	private void initEvent() {
		list=new ArrayList<String>();
		for(int i=0;i<10;i++){
			list.add(i+"");
		}
		adapter=new GridViewAdapter(list, getActivity());
		gridView.setAdapter(adapter);
		
	}

	public static Fragment getInstance(String params) {
		MyProductFragment fragment = new MyProductFragment();
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
