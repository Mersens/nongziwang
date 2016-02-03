package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.entity.NewsBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.DateUtil;
import com.nongziwang.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class IndustryInfoAdapter extends BaseListAdapter<NewsBean>{
	private List<NewsBean> list;

	public IndustryInfoAdapter(List<NewsBean> list, Context context) {
		super(list, context);
		this.list=list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_industry_info_item, parent,false);
			holder.info_image=(ImageView) convertView.findViewById(R.id.info_image);
			holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_msg=(TextView) convertView.findViewById(R.id.tv_msg);
			holder.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		NewsBean bean=list.get(position);
		ImageLoader.getInstance().displayImage(bean.getImgsrc(), holder.info_image,
				ImageLoadOptions.getOptions());
		holder.tv_title.setText(bean.getTitle());
		holder.tv_msg.setText(bean.getCenter().toString().trim());
		holder.tv_time.setText(DateUtil.getStamp2Time(bean.getAddtime()));
		return convertView;
	}

	static class ViewHolder{
		private ImageView info_image;
		private TextView tv_title;
		private TextView tv_msg;
		private TextView tv_time;
	}
	
}
