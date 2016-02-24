package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.activity.MyAddressFragmentActivity;
import com.nongziwang.adapter.MyArrayAdapter;
import com.nongziwang.main.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;

public class MyAddressEditorFragment extends BaseFragment {
	private View view;
	private RelativeLayout layout_ok;
	private MyArrayAdapter adapter;
	private Spinner spinner_province,spinner_city;
	private List<String>list;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_myaddress_editor,container,false);
		initViews();
		initEvent();
		return view;
	}

	
	private void initViews() {
		layout_ok=(RelativeLayout) view.findViewById(R.id.layout_ok);
		spinner_province=(Spinner) view.findViewById(R.id.spinner_province);
		spinner_city=(Spinner) view.findViewById(R.id.spinner_city);
		list = new ArrayList<String>();
		list.add("请选择");
		list.add("北京");
		list.add("上海");
		list.add("深圳");
		list.add("广州");
		list.add("南京");
		list.add("郑州");
	}


	private void initEvent() {
		layout_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(MyAddressFragmentActivity.ACTION_LIST);  
                getActivity().sendBroadcast(mIntent);  				
			}
		});
/*		adapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_province.setAdapter(adapter);
		spinner_province.setSelection(0, true);
		spinner_city.setAdapter(adapter);
		spinner_city.setSelection(0, true);*/
	}


	public static Fragment getInstance(String params) {
		MyAddressEditorFragment fragment = new MyAddressEditorFragment();
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
