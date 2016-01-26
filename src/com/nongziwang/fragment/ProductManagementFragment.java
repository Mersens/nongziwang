package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.MyProductAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.SharePreferenceUtil;

public class ProductManagementFragment extends BaseFragment {
	private View view;
	private ListView listView;
	private String param;
	private int xinxiststic=0;
	private String userid;
	private static final String URL=AppConstants.SERVICE_ADDRESS+"chanpin/getChanpinByStatic"; 
	private static final String TAG="ProductManagementFragment";
	private List<ChanPinBean> list;
	private MyProductAdapter adapter;
	private RelativeLayout layout_loading;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.listview_pro_manage,container,false);
		param=getArguments().getString("params");
		userid=SharePreferenceUtil.getInstance(getActivity().getApplicationContext()).getUserId();
		initViews();
		initEvent();
		initDatas();
		return view;
	}



	private void initViews() {
		layout_loading=(RelativeLayout) view.findViewById(R.id.layout_loading);
		listView=(ListView) view.findViewById(R.id.listView);
	}

	private void initEvent() {


	}
	private void initDatas() {
		switch (Integer.parseInt(param)) {
		case 0:
			xinxiststic=1;
			break;
		case 1:
			xinxiststic=0;
			break;
		case 2:
			xinxiststic=2;
			break;
		case 3:
			xinxiststic=3;
			break;
		}
		RequestParams params=new RequestParams();
		params.put("userid",userid);
		params.put("xinxiststic",xinxiststic);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code=JsonUtils.getCode(arg2);
				if("0".equals(code)){
					Toast.makeText(getActivity(), "该用户没有店铺!", Toast.LENGTH_SHORT).show();
				}else if("1".equals(code)){
					try {
						list=JsonUtils.getChanPinInfo(arg2);
						adapter=new MyProductAdapter(list, getActivity());
						listView.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if("2".equals(code)){
					Toast.makeText(getActivity(), "没有产品信息!", Toast.LENGTH_SHORT).show();
				}
				
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Log.e(TAG, arg2==null?"":arg2);
				Toast.makeText(getActivity(), "获取失败!", Toast.LENGTH_SHORT).show();
				
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
		
		
	}
	@Override
	protected void lazyLoad() {

	}
	public static Fragment getInstance(String params) {
		ProductManagementFragment fragment = new ProductManagementFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}
}
