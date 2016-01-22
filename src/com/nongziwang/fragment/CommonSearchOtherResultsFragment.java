package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.CommonSearchResultsAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

public class CommonSearchOtherResultsFragment extends BaseFragment implements
IXListViewListener, EventListener {
	private View view;
	private XListView listview_search_other;
	private List<String> list;
	private CommonSearchResultsAdapter adapter;
	private RelativeLayout layout_loading;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.listview_search_results_others, container,false);
		initViews();
		initDatas();
		return view;
	}
	
	private void initViews() {
		layout_loading= (RelativeLayout) view.findViewById(R.id.layout_loading);
		listview_search_other=(XListView) view.findViewById(R.id.listview_search_other);
		listview_search_other.setPullLoadEnable(true);
		listview_search_other.setPullRefreshEnable(true);
		listview_search_other.setXListViewListener(this);
/*		adapter=new CommonSearchResultsAdapter(list, getActivity());
		listview_search_other.setAdapter(adapter);*/
	}
	
	public void initDatas(){
		HttpUtils.doPost(AppConstants.SERVICE_ADDRESS+"chanpinsousuo/getChanpinPinpai", new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				// TODO Auto-generated method stub
				System.out.println(arg2);
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
	}
	public static Fragment getInstance(String params) {
		CommonSearchOtherResultsFragment fragment = new CommonSearchOtherResultsFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

}
