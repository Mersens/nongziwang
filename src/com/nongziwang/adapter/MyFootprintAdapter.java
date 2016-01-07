package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyFootprintAdapter extends BaseListAdapter<String> {

	public MyFootprintAdapter(List<String> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.activity_myfootprint_item, parent,false);
			holder.checkbox=(CheckBox) convertView.findViewById(R.id.checkbox);
			holder.img_pro=(ImageView) convertView.findViewById(R.id.img_pro);
			holder.tv_company_name= (TextView) convertView.findViewById(R.id.tv_company_name);
			holder.tv_price= (TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_address= (TextView) convertView.findViewById(R.id.tv_address);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}
	
	static class ViewHolder{
		private CheckBox checkbox;
		private ImageView img_pro;
		private TextView tv_company_name;
		private TextView tv_price;
		private TextView tv_address;
	}
}
