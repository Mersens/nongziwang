package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.ProductDetailFragmentActivity;
import com.nongziwang.activity.SearchResultsFragmentActivity;
import com.nongziwang.adapter.SearchResultsAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.NewsBean;
import com.nongziwang.entity.ProductBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SearchResultsByJiaGeFragment extends BaseFragment implements
		IXListViewListener, EventListener {
	private View view;
	private XListView productlistview;
	private SearchResultsAdapter adapter;
	private RelativeLayout layout_loading;
	private String param;
	private List<ProductBean> lists=new ArrayList<ProductBean>();
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "chanpinsousuo/getChanpinSousuoByKeywords";
	private static final String LEIMUIDURL = AppConstants.SERVICE_ADDRESS
			+ "chanpinsousuo/getChanpinByLeimuId";
	private static final String TAG = "SearchResultsFragment";
	private int currpage = 1;
	private  int jiagepaixu=0;
	private Context context;
	private String pinpaiid = null;
	private String yongtuid = null;
	private String leimuid=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.listview_search_results, container,
				false);
		context=view.getContext();
		param = getArguments().getString("params");
		jiagepaixu=getArguments().getInt("jiagepaixu");
		initView();
		initEvent();
		initDatas();
		return view;
	}

	private void initView() {
		layout_loading = (RelativeLayout) view
				.findViewById(R.id.layout_loading);
		productlistview = (XListView) view.findViewById(R.id.productlistview);
		productlistview.setPullLoadEnable(true);
		productlistview.setPullRefreshEnable(true);
		productlistview.setXListViewListener(this);
	}

	private void initEvent() {

		productlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ProductBean bean=lists.get(position-1);
				intentAction(getActivity(), ProductDetailFragmentActivity.class,bean.getChanpinid());
			}
		});
	}

	private void initDatas() {
		pinpaiid = SearchResultsFragmentActivity.pinpaiid;
		yongtuid = SearchResultsFragmentActivity.yongtuid;
		leimuid=SearchResultsFragmentActivity.leimuid;
		RequestParams params = new RequestParams();
		params.put("currpage", currpage + "");
		params.put("keywords", param);
		params.put("jiagepaixu", jiagepaixu+"");
		if (!TextUtils.isEmpty(pinpaiid)) {
			params.put("pinpaiid", pinpaiid);
		}
		if (!TextUtils.isEmpty(yongtuid)) {
			params.put("yongtuid", yongtuid);
		}
		if(!TextUtils.isEmpty(leimuid)){
			params.put("leimuid", leimuid);
		}
		HttpUtils.doPost(leimuid==null?URL:LEIMUIDURL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(context, " 当前页码输入不正确!",
							Toast.LENGTH_SHORT).show();
				} else if ("1".equals(code)) {
					try {
						List<ProductBean> list = JsonUtils.getProductInfo(arg2);
						lists.addAll(list);
						adapter = new SearchResultsAdapter(list, view
								.getContext());
						productlistview.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if ("2".equals(code)) {
					Toast.makeText(context, " 没有符合的产品信息!",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
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

	public static Fragment getInstance(String params,int jiagepaixu) {
		SearchResultsByJiaGeFragment fragment = new SearchResultsByJiaGeFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		bundle.putInt("jiagepaixu", jiagepaixu);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh() {
		currpage = 1;
		RequestParams params = new RequestParams();
		params.put("currpage", currpage + "");
		params.put("keywords", param);
		params.put("jiagepaixu", jiagepaixu+"");
		if (!TextUtils.isEmpty(pinpaiid)) {
			params.put("pinpaiid", pinpaiid);
		}
		if (!TextUtils.isEmpty(yongtuid)) {
			params.put("yongtuid", yongtuid);
		}
		if(!TextUtils.isEmpty(leimuid)){
			params.put("leimuid", leimuid);
		}
		HttpUtils.doPost(leimuid==null?URL:LEIMUIDURL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(context, " 当前页码输入不正确!",
							Toast.LENGTH_SHORT).show();
				} else if ("1".equals(code)) {
					try {
						lists.clear();
						List<ProductBean> list1 = JsonUtils.getProductInfo(arg2);
						lists.addAll(list1);
						if(adapter!=null){
							adapter=null;
						}
						adapter = new SearchResultsAdapter(list1,
								getActivity());
						productlistview.setAdapter(adapter);
						onLoadFinsh();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("2".equals(code)) {
					Toast.makeText(context, " 没有符合的产品信息!",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				Toast.makeText(context, "请求数据失败!", Toast.LENGTH_SHORT)
						.show();
			}
			
			@Override
			public void onFinish() {
				super.onFinish();
				onLoadFinsh();
			}
		});

	}

	@Override
	public void onLoadMore() {
		currpage++;
		RequestParams params = new RequestParams();
		params.put("currpage", currpage + "");
		params.put("keywords", param);
		params.put("jiagepaixu", jiagepaixu+"");
		if (!TextUtils.isEmpty(pinpaiid)) {
			params.put("pinpaiid", pinpaiid);
		}
		if (!TextUtils.isEmpty(yongtuid)) {
			params.put("yongtuid", yongtuid);
		}
		if(!TextUtils.isEmpty(leimuid)){
			params.put("leimuid", leimuid);
		}
		HttpUtils.doPost(leimuid==null?URL:LEIMUIDURL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(context, " 当前页码输入不正确!",
							Toast.LENGTH_SHORT).show();
				} else if ("1".equals(code)) {
					try {

						List<ProductBean> list2 = JsonUtils.getProductInfo(arg2);
						if (list2.size() < 10) {
							productlistview.setPullLoadEnable(false);
						}
						lists.addAll(list2);
						adapter.addAll(list2);
						adapter.notifyDataSetChanged();
						onLoadFinsh();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("2".equals(code)) {
					Toast.makeText(context, " 没有符合的产品信息!",
							Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				Toast.makeText(context, "请求数据失败!", Toast.LENGTH_SHORT)
						.show();
			}
			
			@Override
			public void onFinish() {
				super.onFinish();
				onLoadFinsh();
			}

		});
	}
	public void onLoadFinsh(){
		productlistview.stopLoadMore();
		productlistview.stopRefresh();
	}

}
