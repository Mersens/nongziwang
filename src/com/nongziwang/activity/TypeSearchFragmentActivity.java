package com.nongziwang.activity;

import com.nongziwang.fragment.CommonSearchFragment;

import android.support.v4.app.Fragment;

public class TypeSearchFragmentActivity extends BaseFragmentActivity{
	@Override
	protected Fragment creatFragment() {
		String type = getIntent().getStringExtra("params");
		return CommonSearchFragment.getInstance(type);
	}
}
