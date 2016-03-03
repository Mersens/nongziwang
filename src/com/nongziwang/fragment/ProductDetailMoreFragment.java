package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.ProductDetailFragmentActivity;
import com.nongziwang.adapter.ProductDetailAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.ToastUtils;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ProductDetailMoreFragment extends BaseFragment implements
		IXListViewListener, EventListener {
	private View view;
	private XListView listView;
	private List<String> list;
	private Handler handler;
	private static final String URL=AppConstants.SERVICE_ADDRESS+"chanpinsousuo/gotoChanpinXiangqing";
	private static final String TAG="ProductDetailMoreFragment";
	private String id;
	private RelativeLayout layout_loading;
	private ChanPinBean bean;
	private ProductDetailAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_product_detail_more, container,
				false);
		ProductDetailFragmentActivity activity = (ProductDetailFragmentActivity) getActivity();
		handler = activity.handler;
		id=getArguments().getString("params");
		initViews();
		initDatas();
		return view;
	}

	private void initViews() {
		layout_loading=(RelativeLayout) view.findViewById(R.id.layout_loading);
		listView = (XListView) view.findViewById(R.id.listView);
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);
	}

	public void initDatas(){
		RequestParams params=new RequestParams();
		params.put("chanpinid", id);
		
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code =JsonUtils.getCode(arg2);
				if("0".equals(code)){
					ToastUtils.showMessage(view.getContext(), "产品id 为空!");
				}else if("1".equals(code)){
					try {
						 bean=JsonUtils.getChanPinDetailInfo(arg2);
						 ProductDetailFragmentActivity.bean=bean;
						 setDatas();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}else if("2".equals(code)){
					ToastUtils.showMessage(view.getContext(), "找不到该产品信息!");
				}
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				ToastUtils.showMessage(view.getContext(), "数据获取失败！");
				Log.e(TAG, arg2==null?"":arg2);
			}
			@Override
			public void onFinish() {
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
	}
	
	
	private void setDatas() {
		list=new ArrayList<String>();
		list.add(bean.getDetail());
		adapter=new ProductDetailAdapter(list, getActivity());
		listView.setAdapter(adapter);
	}
	@Override
	protected void lazyLoad() {

	}

	public static Fragment getInstance(String params) {
		ProductDetailMoreFragment fragment = new ProductDetailMoreFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onRefresh() {
		Message msg = new Message();
		msg.arg1 = ProductDetailFragmentActivity.LOADINGBACK;
		handler.sendMessageDelayed(msg, 1000);
	}

	@Override
	public void onLoadMore() {

	}


}
