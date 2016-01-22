package com.nongziwang.adapter;

import java.util.List;
import java.util.Map;

import com.nongziwang.main.R;
import com.nongziwang.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

public class MainListViewAdapter extends BaseAdapter {
	private List<List<String>> list;
	private Context context;
	public LayoutInflater mInflater;

	public MainListViewAdapter(List<List<String>> list, Context context) {
		this.list = list;
		this.context = context;
		mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_type_item,
					parent, false);
			holder.img_left = (ImageView) convertView
					.findViewById(R.id.img_left);
			holder.img_right_top = (ImageView) convertView
					.findViewById(R.id.img_right_top);
			holder.img_right_bottom_left = (ImageView) convertView
					.findViewById(R.id.img_right_bottom_left);
			holder.img_right_bottom_right = (ImageView) convertView
					.findViewById(R.id.img_right_bottom_right);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		List<String> imglist = list.get(position);
		ImageLoader.getInstance().displayImage(imglist.get(0), holder.img_left,
				ImageLoadOptions.getOptions());
		ImageLoader.getInstance().displayImage(imglist.get(1),
				holder.img_right_top, ImageLoadOptions.getOptions());
		ImageLoader.getInstance().displayImage(imglist.get(2),
				holder.img_right_bottom_left, ImageLoadOptions.getOptions());
		ImageLoader.getInstance().displayImage(imglist.get(3),
				holder.img_right_bottom_right, ImageLoadOptions.getOptions());
		holder.img_left.setOnClickListener(new MyOnClickListener(position,
				Type.LEFT));
		holder.img_right_top.setOnClickListener(new MyOnClickListener(position,
				Type.RIGHT_TOP));
		holder.img_right_bottom_left.setOnClickListener(new MyOnClickListener(
				position, Type.RIGHT_BOTTOM_LEFT));
		holder.img_right_bottom_right.setOnClickListener(new MyOnClickListener(
				position, Type.RIGHT_BOTTOM_RIGHT));
		return convertView;
	}

	static class ViewHolder {
		private ImageView img_left;
		private ImageView img_right_top;
		private ImageView img_right_bottom_left;
		private ImageView img_right_bottom_right;
	}

	enum Type {
		LEFT, RIGHT_TOP, RIGHT_BOTTOM_LEFT, RIGHT_BOTTOM_RIGHT;
	}

	class MyOnClickListener implements OnClickListener {
		private int pos;
		private Type type;

		public MyOnClickListener(int pos, Type type) {
			this.pos = pos;
			this.type = type;
		}

		@Override
		public void onClick(View v) {
			switch (type) {
			case LEFT:

				break;
			case RIGHT_TOP:

				break;
			case RIGHT_BOTTOM_LEFT:

				break;
			case RIGHT_BOTTOM_RIGHT:
				
				break;

			}

		}

	}

}
