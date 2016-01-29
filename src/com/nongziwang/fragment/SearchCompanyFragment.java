package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.SearchCompanyAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.CompanyBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SearchCompanyFragment extends BaseFragment implements
IXListViewListener, EventListener{
	private View view; 
	private XListView listview_search_other;
	private SearchCompanyAdapter adapter;
	private String param;
	private RelativeLayout layout_loading;
	private static final String URL=AppConstants.SERVICE_ADDRESS+"gongsisousuo/getGongsiSousuoData";
	private static final String TAG = "SearchCompanyFragment";
	private int currpage=1;
	private Context context;
	private List<CompanyBean> lists=new ArrayList<CompanyBean>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.listview_search_results_others, null);
		param=getArguments().getString("params");
		context=view.getContext();
		initViews();
		initDatas();
		return view;
	}
	private void initViews() {
		layout_loading=(RelativeLayout) view.findViewById(R.id.layout_loading);
		listview_search_other=(XListView) view.findViewById(R.id.listview_search_other);
		listview_search_other.setPullLoadEnable(false);
		listview_search_other.setPullRefreshEnable(false);
		listview_search_other.setXListViewListener(this);

	}
	
	public void initDatas(){
		RequestParams params=new RequestParams();
		params.put("currpage", currpage + "");
		params.put("keywords", param);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code=JsonUtils.getCode(arg2);
				if("0".equals(code)){
					Toast.makeText(context, "当前页码输入不正确!", Toast.LENGTH_SHORT)
					.show();
				}else if("1".equals(code)){
					try {
						List<CompanyBean> list=JsonUtils.getCompanyInfo(arg2);
						lists.addAll(list);
						adapter=new SearchCompanyAdapter(list, getActivity());
						listview_search_other.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if("2".equals(code)){
					Toast.makeText(context, "没有查询到符合的公司信息!", Toast.LENGTH_SHORT)
					.show();
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg2 == null ? "" : arg2);
				Toast.makeText(context, "请求数据失败!", Toast.LENGTH_SHORT)
						.show();
			}
			@Override
			public void onFinish() {
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
		
		
	}
	
	public static Fragment getInstance(String params) {
		SearchCompanyFragment fragment = new SearchCompanyFragment();
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
