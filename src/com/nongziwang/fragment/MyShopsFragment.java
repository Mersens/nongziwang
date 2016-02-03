package com.nongziwang.fragment;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.MyShopsFragmentActivity;
import com.nongziwang.adapter.MyShopsAdapter;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.entity.DianPuBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.ImageLoadOptions;
import com.nongziwang.utils.JsonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MyShopsFragment extends BaseFragment {
	private View view;
	private GridView gridView;
	private MyShopsAdapter adapter;
	private String url;
	private String dianpuid;
	private DianPuBean dianpubean;
	private List<ChanPinBean> list;
	private static final String TAG = "MyShopsFragment";



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if(view!=null){
			return view;
		}
		view = inflater.inflate(R.layout.layout_myproduct, container, false);
		url = getArguments().getString("params");
		dianpuid=getArguments().getString("id");
		initViews();
		initEvent();
		initDatas();
		return view;
	}

	private void initViews() {
		gridView = (GridView) view.findViewById(R.id.gridView);
		gridView.setTextFilterEnabled(true);
	}

	private void initEvent() {
		
		gridView.setOnScrollListener(new OnScrollListener() {
			private int pos;
			private boolean isSend = false;
			private boolean isShow = true;

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (view.getFirstVisiblePosition() == 0 && pos == 0) {
					if (isShow) {
						Intent mIntent = new Intent(
								MyShopsFragmentActivity.ACTION_SHOW);
						getActivity().sendBroadcast(mIntent);
						isShow = false;
						isSend = false;
					}

				} else {
					if (!isSend) {
						Intent mIntent = new Intent(
								MyShopsFragmentActivity.ACTION_HIDE);
						getActivity().sendBroadcast(mIntent);
						isSend = true;
						isShow = true;
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				pos = firstVisibleItem;
			}
		});
	}
	

	
	
	public void initDatas() {
		RequestParams params = new RequestParams();
		params.put("dianpuid", dianpuid);
		HttpUtils.doPost(url, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(getActivity(), " 店铺id为空!",
							Toast.LENGTH_SHORT).show();
				} else if ("1".equals(code)) {
					try {
						dianpubean = JsonUtils.getDianPuInfo(arg2);
						list = dianpubean.getChanpinlist();
						setDatas();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if ("2".equals(code)) {
					Toast.makeText(getActivity(), "该店铺不存在!", Toast.LENGTH_SHORT)
							.show();
				} else if ("2".equals(code)) {
					Toast.makeText(getActivity(), "该店铺下没有产品!",
							Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(getActivity(), "数据获取失败!", Toast.LENGTH_SHORT)
						.show();
				Log.e(TAG, arg2 == null ? "" : arg2);

			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				MyShopsFragmentActivity.layout_loading.setVisibility(View.GONE);
			}
		});
	}

	public void setDatas() {
		String dianpulogo = dianpubean.getDianpulogo();
		String dianpuname = dianpubean.getDianpuname();
		if (!TextUtils.isEmpty(dianpulogo)) {
			ImageLoader.getInstance().displayImage(dianpulogo,
					MyShopsFragmentActivity.image_dp_logo,
					ImageLoadOptions.getOptionsLoading());
		}
		if (!TextUtils.isEmpty(dianpuname)) {
			MyShopsFragmentActivity.tv_dp_name.setText(dianpuname);
		}
		adapter = new MyShopsAdapter(list, getActivity());
		gridView.setAdapter(adapter);

	}

	public static Fragment getInstance(String params,String id) {
		MyShopsFragment fragment = new MyShopsFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		bundle.putString("id", id);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {

	}

}
