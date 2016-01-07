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

public class GridViewAdapter extends BaseListAdapter<String>{

	public GridViewAdapter(List<String> list, Context context) {
		super(list, context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_myproduct_item, parent,false);
			holder.checkbox=(CheckBox) convertView.findViewById(R.id.checkbox);
			holder.img_pro=(ImageView) convertView.findViewById(R.id.img_pro);
			holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_money=(TextView) convertView.findViewById(R.id.tv_money);
			holder.img_more=(ImageView) convertView.findViewById(R.id.img_more);
			convertView.setTag(holder);

		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.img_more.setOnClickListener(new MyOnClickListener(position));
		return convertView;
	}

	static class ViewHolder{
		private CheckBox checkbox;
		private ImageView img_pro;
		private TextView tv_title;
		private TextView tv_money;
		private ImageView img_more;
	}
	class MyOnClickListener implements OnClickListener{
		private int pos;
		private MyOnClickListener(int pos){
			this.pos=pos;
		}
		@Override
		public void onClick(View v) {
			
		}
		 
	 }
}
