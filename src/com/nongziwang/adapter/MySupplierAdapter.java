package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MySupplierAdapter extends BaseListAdapter<String> {

	public MySupplierAdapter(List<String> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_mysupplier_item, parent,false);
			holder.checkbox=(CheckBox) convertView.findViewById(R.id.checkbox);
			holder.img_pro=(ImageView) convertView.findViewById(R.id.img_pro);
			holder.tv_company_name=(TextView) convertView.findViewById(R.id.tv_company_name);
			holder.tv_msg=(TextView) convertView.findViewById(R.id.tv_msg);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		return convertView;
	}

	static class ViewHolder{
		private ImageView img_pro;
		private TextView tv_company_name;
		private TextView tv_msg;
		private CheckBox checkbox;
		
	}
	
	
}
