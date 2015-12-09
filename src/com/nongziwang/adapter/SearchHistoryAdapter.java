package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchHistoryAdapter extends BaseListAdapter<String> {
	private List<String> list;

	public SearchHistoryAdapter(List<String> list, Context context) {
		super(list, context);
		this.list = list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View view=mInflater.inflate(R.layout.layout_search_history_item,parent,false );
		TextView tv=(TextView) view.findViewById(R.id.search_history_name);
		tv.setText(list.get(position));
		return view;
	}
}
