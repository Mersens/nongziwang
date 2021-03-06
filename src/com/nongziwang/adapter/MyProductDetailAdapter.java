package com.nongziwang.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nongziwang.activity.MyShopsFragmentActivity;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.ImageLoadOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyProductDetailAdapter extends BaseListAdapter<ChanPinBean>{
	private List<ChanPinBean> list;
	public MyProductDetailAdapter(List<ChanPinBean> list, Context context) {
		super(list, context);
		this.list=list;
	}

	@Override
	public View getContentView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			holder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.layout_product_detail_main_item, parent,false);
			holder.img_dp_logo=(ImageView) convertView.findViewById(R.id.img_dp_logo);
			holder.pro_image=(ImageView) convertView.findViewById(R.id.pro_image);
			holder.tv_title=(TextView) convertView.findViewById(R.id.tv_title);
			holder.tv_address=(TextView) convertView.findViewById(R.id.tv_address);
			holder.tv_all_sp=(TextView) convertView.findViewById(R.id.tv_all_sp);
			holder.tv_into_dp=(TextView) convertView.findViewById(R.id.tv_into_dp);
			holder.tv_dp_name=(TextView) convertView.findViewById(R.id.tv_dp_name);
			holder.tv_jg=(TextView) convertView.findViewById(R.id.tv_jg);
			holder.tv_yf=(TextView) convertView.findViewById(R.id.tv_yf);
			convertView.setTag(holder);
 		}else{
			holder=(ViewHolder) convertView.getTag();
		}

		ChanPinBean bean=list.get(position);
		ImageLoader.getInstance().displayImage(bean.getChanpinimg(),holder.pro_image,ImageLoadOptions.getOptions() );
		ImageLoader.getInstance().displayImage(bean.getGslogo(),holder.img_dp_logo,ImageLoadOptions.getOptions() );
		holder.tv_title.setText(bean.getTitle());
		holder.tv_address.setText("发货 "+bean.getProvince());
		holder.tv_dp_name.setText(bean.getDianpuname());
		holder.tv_all_sp.setOnClickListener(new MyOnClickListener(position));
		holder.tv_into_dp.setOnClickListener(new MyOnClickListener(position));
		holder.tv_jg.setText("¥ "+bean.getJiage());
		holder.tv_yf.setText(bean.getYunfei());
		return convertView;
	}

	class MyOnClickListener implements OnClickListener{
		private int pos;
		public MyOnClickListener(int pos){
			this.pos=pos;
		}

		@Override
		public void onClick(View v) {
	      intentAction((Activity)context, MyShopsFragmentActivity.class,list.get(pos).getDianpuid());
		}
		
	}
	public <T> void intentAction(Activity context, Class<T> cls,String params) {
		Intent intent = new Intent(context, cls);
		intent.putExtra("params", params);
		context.startActivity(intent);
		context.overridePendingTransition(R.anim.left_in,
				R.anim.left_out);
	}
	static class ViewHolder{
		private ImageView pro_image;
		private ImageView img_dp_logo;
		private TextView tv_title;
		private TextView tv_address;
		private TextView tv_all_sp;
		private TextView tv_into_dp;
		private TextView tv_dp_name;
		private TextView tv_jg;
		private TextView tv_yf;
	}
}
