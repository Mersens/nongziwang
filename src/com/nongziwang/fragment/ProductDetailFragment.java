package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import android.annotation.SuppressLint;
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

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.ProductDetailFragmentActivity;
import com.nongziwang.adapter.MyProductDetailAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.ToastUtils;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;

@SuppressLint("ClickableViewAccessibility")
public class ProductDetailFragment extends BaseFragment implements
IXListViewListener, EventListener{
	private View view;
	private String id;
	private static final String URL=AppConstants.SERVICE_ADDRESS+"chanpinsousuo/gotoChanpinXiangqing";
	private static final String TAG="ProductDetailFragment";
	private ChanPinBean bean;
	private RelativeLayout layout_loading;
	private XListView listView;
	private List<ChanPinBean> list;
	private MyProductDetailAdapter adapter;
	private Handler handler;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_product_detail, container,
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
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(false);
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
	
	
	
	public void setDatas(){
		list=new ArrayList<ChanPinBean>();
		list.add(bean);
		adapter=new MyProductDetailAdapter(list, getActivity());
		listView.setAdapter(adapter);
	}

	@Override
	protected void lazyLoad() {

	}

	public static Fragment getInstance(String params) {
		ProductDetailFragment fragment = new ProductDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	public void onRefresh() {
		
	}

	@Override
	public void onLoadMore() {
		Message msg = new Message();
		msg.arg1 = ProductDetailFragmentActivity.LOADING;
		handler.sendMessageDelayed(msg, 1000);
	}



}
