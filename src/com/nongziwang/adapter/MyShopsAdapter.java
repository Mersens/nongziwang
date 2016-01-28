package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class MyShopsAdapter extends BaseListAdapter<ChanPinBean> {
	private List<ChanPinBean> list;

	public MyShopsAdapter(List<ChanPinBean> list, Context context) {
		super(list, context);
		this.list = list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.layout_myshops_item,
					parent, false);
			holder.pro_image = (ImageView) convertView
					.findViewById(R.id.pro_image);
			holder.tv_jg = (TextView) convertView.findViewById(R.id.tv_jg);
			holder.tv_title = (TextView) convertView
					.findViewById(R.id.tv_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ChanPinBean bean = list.get(position);
		holder.tv_jg.setText("¥ " + bean.getJiage());
		holder.tv_title.setText(bean.getTitle());
		holder.pro_image.setImageResource(R.drawable.android);
		ImageLoader.getInstance().displayImage(bean.getChanpinimg(),
				holder.pro_image, ImageLoadOptions.getOptions());

		return convertView;
	}

	static class ViewHolder {
		private ImageView pro_image;
		private TextView tv_title;
		private TextView tv_jg;
	}

}
