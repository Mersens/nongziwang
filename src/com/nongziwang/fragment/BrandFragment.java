package com.nongziwang.fragment;

import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * 
 * @author Administrator
 * Æ·ÅÆ
 */
public class BrandFragment extends BaseFragment implements OnClickListener{
	private View view;
	private TextView tv_tj,tv_all;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_brand, container,false);
		initViews();
		addFragment("params");
		return view;
	}

	private void initViews() {
		tv_tj=(TextView) view.findViewById(R.id.tv_tj);
		tv_all=(TextView) view.findViewById(R.id.tv_all);
		tv_tj.setOnClickListener(this);
		tv_all.setOnClickListener(this);
	}
	public static Fragment getInstance(String params) {
		BrandFragment fragment = new BrandFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}
	public Fragment creatFragment(String params) {
		return CommonSearchOtherResultsFragment.getInstance(params);

	}

	public void addFragment(String params) {
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentBrand);
		if (fragment != null) {
			fm.beginTransaction().remove(fragment).commit();
		}
		fragment = creatFragment(params);
		fm.beginTransaction().add(R.id.fragmentBrand, fragment).commit();

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_tj:
			setColor(0);
			addFragment("params");
			break;
		case R.id.tv_all:
			setColor(1);
			addFragment("params");
			break;

		}
		
	}

	public void setColor(int i){
		resetColor();
		switch (i) {
		case 0:
			tv_tj.setTextColor(getResources().getColor(R.color.title_yellow_text_color));
			break;
		case 1:
			tv_all.setTextColor(getResources().getColor(R.color.title_yellow_text_color));
			break;
		}
	}

	private void resetColor() {
		tv_tj.setTextColor(getResources().getColor(R.color.gray_text_color));
		tv_all.setTextColor(getResources().getColor(R.color.gray_text_color));		
	}

}
