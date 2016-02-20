package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.IndustryInfoDetialActivity;
import com.nongziwang.adapter.IndustryInfoAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.NewsBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.ToastUtils;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;

import android.content.Intent;
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

/**
 * 
 * @title IndustryInfoFragment
 * @description:行业资讯
 * @author Mersens
 * @time 2016年1月20日
 */
public class IndustryInfoFragment extends BaseFragment implements
		IXListViewListener, EventListener {
	private View view;
	private XListView listView;
	private boolean isPrepared;
	private static final String TAG = "IndustryInfoFragment";
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "news/getNewsData";
	private static final String ZHUANTI_URL = AppConstants.SERVICE_ADDRESS
			+ "news/getZhuantiData";
	private List<NewsBean> lists = new ArrayList<NewsBean>();
	private IndustryInfoAdapter adapter;
	private String type = null;
	private boolean isNews = false;
	private RelativeLayout layout_loading;
	private int currpage = 1;
	private int idx = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater
				.inflate(R.layout.layout_industry_info, container, false);
		type = getArguments().getString("params");
		isPrepared = true;
		initViews();
		lazyLoad();
		initEvent();
		return view;
	}

	private void initViews() {
		listView = (XListView) view.findViewById(R.id.listView);
		listView.setPullLoadEnable(true);
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);
		layout_loading = (RelativeLayout) view
				.findViewById(R.id.layout_loading);
	}

	private void initEvent() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String newsid = lists.get(position - 1).getNewsid();
				Intent intent = new Intent(getActivity(),
						IndustryInfoDetialActivity.class);
				intent.putExtra("newsid", newsid);
				intent.putExtra("isNews", isNews);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.left_in,
						R.anim.left_out);
			}
		});
	}

	public static Fragment getInstance(String params) {
		IndustryInfoFragment fragment = new IndustryInfoFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {

		if (!isPrepared || !isVisible) {
			return;
		}

		if (!TextUtils.isEmpty(type)) {
			idx = Integer.parseInt(type);
			switch (idx) {
			case 0:
				isNews = false;
				doSearchZhuanTi(idx);
				break;
			case 1:
				isNews = true;
				doSearchNews(idx);
				break;
			case 2:
				isNews = true;
				doSearchNews(idx);
				break;
			case 3:
				isNews = true;
				doSearchNews(idx);
				break;
			case 4:
				isNews = true;
				doSearchNews(idx);
				break;
			case 5:
				isNews = true;
				doSearchNews(idx);
				break;
			}
		}
	}

	public void doSearchNews(int idx) {
		RequestParams params = new RequestParams();
		params.put("typeid", idx + "");
		params.put("currpage", currpage + "");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ToastUtils.showMessage(getActivity(), "typeid为空!");
					} else if ("1".equals(code)) {
						try {
							List<NewsBean> newslist1 = JsonUtils.getNewsInfo(arg2);
							if (newslist1 != null && newslist1.size() > 0) {
								if (newslist1.size() < 10) {
									listView.setPullLoadEnable(false);
								}
								lists.addAll(newslist1);
								adapter = new IndustryInfoAdapter(newslist1,
										getActivity());
								listView.setAdapter(adapter);
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if ("2".equals(code)) {
						ToastUtils.showMessage(getActivity(), "页码输入错误!");
					} else if ("3".equals(code)) {
						ToastUtils.showMessage(getActivity(), "没有相关新闻!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(getActivity(), "请求数据失败!");
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
	}

	public void doSearchZhuanTi(int idx) {
		RequestParams params = new RequestParams();
		params.put("currpage", currpage + "");
		HttpUtils.doPost(ZHUANTI_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ToastUtils.showMessage(getActivity(), "当前页码输入不正确!");
					} else if ("1".equals(code)) {
						try {
							List<NewsBean> zhuangtilist = JsonUtils.getZhuanTiInfo(arg2);

							if (zhuangtilist != null && zhuangtilist.size() > 0) {
								if (zhuangtilist.size() < 10) {
									listView.setPullLoadEnable(false);
								}
								lists.addAll(zhuangtilist);
								adapter = new IndustryInfoAdapter(zhuangtilist,
										getActivity());
								listView.setAdapter(adapter);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if ("2".equals(code)) {
						ToastUtils.showMessage(getActivity(), "没有专题数据!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(getActivity(), "请求数据失败!");
			}

			@Override
			public void onFinish() {
				layout_loading.setVisibility(View.GONE);
				super.onFinish();
			}

		});
	}

	public void doNewsOnRefresh() {
		currpage = 1;
		RequestParams params = new RequestParams();
		params.put("typeid", idx + "");
		params.put("currpage", currpage + "");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ToastUtils.showMessage(getActivity(), "typeid为空!");
					} else if ("1".equals(code)) {
						try {
							lists.clear();
							List<NewsBean> newslist = JsonUtils.getNewsInfo(arg2);
							lists.addAll(newslist);
							if(adapter!=null){
								adapter=null;
							}
							adapter = new IndustryInfoAdapter(newslist,
									getActivity());
							listView.setAdapter(adapter);
							onLoadFinsh();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if ("2".equals(code)) {
						ToastUtils.showMessage(getActivity(), "页码输入错误!");
					} else if ("3".equals(code)) {
						ToastUtils.showMessage(getActivity(), "没有相关新闻!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(getActivity(), "刷新数据失败!");

			}
			@Override
			public void onFinish() {
				super.onFinish();
				onLoadFinsh();
			}


		});

	}

	public void doZhuanTiOnRefresh() {
		currpage = 1;
		RequestParams params = new RequestParams();
		params.put("currpage", currpage + "");
		HttpUtils.doPost(ZHUANTI_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ToastUtils.showMessage(getActivity(), "当前页码输入不正确!");
					} else if ("1".equals(code)) {
						try {
							lists.clear();
							List<NewsBean> zhuangtilist1 = JsonUtils.getZhuanTiInfo(arg2);
							lists.addAll(zhuangtilist1);
							if(adapter!=null){
								adapter=null;
							}
							adapter = new IndustryInfoAdapter(zhuangtilist1,
									getActivity());
							listView.setAdapter(adapter);
							onLoadFinsh();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else if ("2".equals(code)) {
						ToastUtils.showMessage(getActivity(), "没有专题数据!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(getActivity(), "刷新数据失败!");
			}
			@Override
			public void onFinish() {
				super.onFinish();
				onLoadFinsh();
			}

		});
	}

	public void doNewsOnLoadMore() {
		currpage++;
		RequestParams params = new RequestParams();
		params.put("typeid", idx + "");
		params.put("currpage", currpage + "");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ToastUtils.showMessage(getActivity(), "typeid为空!");
					} else if ("1".equals(code)) {
						try {
							List<NewsBean> list2 = JsonUtils.getNewsInfo(arg2);
							if (list2.size() < 10) {
								listView.setPullLoadEnable(false);
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
						ToastUtils.showMessage(getActivity(), "页码输入错误!");
					} else if ("3".equals(code)) {
						listView.setPullLoadEnable(false);
						ToastUtils.showMessage(getActivity(), "没有相关新闻!");
						
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(getActivity(), "刷新数据失败!");

			}
			
			@Override
			public void onFinish() {
				super.onFinish();
				onLoadFinsh();
			}

		});

	}

	public void doZhuanTiOnLoadMore() {
		currpage++;
		RequestParams params = new RequestParams();
		params.put("currpage", currpage + "");
		HttpUtils.doPost(ZHUANTI_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						ToastUtils.showMessage(getActivity(), "当前页码输入不正确!");
					} else if ("1".equals(code)) {
						try {
							List<NewsBean> list3 = JsonUtils
									.getZhuanTiInfo(arg2);
							if (list3.size() < 10) {
								listView.setPullLoadEnable(false);
							}
							
							lists.addAll(list3);
							adapter.addAll(list3);
							adapter.notifyDataSetChanged();
							onLoadFinsh();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} else if ("2".equals(code)) {
						listView.setPullLoadEnable(false);
						ToastUtils.showMessage(getActivity(), "没有专题数据!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(getActivity(), "加载数据失败!");
			}

			@Override
			public void onFinish() {
				super.onFinish();
				onLoadFinsh();
			}
		});
	}

	@Override
	public void onRefresh() {
		if (isNews)
			doNewsOnRefresh();
		else
			doZhuanTiOnRefresh();
	}

	@Override
	public void onLoadMore() {
		if (isNews)
			doNewsOnLoadMore();
		else
			doZhuanTiOnLoadMore();
	}
	
	public void onLoadFinsh(){
		listView.stopLoadMore();
		listView.stopRefresh();
	}

}
