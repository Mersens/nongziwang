package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.adapter.CommonSearchResultsAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CommonSearchOtherResultsFragment extends BaseFragment {
	private View view;
	private ListView listview_search_other;
	private List<String> list;
	private CommonSearchResultsAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.listview_search_results_others, container,false);
		initViews();
		return view;
	}
	

	private void initViews() {
		listview_search_other=(ListView) view.findViewById(R.id.listview_search_other);
		list = new ArrayList<String>();
		list.add("复合肥");
		list.add("农机");
		list.add("农膜");
		list.add("硝铵酸");
		adapter=new CommonSearchResultsAdapter(list, getActivity());
		listview_search_other.setAdapter(adapter);
		
	}
	public static Fragment getInstance(String params) {
		CommonSearchOtherResultsFragment fragment = new CommonSearchOtherResultsFragment();
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
