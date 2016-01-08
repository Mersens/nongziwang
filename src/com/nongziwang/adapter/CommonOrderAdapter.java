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

public class CommonOrderAdapter extends BaseListAdapter<String>{

	public CommonOrderAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
		    convertView=mInflater.inflate(R.layout.layout_common_order_item, parent,false);
		    holder.checkbox=(CheckBox) convertView.findViewById(R.id.checkbox);
		    holder.tv_company_name=(TextView) convertView.findViewById(R.id.tv_company_name);
		    holder.tv_tips=(TextView) convertView.findViewById(R.id.tv_tips);
		    holder.img_pro=(ImageView) convertView.findViewById(R.id.img_pro);
		    holder.tv_msg=(TextView) convertView.findViewById(R.id.tv_msg);
		    holder.tv_price=(TextView) convertView.findViewById(R.id.tv_price);
		    holder.tv_count=(TextView) convertView.findViewById(R.id.tv_count);
		    holder.tv_all_count_msg=(TextView) convertView.findViewById(R.id.tv_all_count_msg);
		    holder.tv_logistics=(TextView) convertView.findViewById(R.id.tv_logistics);
		    holder.tv_del=(TextView) convertView.findViewById(R.id.tv_del);
		    convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_logistics.setOnClickListener(new MyOnClickListener(position));
		holder.tv_del.setOnClickListener(new MyOnClickListener(position));
		return convertView;
	}

	static class ViewHolder{
		private CheckBox checkbox;
		private TextView tv_company_name;
		private TextView tv_tips;
		private ImageView img_pro;
		private TextView tv_msg;
		private TextView tv_price;
		private TextView tv_count;
		private TextView tv_all_count_msg;
		private TextView tv_logistics;
		private TextView tv_del;
		
	}
	
	class MyOnClickListener implements OnClickListener{
		private int pos;
		public MyOnClickListener(int pos){
			this.pos=pos;
		}
		@Override
		public void onClick(View v) {
			
		}
		
	}
}
