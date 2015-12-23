package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class MyAddressListAdapter extends BaseListAdapter<String> {

	public MyAddressListAdapter(List<String> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View view=mInflater.inflate(R.layout.layout_myaddresslist_item, parent,false);
		return view;
	}

}
