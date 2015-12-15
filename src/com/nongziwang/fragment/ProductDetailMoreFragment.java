package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.adapter.ProductDetailAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ProductDetailMoreFragment extends BaseFragment{
	private View view;
	private ListView listView;
	private List<String> list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_product_detail_more, container,false);
		initViews();
		initEvent();
		return view;
	}
	


	private void initViews() {
		listView=(ListView) view.findViewById(R.id.listView);
	}
	private void initEvent() {
		list=new ArrayList<String>();
		for(int i=0;i<6;i++){
			list.add(i+"");
		}
		listView.setAdapter(new ProductDetailAdapter(list, getActivity()));
		
	}
	@Override
	protected void lazyLoad() {
		
	}

	
	public static Fragment getInstance(String params) {
		ProductDetailMoreFragment fragment = new ProductDetailMoreFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}
	
	
	
}
