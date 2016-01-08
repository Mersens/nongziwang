package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyProductAdapter extends BaseListAdapter<String> {

	public MyProductAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_pro_manage_item,
					parent, false);
			holder.checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			holder.img_pro = (ImageView) convertView.findViewById(R.id.img_pro);
			holder.tv_pro_title = (TextView) convertView
					.findViewById(R.id.tv_pro_title);
			holder.tv_pro_msg = (TextView) convertView
					.findViewById(R.id.tv_pro_msg);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_jy_static = (TextView) convertView
					.findViewById(R.id.tv_jy_static);
			holder.tv_open_jy = (TextView) convertView
					.findViewById(R.id.tv_open_jy);
			holder.tv_update = (TextView) convertView
					.findViewById(R.id.tv_update);
			holder.tv_xj = (TextView) convertView.findViewById(R.id.tv_xj);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_update.setOnClickListener(new MyOnClickListener(position,
				Style.UPDATE));
		holder.tv_xj.setOnClickListener(new MyOnClickListener(position,
				Style.UPDOWN));
		return convertView;
	}

	enum Style {
		UPDATE, UPDOWN;
	}

	static class ViewHolder {
		private CheckBox checkbox;
		private ImageView img_pro;
		private TextView tv_pro_title;
		private TextView tv_pro_msg;
		private TextView tv_time;
		private TextView tv_jy_static;
		private TextView tv_open_jy;
		private TextView tv_update;
		private TextView tv_xj;

	}

	class MyOnClickListener implements OnClickListener {
		private int pos;
		private Style style;

		public MyOnClickListener(int pos, Style style) {
			this.pos = pos;
			this.style = style;
		}

		@Override
		public void onClick(View v) {
			switch (style) {
			case UPDATE:

				break;
			case UPDOWN:

				break;

			}

		}

	}

}
