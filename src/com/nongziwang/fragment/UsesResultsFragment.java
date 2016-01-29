package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.SearchResultsFragmentActivity;
import com.nongziwang.adapter.CommonSearchResultsAdapter;
import com.nongziwang.adapter.UseshResultsAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.PinPaiBean;
import com.nongziwang.entity.UsesBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class UsesResultsFragment extends BaseFragment implements
IXListViewListener, EventListener {
	private View view;
	private XListView listview_search_other;
	private List<UsesBean> lists =new ArrayList<UsesBean>();
	private UseshResultsAdapter adapter;
	private RelativeLayout layout_loading;
	private Context context;
	private static final String URL=AppConstants.SERVICE_ADDRESS+"chanpinsousuo/getChanpinYongtu";
	private static final String TAG = "UsesResultsFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.listview_search_results_others, container,false);
		context=view.getContext();
		initViews();
		initDatas();
		return view;
	}
	
	private void initViews() {
		layout_loading= (RelativeLayout) view.findViewById(R.id.layout_loading);
		listview_search_other=(XListView) view.findViewById(R.id.listview_search_other);
		listview_search_other.setPullLoadEnable(false);
		listview_search_other.setPullRefreshEnable(false);
		listview_search_other.setXListViewListener(this);
		listview_search_other.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				UsesBean bean=lists.get(position);
				Intent intent=new Intent(SearchResultsFragmentActivity.ACTION_YONGTUID);
				intent.putExtra("yongtuid",bean.getUsesid() );
				context.sendBroadcast(intent);
			}
		});
	}
	
	public void initDatas(){
		HttpUtils.doPost(URL, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {

				String code=JsonUtils.getCode(arg2);
				if("0".equals(code)){
					Toast.makeText(context, "没有产品用途!",
							Toast.LENGTH_SHORT).show();
				}else if("1".equals(code)){
					try {
						List<UsesBean> list=JsonUtils.getUsesInfo(arg2);
						lists.addAll(list);
						adapter=new UseshResultsAdapter(list, context);
						listview_search_other.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
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
				// TODO Auto-generated method stub
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
	}
	public static Fragment getInstance(String params) {
		UsesResultsFragment fragment = new UsesResultsFragment();
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
		
	}


	@Override
	public void onLoadMore() {
		
	}

}
