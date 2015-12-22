package com.nongziwang.adapter;

import java.util.Arrays;
import java.util.List;

import com.nongziwang.main.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyArrayAdapter extends ArrayAdapter<String> {
	private LayoutInflater mInflater;
	private List<String> list;
	
	public MyArrayAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		mInflater=LayoutInflater.from(context);
		this.list=Arrays.asList(objects);
	}
	public MyArrayAdapter(Context context, int resource, List<String> list) {
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
		spinner_name.setText(list.get(position));
		return view;
	}
}
