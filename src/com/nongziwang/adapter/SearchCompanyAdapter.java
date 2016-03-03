package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.entity.CompanyBean;
import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchCompanyAdapter extends BaseListAdapter<CompanyBean> {
	private List<CompanyBean> list;
	public SearchCompanyAdapter(List<CompanyBean> list, Context context) {
		super(list, context);
		this.list=list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_search_company_item, parent,false);
			holder.tv_company=(TextView) convertView.findViewById(R.id.tv_company);
			holder.tv_jyms=(TextView) convertView.findViewById(R.id.tv_jyms);
			holder.tv_zycp=(TextView) convertView.findViewById(R.id.tv_zycp);
			holder.tv_msg=(TextView) convertView.findViewById(R.id.tv_msg);
			holder.tv_address=(TextView) convertView.findViewById(R.id.tv_address);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		CompanyBean bean=list.get(position);
		holder.tv_company.setText(bean.getGongsiname());
		holder.tv_jyms.setText(bean.getDianputype());
		holder.tv_zycp.setText(bean.getZhuyingchanpin());
		holder.tv_msg.setText(bean.getChanpinsum()+" ¼þ²úÆ·");
		return convertView;
	}
	
	static class ViewHolder{
		private TextView tv_company;
		private TextView tv_jyms;
		private TextView tv_zycp;
		private TextView tv_msg;
		private TextView tv_address;
	}

}
