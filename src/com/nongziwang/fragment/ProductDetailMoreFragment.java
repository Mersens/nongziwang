package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import com.nongziwang.activity.ProductDetailFragmentActivity;
import com.nongziwang.adapter.ProductDetailAdapter;
import com.nongziwang.main.R;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProductDetailMoreFragment extends BaseFragment implements
		IXListViewListener, EventListener {
	private View view;
	private XListView listView;
	private List<String> list;
	private Handler handler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_product_detail_more, container,
				false);
		ProductDetailFragmentActivity activity = (ProductDetailFragmentActivity) getActivity();
		handler = activity.handler;
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		listView = (XListView) view.findViewById(R.id.listView);
		//不允许上拉加载
		listView.setPullLoadEnable(false);
		// 允许下拉
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);
	}

	private void initEvent() {
		list = new ArrayList<String>();
		for (int i = 0; i < 6; i++) {
			list.add(i + "");
		}
		listView.setAdapter(new ProductDetailAdapter(list, getActivity()));

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
		// TODO Auto-generated method stub

	}

}
