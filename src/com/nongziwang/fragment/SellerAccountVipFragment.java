package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;
import com.nongziwang.adapter.MyArrayAdapter;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

public class SellerAccountVipFragment extends BaseFragment {
	private View view;
	private Spinner spinner_province,spinner_city,spinner_area;
	private List<String> list;
	private MyArrayAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_seller_account_vip, container,false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		spinner_province=(Spinner) view.findViewById(R.id.spinner_province);
		spinner_city=(Spinner) view.findViewById(R.id.spinner_city);
		spinner_area=(Spinner) view.findViewById(R.id.spinner_area);
		
	}


	private void initEvent() {
		list = new ArrayList<String>();
		list.add("请选择");
		list.add("北京");
		list.add("上海");
		list.add("深圳");
		list.add("广州");
		list.add("南京");
		list.add("郑州");
		adapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_province.setAdapter(adapter);
		spinner_province.setSelection(0, true);
		spinner_city.setAdapter(adapter);
		spinner_city.setSelection(0, true);
		spinner_area.setAdapter(adapter);
		spinner_area.setSelection(0, true);
		
	}


	public static Fragment getInstance(String params) {
		SellerAccountVipFragment fragment = new SellerAccountVipFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
}
