package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.nongziwang.activity.MyAddressFragmentActivity;
import com.nongziwang.adapter.MyArrayAdapter;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.MyRegion;
import com.nongziwang.main.R;

public class MyAddressAddFragment extends BaseFragment {
	private View view;
	private RelativeLayout layout_ok;
	private MyArrayAdapter provinces_adapter,citys_adapter;
	private Spinner spinner_province,spinner_city;
	private List<MyRegion> provinces;
	private List<MyRegion> citys;
	private NongziDao dao;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_myaddress_add,container,false);
		dao=new NongziDaoImpl(getActivity());
		initViews();
		initEvent();
		return view;
	}

	
	private void initViews() {
		layout_ok=(RelativeLayout) view.findViewById(R.id.layout_ok);
		spinner_province=(Spinner) view.findViewById(R.id.spinner_province);
		spinner_city=(Spinner) view.findViewById(R.id.spinner_city);


	}


	private void initEvent() {
		provinces=dao.findAllProvinces();
		citys = new ArrayList<MyRegion>();
		citys.add(new MyRegion("1", "«Î—°‘Ò", ""));
		layout_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent(MyAddressFragmentActivity.ACTION_LIST);  
                getActivity().sendBroadcast(mIntent);  				
			}
		});
		provinces_adapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, provinces);
		citys_adapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, citys);
		citys_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		provinces_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_province.setAdapter(provinces_adapter);
		spinner_province.setSelection(0, true);
		spinner_city.setAdapter(citys_adapter);
		spinner_city.setSelection(0, true);
		
		
		spinner_province.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0){
					String parintid=provinces.get(position).getId();
					citys.clear();
					citys=dao.findAllCitysByProvincesId(parintid);
					citys_adapter.clear();
					citys_adapter.addAll(citys);
					citys_adapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}


	public static Fragment getInstance(String params) {
		MyAddressAddFragment fragment = new MyAddressAddFragment();
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
