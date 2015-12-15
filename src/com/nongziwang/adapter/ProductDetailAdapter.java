package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ProductDetailAdapter extends BaseListAdapter<String>{
	private List<String> list;

	public ProductDetailAdapter(List<String> list, Context context) {
		super(list, context);
        this.list=list;
}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		View view=mInflater.inflate(R.layout.layout_product_detail_item, parent,false);
		return view;
	}

}
