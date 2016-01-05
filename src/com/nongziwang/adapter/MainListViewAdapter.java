package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MainListViewAdapter extends BaseListAdapter<String> {
	private List<String> list;
	private Context context;

	public MainListViewAdapter(List<String> list, Context context) {
		super(list, context);
		this.list = list;
		this.context = context;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
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
		holder.img_left.setOnClickListener(new MyOnClickListener(position,
				Type.LEFT));
		holder.img_right_top.setOnClickListener(new MyOnClickListener(
				position, Type.RIGHT_TOP));
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
				Toast.makeText(context, "pos是" + pos + "-------->LEFT",
						Toast.LENGTH_SHORT).show();
				break;
			case RIGHT_TOP:
				Toast.makeText(context, "pos是" + pos + "-------->RIGHT_TOP",
						Toast.LENGTH_SHORT).show();
				break;
			case RIGHT_BOTTOM_LEFT:
				Toast.makeText(context,
						"pos是" + pos + "-------->RIGHT_BOTTOM_LEFT",
						Toast.LENGTH_SHORT).show();
				break;
			case RIGHT_BOTTOM_RIGHT:
				Toast.makeText(context,
						"pos是" + pos + "-------->RIGHT_BOTTOM_RIGHT",
						Toast.LENGTH_SHORT).show();
				break;

			}

		}

	}
}
