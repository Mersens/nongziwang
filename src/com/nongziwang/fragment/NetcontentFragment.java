package com.nongziwang.fragment;

import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 
 * @author Administrator
 * ¾»º¬Á¿
 */
public class NetcontentFragment extends BaseFragment {
	private View view;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_netcontent, container,false);
		addFragment("params");
		return view;
	}
	public static Fragment getInstance(String params) {
		NetcontentFragment fragment = new NetcontentFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}
	public Fragment creatFragment(String params) {
		return CommonSearchOtherResultsFragment.getInstance(params);
	}

	public void addFragment(String params) {
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentNetcontent);
		if (fragment != null) {
			fm.beginTransaction().remove(fragment).commit();
		}
		fragment = creatFragment(params);
		fm.beginTransaction().add(R.id.fragmentNetcontent, fragment).commit();
	}
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
	}

}
