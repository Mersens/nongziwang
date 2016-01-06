package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultsAdapter extends BaseListAdapter<String> {

	public SearchResultsAdapter(List<String> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.layout_search_result_item,
					parent, false);
			holder.img_cart = (ImageView) convertView
					.findViewById(R.id.img_cart);
			holder.pro_img = (ImageView) convertView.findViewById(R.id.pro_img);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			holder.tv_money = (TextView) convertView
					.findViewById(R.id.tv_money);
			holder.tv_address = (TextView) convertView
					.findViewById(R.id.tv_address);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.img_cart.setOnClickListener(new MyOnClickListener(position));
		return convertView;
	}

	class MyOnClickListener implements OnClickListener {
		private int pos;

		public MyOnClickListener(int pos) {
			this.pos = pos;
		}

		@Override
		public void onClick(View v) {

		}

	}

	static class ViewHolder {
		private ImageView pro_img;
		private TextView tv_title;
		private TextView tv_money;
		private TextView tv_address;
		private ImageView img_cart;
	}
}
