package com.nongziwang.fragment;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.GongsiDetailFragmentActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.GongsiDetailBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.ImageLoadOptions;
import com.nongziwang.utils.JsonUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GongsiBaseDetailFragment extends BaseFragment{
	private View view;
	private String id;
	private TextView tv_gsmc,tv_jyms,tv_szdq,tv_zycp,tv_lxr,tv_lxdh,tv_lxdz;
	private ImageView img_mycd;
	private RelativeLayout layout_loading;
	private static final String URL=AppConstants.SERVICE_ADDRESS+"/gongsisousuo/getGongsiXinxi";
	private GongsiDetailBean bean;
	private static final String TAG="GongsiBaseDetailFragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_gongsibasedetail,null);
		id=getArguments().getString("params");
		initViews();
		initDatas();
		return view;
	}
	
	private void initViews() {
		tv_gsmc=(TextView) view.findViewById(R.id.tv_gsmc);
		tv_jyms=(TextView) view.findViewById(R.id.tv_jyms);
		tv_szdq=(TextView) view.findViewById(R.id.tv_szdq);
		tv_zycp=(TextView) view.findViewById(R.id.tv_zycp);
		tv_lxr=(TextView) view.findViewById(R.id.tv_lxr);
		tv_lxdh=(TextView) view.findViewById(R.id.tv_lxdh);
		tv_lxdz=(TextView) view.findViewById(R.id.tv_lxdz);
		layout_loading=(RelativeLayout) view.findViewById(R.id.layout_loading);
		img_mycd=(ImageView) view.findViewById(R.id.img_mycd);
	}

	private void initDatas() {
		RequestParams params=new RequestParams();
		params.put("dianpuid", id);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code=JsonUtils.getCode(arg2);
				if("0".equals(code)){
					Toast.makeText(getActivity(), "dianpuid为空!", Toast.LENGTH_SHORT).show();
				}else if("1".equals(code)){
					try {
						bean=JsonUtils.getGongsiDetail(arg2);
						setDatas();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}else if("2".equals(code)){
					Toast.makeText(getActivity(), "没有查询到公司信息!", Toast.LENGTH_SHORT).show();
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Log.e(TAG, arg2==null?"":arg2);
				Toast.makeText(getActivity(), "数据加载失败!", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onFinish() {
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
	}
	
	public void setDatas(){
		ImageLoader.getInstance().displayImage(bean.getManyidu(), img_mycd,ImageLoadOptions.getOptions());
		tv_gsmc.setText(bean.getGongsiname());
		tv_jyms.setText(bean.getDianputype());
		tv_szdq.setText(bean.getDiqu());
		tv_zycp.setText(bean.getZhuyingchanpin());
		tv_lxr.setText(bean.getLianxiren());
		tv_lxdh.setText(bean.getLianxirendianhua());
		tv_lxdz.setText(bean.getLianxidizhi());
		GongsiDetailFragmentActivity.tel_num=bean.getLianxirendianhua();
	}
	
	public static Fragment getInstance(String params) {
		GongsiBaseDetailFragment fragment = new GongsiBaseDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}
	@Override
	protected void lazyLoad() {
		
	}


}
