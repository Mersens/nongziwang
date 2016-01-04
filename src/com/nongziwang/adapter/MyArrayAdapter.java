package com.nongziwang.adapter;
import java.util.List;
import com.nongziwang.entity.MyRegion;
import com.nongziwang.main.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<MyRegion> {
	private LayoutInflater mInflater;
	private List<MyRegion> list;

	public MyArrayAdapter(Context context, int resource, List<MyRegion> list) {
		super(context, resource, list);
		mInflater=LayoutInflater.from(context);
		this.list=list;
	}
	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = mInflater.inflate(R.layout.layout_spinner_item, parent,
				false);
		TextView spinner_name = (TextView) view
				.findViewById(R.id.spinner_name);
		spinner_name.setText(list.get(position).getName());
		return view;
	}
}
