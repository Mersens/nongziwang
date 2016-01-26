package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.ReleaseProductFragmentActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.LeiMuBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;

public class ReleaseProductTypeFragment extends BaseFragment {
	private View view;
	private LinearLayout layout_title;
	private LayoutInflater mInflater;
	private int selectColor;
	private int tvUnSelectColor;
	private int viewUnSelectColor;
	private List<TextView> tvlist_menu_0;
	private List<View> viewlist_menu_0;
	private List<TextView> tvlist_menu_1;
	private List<View> viewlist_menu_1;
	private List<TextView> tvlist_menu_2;
	private List<View> viewlist_menu_2;
	private List<LeiMuBean> list;
	private List<LeiMuBean> list1;
	private List<LeiMuBean> list2;
	private PopupWindow popupwindow;
	private PopupWindow popupwindow1;
	private ReleaseProductFragmentActivity activity;
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "leimu/getLeimuByParentId";
	private static final String TAG="ReleaseProductTypeFragment";
	private StringBuffer sbf;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_rele_pro_type, null);
		mInflater = LayoutInflater.from(getActivity());
		activity = (ReleaseProductFragmentActivity) getActivity();
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		tvlist_menu_0 = new ArrayList<TextView>();
		viewlist_menu_0 = new ArrayList<View>();
		tvlist_menu_1 = new ArrayList<TextView>();
		viewlist_menu_1 = new ArrayList<View>();
		tvlist_menu_2 = new ArrayList<TextView>();
		viewlist_menu_2 = new ArrayList<View>();
		layout_title = (LinearLayout) view.findViewById(R.id.layout_title);
		selectColor = getResources().getColor(R.color.title_yellow_text_color);
		tvUnSelectColor = getResources()
				.getColor(R.color.base_color_text_black);
		viewUnSelectColor = getResources().getColor(
				R.color.base_color_text_gray);
	}

	private void initEvent() {
		sbf=new StringBuffer();
		list = new ArrayList<LeiMuBean>();
		list1 = new ArrayList<LeiMuBean>();
		list2 = new ArrayList<LeiMuBean>();
		list.add(new LeiMuBean("1", "肥料", "0"));
		list.add(new LeiMuBean("2", "农药", "0"));
		list.add(new LeiMuBean("3", "种子", "0"));
		list.add(new LeiMuBean("4", "农机", "0"));
		list.add(new LeiMuBean("5", "农膜", "0"));

		for (int i = 0; i < list.size(); i++) {
			View view = mInflater
					.inflate(R.layout.listview_rele_pro_item, null);
			view.setId(i);
			TextView type_name = (TextView) view.findViewById(R.id.type_name);
			type_name.setText(list.get(i).getName());
			tvlist_menu_0.add(type_name);
			View bottom_view = view.findViewById(R.id.bottom_view);
			viewlist_menu_0.add(bottom_view);
			layout_title.addView(view);
			view.setOnClickListener(itemListener_0);
		}

	}

	private View.OnClickListener itemListener_0 = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			setTabs0(v.getId());
		}
	};
	private View.OnClickListener itemListener_1 = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			setTabs1(v.getId());
		}
	};
	private View.OnClickListener itemListener_2 = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			setTabs2(v.getId());
		}
	};

	public void setTabs0(int n) {
		for (int i = 0; i < list.size(); i++) {
			if (i != n) {
				tvlist_menu_0.get(i).setTextColor(tvUnSelectColor);
				viewlist_menu_0.get(i).setBackgroundColor(viewUnSelectColor);
			}
		}
		tvlist_menu_0.get(n).setTextColor(selectColor);
		viewlist_menu_0.get(n).setBackgroundColor(selectColor);
		doSearchMenu1(list.get(n).getLeimuid());
		//showPopMenu1();
	}

	public void setTabs1(int n) {
		for (int i = 0; i < list1.size(); i++) {
			if (i != n) {
				tvlist_menu_1.get(i).setTextColor(tvUnSelectColor);
				viewlist_menu_1.get(i).setBackgroundColor(viewUnSelectColor);
			}
		}
		tvlist_menu_1.get(n).setTextColor(selectColor);
		viewlist_menu_1.get(n).setBackgroundColor(selectColor);
		String id=list1.get(n).getLeimuid();
		sbf.append(id+":");
		doSearchMenu2(id);

	}

	public void setTabs2(int n) {
		for (int i = 0; i < list2.size(); i++) {
			if (i != n) {
				tvlist_menu_2.get(i).setTextColor(tvUnSelectColor);
				viewlist_menu_2.get(i).setBackgroundColor(viewUnSelectColor);
			}
		}
		tvlist_menu_2.get(n).setTextColor(selectColor);
		viewlist_menu_2.get(n).setBackgroundColor(selectColor);
		String id=list2.get(n).getLeimuid();
		sbf.append(id);
		doComplate();

	}

	public void doComplate(){
		/**
		 * 发送广播，更新UI
		 */
		if(popupwindow1 !=null && popupwindow1.isShowing()){
			popupwindow1.dismiss();
		}
		if(popupwindow !=null && popupwindow.isShowing()){
			popupwindow.dismiss();
		}
        Intent mIntent = new Intent(ReleaseProductFragmentActivity.ACTION_PRODUCT_MSG);
        mIntent.putExtra("ids", sbf.toString());
        activity.sendBroadcast(mIntent); 
	}
	
	
	public void doSearchMenu1(final String id){
		RequestParams params = new RequestParams();
		params.put("parentid",id);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						doComplate();
					} else if ("1".equals(code)) {
						try {
							list1 = JsonUtils.getLeiMuByInfo(arg2);
							if(list1.size()>0){
								showPopMenu1();
							}else{
								doComplate();
							}
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				
			}
		});
		
		
	}
	
	public void doSearchMenu2(final String id){
		RequestParams params = new RequestParams();
		params.put("parentid",id);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						doComplate();
					} else if ("1".equals(code)) {
						try {
							list2 = JsonUtils.getLeiMuByInfo(arg2);
							if(list2.size()>0){
								showPopMenu2();
							}else{
								doComplate();
							}
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				
			}
		});
		
	}
	
	
	/**
	 * 一级目录
	 */
	@SuppressWarnings("deprecation")
	public void showPopMenu1() {
		Rect frame = new Rect();
		getActivity().getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);
		int topHeight = frame.top + activity.getHeadViewHeight()
				+ Dp2Px(activity, 1);

		View pop_v1 = mInflater.inflate(R.layout.layout_rele_pro_type, null);
		LinearLayout layout = (LinearLayout) pop_v1
				.findViewById(R.id.layout_title);
		for (int i = 0; i < list1.size(); i++) {
			View view = mInflater
					.inflate(R.layout.listview_rele_pro_item, null);
			view.setId(i);
			TextView type_name = (TextView) view.findViewById(R.id.type_name);
			type_name.setText(list1.get(i).getName());
			tvlist_menu_1.add(type_name);
			View bottom_view = view.findViewById(R.id.bottom_view);
			viewlist_menu_1.add(bottom_view);
			layout.addView(view);
			view.setOnClickListener(itemListener_1);
		}
		int mScreenWidth = activity.getScreenWidth();
		int mScreenHeight = activity.getScreenHeight();
		popupwindow = new PopupWindow(pop_v1);
		int mWidth = (mScreenWidth * 5) / 6;
		popupwindow.setWidth(mWidth);
		popupwindow.setHeight(mScreenHeight - topHeight);
		popupwindow.setTouchable(true);
		popupwindow.setFocusable(true);
		popupwindow.setOutsideTouchable(true);
		popupwindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.layout_rele_pro_bg));
		popupwindow.setAnimationStyle(R.style.GrowFromRight);

		popupwindow.showAtLocation(pop_v1.findViewById(R.id.layout_main),
				Gravity.RIGHT | Gravity.TOP, 0, topHeight);
		popupwindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				popupwindow = null;
				tvlist_menu_1.clear();
				viewlist_menu_1.clear();

			}
		});

	}

	/**
	 * 二级目录
	 */
	@SuppressWarnings("deprecation")
	public void showPopMenu2() {
		Rect frame = new Rect();
		getActivity().getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);
		int topHeight = frame.top + activity.getHeadViewHeight()
				+ Dp2Px(activity, 1);

		View v = mInflater.inflate(R.layout.layout_rele_pro_type, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.layout_title);
		for (int i = 0; i < list2.size(); i++) {
			View view = mInflater
					.inflate(R.layout.listview_rele_pro_item, null);
			view.setId(i);
			TextView type_name = (TextView) view.findViewById(R.id.type_name);
			type_name.setText(list2.get(i).getName());
			tvlist_menu_2.add(type_name);
			View bottom_view = view.findViewById(R.id.bottom_view);
			viewlist_menu_2.add(bottom_view);
			layout.addView(view);
			view.setOnClickListener(itemListener_2);
		}
		int mScreenWidth = activity.getScreenWidth();
		int mScreenHeight = activity.getScreenHeight();
		popupwindow1 = new PopupWindow(v);
		int mWidth = mScreenWidth / 2;
		popupwindow1.setWidth(mWidth);
		popupwindow1.setHeight(mScreenHeight - topHeight);
		popupwindow1.setTouchable(true);
		popupwindow1.setFocusable(true);
		popupwindow1.setOutsideTouchable(true);
		popupwindow1.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.layout_rele_pro_bg));
		popupwindow1.setAnimationStyle(R.style.GrowFromRight);
		popupwindow1.showAtLocation(v.findViewById(R.id.layout_main),
				Gravity.RIGHT | Gravity.TOP, 0, topHeight);
		popupwindow1.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				popupwindow1 = null;
				tvlist_menu_2.clear();
				viewlist_menu_2.clear();

			}
		});
	}

	public static Fragment getInstance(String params) {
		ReleaseProductTypeFragment fragment = new ReleaseProductTypeFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	/**
	 * 把dp转化为px
	 * 
	 * @param context
	 * @param dp
	 * @return
	 */
	public int Dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	@Override
	protected void lazyLoad() {

	}

}
