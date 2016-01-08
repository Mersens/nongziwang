package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import com.nongziwang.adapter.MyArrayAdapter;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.MyRegion;
import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class SellerAccountVipFragment extends BaseFragment {
	private View view;
	private Spinner spinner_province,spinner_city,spinner_area;
	private List<MyRegion> provinces;
	private List<MyRegion> citys;
	private List<MyRegion> areas;
	private MyArrayAdapter provincesAdapter,citysAdapter,areasAdapter;
	private NongziDao dao;
	private Button btn_submit;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_seller_account_vip, container,false);
		dao=new NongziDaoImpl(getActivity());
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		spinner_province=(Spinner) view.findViewById(R.id.spinner_province);
		spinner_city=(Spinner) view.findViewById(R.id.spinner_city);
		spinner_area=(Spinner) view.findViewById(R.id.spinner_area);
		btn_submit=(Button) view.findViewById(R.id.btn_submit);
		
	}


	private void initEvent() {
		provinces=dao.findAllProvinces();
		citys=new ArrayList<MyRegion>();
		citys.add(new MyRegion("1","«Î—°‘Ò",null));
		areas=new ArrayList<MyRegion>();
		areas.add(new MyRegion("1","«Î—°‘Ò",null));
		
		provincesAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, provinces);
		provincesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		citysAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, citys);
		citysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		areasAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, areas);
		areasAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_province.setAdapter(provincesAdapter);
		spinner_province.setSelection(0,true);
		
		spinner_city.setAdapter(citysAdapter);
		spinner_city.setSelection(0, true);
		
		spinner_area.setAdapter(areasAdapter);
		spinner_area.setSelection(0,true);
		
		spinner_province.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0){
					String parintid=provinces.get(position).getId();
					citys.clear();
					citys=dao.findAllCitysByProvincesId(parintid);
					citysAdapter.clear();
					citysAdapter.addAll(citys);
					citysAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		spinner_city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if(position!=0){
					String parintid=citys.get(position).getId();
					areas.clear();
					areas=dao.findAllAreasByCityId(parintid);
					areasAdapter.clear();
					areasAdapter.addAll(areas);
					areasAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
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
