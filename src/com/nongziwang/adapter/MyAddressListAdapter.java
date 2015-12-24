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
		View view=mInflater.inflate(R.layout.layout_myaddresslist_item, parent,false);
		TextView tv_editor=(TextView) view.findViewById(R.id.tv_editor);
		tv_editor.setOnClickListener(new MyEditorOnClick(position));
		return view;
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
