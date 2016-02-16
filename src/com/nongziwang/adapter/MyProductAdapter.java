package com.nongziwang.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.LoginActivity;
import com.nongziwang.activity.ProductUpdateActivity;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.fragment.ProductManagementFragment;
import com.nongziwang.main.R;
import com.nongziwang.utils.DateUtil;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.ImageLoadOptions;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.view.DialogTips;
import com.nongziwang.view.DialogWaiting;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyProductAdapter extends BaseListAdapter<ChanPinBean> {
	private List<ChanPinBean> list;
	private Map<Integer, Boolean> isCheckMap = new HashMap<Integer, Boolean>();
	private boolean isShngjia = false;
	private DialogWaiting dialog;

	public MyProductAdapter(List<ChanPinBean> list, Context context) {
		super(list, context);
		this.list = list;
		configCheckMap(false);
		int xinxiststic = ProductManagementFragment.xinxiststic;
		if (xinxiststic == 3) {
			isShngjia = true;
		}

	}

	public void configCheckMap(boolean bool) {
		for (int i = 0; i < list.size(); i++) {
			isCheckMap.put(i, bool);
		}
	}

	@Override
	public View getContentView(final int position, View convertView,
			ViewGroup parent) {

		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listview_pro_manage_item,
					parent, false);
			holder.checkbox = (CheckBox) convertView
					.findViewById(R.id.checkbox);
			holder.img_pro = (ImageView) convertView.findViewById(R.id.img_pro);
			holder.tv_pro_title = (TextView) convertView
					.findViewById(R.id.tv_pro_title);
			holder.tv_pro_msg = (TextView) convertView
					.findViewById(R.id.tv_pro_msg);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_jy_static = (TextView) convertView
					.findViewById(R.id.tv_jy_static);
			holder.tv_update = (TextView) convertView
					.findViewById(R.id.tv_update);
			holder.tv_xj = (TextView) convertView.findViewById(R.id.tv_xj);
			holder.layout_cz=(RelativeLayout) convertView.findViewById(R.id.layout_cz);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ChanPinBean bean = list.get(position);
		ImageLoader.getInstance().displayImage(bean.getChanpinimg(),
				holder.img_pro, ImageLoadOptions.getOptions());
		holder.tv_pro_title.setText(bean.getTitle());
		holder.tv_pro_msg.setText(bean.getMiaoshu());
		holder.tv_time.setText(DateUtil.getStamp2Time(bean.getAddtime()));
		if (isShngjia) {
			holder.tv_xj.setText("上架");
		} else {
			holder.tv_xj.setText("下架");
		}

		if (ProductManagementFragment.xinxiststic == 0) {
			holder.layout_cz.setVisibility(View.GONE);
			holder.checkbox.setVisibility(View.GONE);

		}
		if(ProductManagementFragment.xinxiststic == 2){
			holder.tv_xj.setVisibility(View.GONE);
		}
		holder.tv_update.setOnClickListener(new MyOnClickListener(position,
				Style.UPDATE));
		holder.tv_xj.setOnClickListener(new MyOnClickListener(position,
				Style.UPDOWN));
		holder.checkbox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
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

		return convertView;
	}

	public void remove(int position) {
		this.list.remove(position);
	}

	public Map<Integer, Boolean> getCheckMap() {
		return this.isCheckMap;
	}

	public List<ChanPinBean> getDatas() {
		return this.list;
	}

	enum Style {
		UPDATE, UPDOWN;
	}

	public static class ViewHolder {
		public CheckBox checkbox;
		private ImageView img_pro;
		private TextView tv_pro_title;
		private TextView tv_pro_msg;
		private TextView tv_time;
		private TextView tv_jy_static;
		private TextView tv_update;
		private TextView tv_xj;
		private RelativeLayout layout_cz;
	}

	class MyOnClickListener implements OnClickListener {
		private int pos;
		private Style style;

		public MyOnClickListener(int pos, Style style) {
			this.pos = pos;
			this.style = style;
		}

		@Override
		public void onClick(View v) {
			switch (style) {
			case UPDATE:
				DialogTips dialogtips = new DialogTips(context, "温馨提示",
						"您确定要修改?", "确定", true, true);
				dialogtips.SetOnSuccessListener(new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface,
							int userId) {
						Intent intent = new Intent(context, ProductUpdateActivity.class);
						intent.putExtra("params", list.get(pos).getChanpinid());
						context.startActivity(intent);
						((Activity) context).overridePendingTransition(R.anim.left_in,
								R.anim.left_out);

					}
				});
				dialogtips.show();
				dialogtips = null;
				break;
			case UPDOWN:
				if (isShngjia) {
					DialogTips dialog = new DialogTips(context, "温馨提示",
							"您确定要上架?", "确定", true, true);
					dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogInterface,
								int userId) {
							doShangjia(pos);
						}
					});
					dialog.show();
					dialog = null;
				} else {
					DialogTips dialog = new DialogTips(context, "温馨提示",
							"您确定要下架?", "确定", true, true);
					dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogInterface,
								int userId) {
							doXiajia(pos);
						}
					});
					dialog.show();
					dialog = null;

				}
				break;
			}
		}
	}

	public void doShangjia(final int pos) {
		ChanPinBean bean = list.get(pos);
		RequestParams params = new RequestParams();
		params.put("chanpinids", bean.getChanpinid());
		HttpUtils.doPost(ProductManagementFragment.SHANGJIA_URL, params,
				new TextHttpResponseHandler() {
					@Override
					public void onStart() {
						dialog = new DialogWaiting(context);
						dialog.show();
						super.onStart();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2) {
						String code = JsonUtils.getCode(arg2);
						if ("0".equals(code)) {
							Toast.makeText(context, "产品id为空!",
									Toast.LENGTH_SHORT).show();
						} else if ("1".equals(code)) {
							getCheckMap().remove(pos);
							remove(pos);
							DialogTips dialog = new DialogTips(context,
									"上架成功！", "确定");
							dialog.show();
							dialog = null;
							notifyDataSetChanged();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable arg3) {
						Toast.makeText(context, "上架失败!", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onFinish() {
						dialogDismiss();
						super.onFinish();
					}
				});

	}

	public void doXiajia(final int pos) {
		ChanPinBean bean = list.get(pos);
		RequestParams params = new RequestParams();
		params.put("chanpinids", bean.getChanpinid());
		HttpUtils.doPost(ProductManagementFragment.XIAJIA_URL, params,
				new TextHttpResponseHandler() {
					@Override
					public void onStart() {
						dialog = new DialogWaiting(context);
						dialog.show();
						super.onStart();
					}

					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2) {
						String code = JsonUtils.getCode(arg2);
						if ("0".equals(code)) {
							Toast.makeText(context, "产品id为空!",
									Toast.LENGTH_SHORT).show();
						} else if ("1".equals(code)) {
							getCheckMap().remove(pos);
							remove(pos);
							DialogTips dialog = new DialogTips(context,
									"下架成功！", "确定");
							dialog.show();
							dialog = null;
							notifyDataSetChanged();
						}
					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable arg3) {
						Toast.makeText(context, "上架失败!", Toast.LENGTH_SHORT)
								.show();
					}

					@Override
					public void onFinish() {
						dialogDismiss();
						super.onFinish();
					}
				});
	}

	public void dialogDismiss() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

}
