package com.nongziwang.fragment;

import com.nongziwang.main.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * 
 * @author Administrator
 *
 */
public class UsesFragment extends BaseFragment{
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_use, container,false);
		initViews();
		addFragment("params");
		return view;
	}
	

	private void initViews() {
		// TODO Auto-generated method stub
		
	}
	public static Fragment getInstance(String params) {
		UsesFragment fragment = new UsesFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}
	public Fragment creatFragment(String params) {
		return UsesResultsFragment.getInstance(params);

	}

	public void addFragment(String params) {
		FragmentManager fm = getFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentUse);
		if (fragment != null) {
			fm.beginTransaction().remove(fragment).commit();
		}
		fragment = creatFragment(params);
		fm.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).add(R.id.fragmentUse, fragment).commit();

	}
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}


}
