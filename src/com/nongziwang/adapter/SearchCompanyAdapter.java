package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchCompanyAdapter extends BaseListAdapter<String> {


	public SearchCompanyAdapter(List<String> list, Context context) {
		super(list, context);
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_search_company_item, parent,false);
			holder.img_company=(ImageView) convertView.findViewById(R.id.img_company);
			holder.tv_company=(TextView) convertView.findViewById(R.id.tv_company);
			holder.tv_jyms=(TextView) convertView.findViewById(R.id.tv_jyms);
			holder.tv_zycp=(TextView) convertView.findViewById(R.id.tv_zycp);
			holder.tv_msg=(TextView) convertView.findViewById(R.id.tv_msg);
			holder.tv_address=(TextView) convertView.findViewById(R.id.tv_address);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		return convertView;
	}
	
	static class ViewHolder{
		private TextView tv_company;
		private ImageView img_company;
		private TextView tv_jyms;
		private TextView tv_zycp;
		private TextView tv_msg;
		private TextView tv_address;
	}

}
