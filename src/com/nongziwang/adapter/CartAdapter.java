package com.nongziwang.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import com.nongziwang.main.R;

public class CartAdapter extends BaseListAdapter<String> {

	public CartAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.layout_cart_item, parent,
					false);
			holder = new ViewHolder();
			holder.btn_cart_add = (Button) convertView
					.findViewById(R.id.btn_cart_add);
			holder.btn_cart_num_edit = (Button) convertView
					.findViewById(R.id.btn_cart_num_edit);
			holder.btn_cart_reduce = (Button) convertView
					.findViewById(R.id.btn_cart_reduce);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ViewHolder holder2 = holder;
		holder.btn_cart_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int num = getNum(holder2.btn_cart_num_edit);
				if (!holder2.btn_cart_reduce.isEnabled()) {
					holder2.btn_cart_reduce.setEnabled(true);
				}
				num++;
				holder2.btn_cart_num_edit.setText("" + num);
			}
		});
		holder.btn_cart_reduce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int num = getNum(holder2.btn_cart_num_edit);
				if (num == 1) {
					holder2.btn_cart_reduce.setEnabled(false);
				} else {
					num--;
				}
				holder2.btn_cart_num_edit.setText("" + num);
			}
		});
		return convertView;
	}

	private int getNum(Button tvNum) {
		String num = tvNum.getText().toString().trim();
		return Integer.valueOf(num);
	}
	
	static class ViewHolder {
		protected Button btn_cart_reduce;
		protected Button btn_cart_num_edit;
		protected Button btn_cart_add;
	}

}
