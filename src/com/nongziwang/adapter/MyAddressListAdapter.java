package com.nongziwang.adapter;

import java.util.List;

import com.nongziwang.activity.MyAddressFragmentActivity;
import com.nongziwang.main.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAddressListAdapter extends BaseListAdapter<String> {
	private Context context;

	public MyAddressListAdapter(List<String> list, Context context) {
		super(list, context);
		this.context=context;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_myaddresslist_item, parent,false);
			holder.tv_address=(TextView) convertView.findViewById(R.id.tv_address);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_tel=(TextView) convertView.findViewById(R.id.tv_tel);
			holder.tv_editor=(TextView) convertView.findViewById(R.id.tv_editor);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_editor.setOnClickListener(new MyEditorOnClick(position));
		return convertView;
	}

	static class ViewHolder{
		private TextView tv_name;
		private TextView tv_tel;
		private TextView tv_address;
		private TextView tv_editor;
	}
	
	class MyEditorOnClick implements OnClickListener{
		private int pos;

		public MyEditorOnClick(int pos){
			this.pos=pos;
		}
		@Override
		public void onClick(View v) {
			Intent mIntent = new Intent(MyAddressFragmentActivity.ACTION_EDITOR);  
			context.sendBroadcast(mIntent);  
		}
		
	}
}
