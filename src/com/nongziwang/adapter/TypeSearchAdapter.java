package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TypeSearchAdapter extends BaseListAdapter<String> {
	private List<String> list;

	public TypeSearchAdapter(List<String> list, Context context) {
		super(list, context);
		this.list=list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View view=mInflater.inflate(R.layout.listview_type_search_item, parent,false);
		TextView type_name=(TextView) view.findViewById(R.id.type_name);
		type_name.setText(list.get(position));
		return view;
	}

}
