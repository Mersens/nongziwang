package com.nongziwang.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongziwang.entity.ShareBean;
import com.nongziwang.main.R;

public class ShareAdapter extends BaseListAdapter<ShareBean>{
	private List<ShareBean> list;

	public ShareAdapter(List<ShareBean> list, Context context) {
		super(list, context);
		this.list=list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.gridview_item, null);
			holder.image=(ImageView) convertView.findViewById(R.id.image);
			holder.name=(TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
			
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		ShareBean bean=list.get(position);
		holder.image.setImageDrawable(bean.getmDrawable());
		holder.name.setText(bean.getmName());
		return convertView;
	}

	static class ViewHolder{
		private ImageView image;
		private TextView name;
	}
}
