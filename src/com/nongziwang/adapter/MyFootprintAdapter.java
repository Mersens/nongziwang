package com.nongziwang.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.entity.FootprintBean;
import com.nongziwang.main.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyFootprintAdapter extends BaseListAdapter<FootprintBean> {
	private List<FootprintBean> list;
	private Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
	private Map<Integer, Boolean> isHideMap = new HashMap<Integer, Boolean>();

	public MyFootprintAdapter(List<FootprintBean> list, Context context) {
		super(list, context);
        this.list=list;
        configCheckMap(false);
        configHideCheckMap(true);
	}
	public void configHideCheckMap(boolean bool) {
		for (int i = 0; i < list.size(); i++) {
			isHideMap.put(i, bool);
		}

	}
	public void configCheckMap(boolean bool) {
		for (int i = 0; i < list.size(); i++) {
			isCheckMap.put(i, bool);
		}

	}
	@Override
	public View getContentView(final int position, View convertView, ViewGroup parent) {
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
		holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				isCheckMap.put(position, isChecked);
			}
		});
		if (isCheckMap.get(position) == null) {
			isCheckMap.put(position, false);
		}
		holder.checkbox.setChecked(isCheckMap.get(position));
		if(!isHideMap.get(position)){
			holder.checkbox.setVisibility(View.VISIBLE);
			
		}else{
			holder.checkbox.setVisibility(View.GONE);
		}
		
		return convertView;
	}

	public void remove(int position) {
		this.list.remove(position);
	}

	public Map<Integer, Boolean> getCheckMap() {
		return this.isCheckMap;
	}
	
	public List<FootprintBean> getDatas(){
		return this.list;
	}
	
	public static class ViewHolder{
		public CheckBox checkbox;
		private ImageView img_pro;
		private TextView tv_company_name;
		private TextView tv_price;
		private TextView tv_address;
	}
}
