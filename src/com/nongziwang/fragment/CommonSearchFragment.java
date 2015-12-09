package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.adapter.TypeSearchAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CommonSearchFragment extends BaseFragment {

	private View view;
	private TextView tv_type;
	private ImageView type_img;
	private ListView type_listview;
	private String params;
	private boolean isPrepared;
	private TypeSearchAdapter adapter;
	private List<String> list;
	public static final String HUAFEI = "化肥";
	public static final String NONGYAO = "农药";
	public static final String ZHONGZI = "种子";
	public static final String NONGJI = "农机";
	public static final String NONGMO = "农膜";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_type_search, container, false);
		isPrepared = true;
		initViews();
		lazyLoad();
		return view;
	}

	private void initViews() {
		tv_type = (TextView) view.findViewById(R.id.tv_type);
		type_img = (ImageView) view.findViewById(R.id.type_img);
		type_listview = (ListView) view.findViewById(R.id.type_listview);
		list = new ArrayList<String>();
		list.add("复合肥");
		list.add("农机");
		list.add("农膜");
		list.add("硝铵酸");

	}

	public static Fragment getInstance(String type) {
		CommonSearchFragment fragment = new CommonSearchFragment();
		Bundle bundle = new Bundle();
		bundle.putString("type", type);
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	protected void lazyLoad() {
		params = getArguments().getString("type");
		if (!TextUtils.isEmpty(params)) {
			tv_type.setText(params);
			if (params.equals(HUAFEI)) {
				type_img.setBackgroundResource(R.drawable.icon_huafei);
			} else if (params.equals(NONGYAO)) {
				type_img.setBackgroundResource(R.drawable.icon_nongyao);
			} else if (params.equals(ZHONGZI)) {
				type_img.setBackgroundResource(R.drawable.icon_zhongzi);
			} else if (params.equals(NONGJI)) {
				type_img.setBackgroundResource(R.drawable.icon_tuolaji);
			} else if (params.equals(NONGMO)) {
				type_img.setBackgroundResource(R.drawable.icon_nongmo);
			}

		}
		adapter=new TypeSearchAdapter(list, getActivity());
		type_listview.setAdapter(adapter);
	}

}
