package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.GongsiDetailFragmentActivity;
import com.nongziwang.adapter.SearchCompanyAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.CompanyBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.ToastUtils;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
		listview_search_other.setPullLoadEnable(true);
		listview_search_other.setPullRefreshEnable(true);
		listview_search_other.setXListViewListener(this);
		listview_search_other.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CompanyBean bean=lists.get(position-1);
				Intent intent = new Intent(getActivity(), GongsiDetailFragmentActivity.class);
				intent.putExtra("dianpuid", bean.getDianpuid());
				intent.putExtra("gongsiid", bean.getGongsiid());
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.left_in,
						R.anim.left_out);
			}
		});

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
					ToastUtils.showMessage(context, "当前页码输入不正确!");
				}else if("1".equals(code)){
					try {
						List<CompanyBean> list=JsonUtils.getCompanyInfo(arg2);
						if(list.size()<10){
						listview_search_other.setPullLoadEnable(false);
						}
						lists.addAll(list);
						adapter=new SearchCompanyAdapter(list, getActivity());
						listview_search_other.setAdapter(adapter);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else if("2".equals(code)){
					ToastUtils.showMessage(context, "没有查询到符合的公司信息!");
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(context, "请求数据失败!");
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
		
	}
	@Override
	public void onRefresh() {
		currpage=1;
		RequestParams params=new RequestParams();
		params.put("currpage", currpage + "");
		params.put("keywords", param);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code=JsonUtils.getCode(arg2);
				if("0".equals(code)){
					ToastUtils.showMessage(context, "当前页码输入不正确!");
				}else if("1".equals(code)){
					try {
						lists.clear();
						List<CompanyBean> refreslist=JsonUtils.getCompanyInfo(arg2);
						if(refreslist.size()<10){
						listview_search_other.setPullLoadEnable(false);
						}
						lists.addAll(refreslist);
						if(adapter!=null){
							adapter=null;
						}
						adapter=new SearchCompanyAdapter(refreslist, getActivity());
						listview_search_other.setAdapter(adapter);
						onLoadFinsh();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else if("2".equals(code)){
					ToastUtils.showMessage(context, "没有查询到符合的公司信息!");
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(context, "请求数据失败!");
			}

		});
	}
	@Override
	public void onLoadMore() {
		currpage++;
		RequestParams params=new RequestParams();
		params.put("currpage", currpage + "");
		params.put("keywords", param);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code=JsonUtils.getCode(arg2);
				if("0".equals(code)){
					ToastUtils.showMessage(context, "当前页码输入不正确!");
				}else if("1".equals(code)){
					try {
						List<CompanyBean> loadmorelist=JsonUtils.getCompanyInfo(arg2);
						lists.addAll(loadmorelist);
						adapter.addAll(loadmorelist);
						adapter.notifyDataSetChanged();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else if("2".equals(code)){
					ToastUtils.showMessage(context, "没有查询到符合的公司信息!");
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(context, "请求数据失败!");
			}
		});
	}
	public void onLoadFinsh(){
		listview_search_other.stopLoadMore();
		listview_search_other.stopRefresh();
	}

}
