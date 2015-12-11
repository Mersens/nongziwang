package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommonSearchResultsAdapter extends BaseListAdapter<String> {


	public CommonSearchResultsAdapter(List<String> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View view=mInflater.inflate(R.layout.common_search_results_item, parent,false);
		TextView tv=(TextView) view.findViewById(R.id.tv_name);
		tv.setText(list.get(position));
		return view;
	}

}
