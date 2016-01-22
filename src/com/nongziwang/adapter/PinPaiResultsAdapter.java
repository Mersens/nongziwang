package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.entity.PinPaiBean;
import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PinPaiResultsAdapter extends BaseListAdapter<PinPaiBean> {
	private List<PinPaiBean>  list;


	public PinPaiResultsAdapter(List<PinPaiBean> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
		this.list=list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.common_search_results_item, parent,false);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_name.setText(list.get(position).getPinpainame());
		return convertView;
	}

	static class ViewHolder{
		private TextView tv_name;
	}
}
