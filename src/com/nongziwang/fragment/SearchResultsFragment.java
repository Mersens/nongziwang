package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.adapter.SearchResultsAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class SearchResultsFragment extends BaseFragment{
	private View view;
	private ListView productlistview;
	private SearchResultsAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.listview_search_results, container,false);
		initView();
		return view;
	}


	
	private void initView() {
		productlistview=(ListView) view.findViewById(R.id.productlistview);
		List<String> list=new ArrayList<String>();
		for(int i=0;i<5;i++){
			list.add(i+"");
		}
		adapter=new SearchResultsAdapter(list,getActivity());
		productlistview.setAdapter(adapter);
	}


	public static Fragment getInstance(String params) {
		SearchResultsFragment fragment = new SearchResultsFragment();
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
