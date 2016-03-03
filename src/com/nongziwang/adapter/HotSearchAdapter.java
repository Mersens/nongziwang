package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HotSearchAdapter extends BaseListAdapter<String>{
	private List<String> list;

	public HotSearchAdapter(List<String> list, Context context) {
		super(list, context);
		this.list=list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View view=mInflater.inflate(R.layout.layout_hotsearch_item, null);
		TextView tv_name=(TextView) view.findViewById(R.id.tv_name);
		tv_name.setText(list.get(position));
		return view;
	}

}
