package com.nongziwang.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GongsiRZDetailFragment extends BaseFragment {
	private View view;
	private String id;
	private TextView tv_rz_msg,tv_gs_name;
	private static final String TAG="GongsiBaseDetailFragment";
	private static final String URL=AppConstants.SERVICE_ADDRESS
			+ "/gongsisousuo/getGongsiRenzhengXinxi";
	private RelativeLayout layout_loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_gongsirzdetail, null);
		id = getArguments().getString("params");
		initViews();
		initDatas();
		return view;
	}

	private void initViews() {
		tv_rz_msg=(TextView) view.findViewById(R.id.tv_rz_msg);
		tv_gs_name=(TextView) view.findViewById(R.id.tv_gs_name);
		layout_loading=(RelativeLayout) view.findViewById(R.id.layout_loading);

	}

	public void initDatas() {
		RequestParams params = new RequestParams();
		params.put("gongsiid", id);
		HttpUtils.doPost(URL, params,
				new TextHttpResponseHandler() {
					@Override
					public void onSuccess(int arg0, Header[] arg1, String arg2) {
						String code=JsonUtils.getCode(arg2);
						if("0".equals(code)){
							Toast.makeText(getActivity(), "gongsiid为空!", Toast.LENGTH_SHORT).show();
						}else if("1".equals(code)){
							try {
								JSONObject jsonObject = new JSONObject(arg2);
								tv_gs_name.setText(jsonObject.getString("gongsiname"));
								tv_rz_msg.setText("公司信息已认证");
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}else if("2".equals(code)){
							tv_rz_msg.setText("公司信息未认证");
							Toast.makeText(getActivity(), "公司信息未认证!", Toast.LENGTH_SHORT).show();
						}

					}

					@Override
					public void onFailure(int arg0, Header[] arg1, String arg2,
							Throwable arg3) {
						Log.e(TAG, arg2==null?"":arg2);
						Toast.makeText(getActivity(), "数据加载失败!", Toast.LENGTH_SHORT).show();

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
		GongsiRZDetailFragment fragment = new GongsiRZDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {

	}


}
