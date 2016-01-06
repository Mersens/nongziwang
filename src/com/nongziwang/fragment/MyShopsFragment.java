package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;

import com.nongziwang.activity.MyShopsFragmentActivity;
import com.nongziwang.adapter.MyShopsAdapter;
import com.nongziwang.main.R;

public class MyShopsFragment extends BaseFragment {
	private View view;
	private List<String> list;
	private GridView gridView;
	private MyShopsAdapter adapter;

	public static Fragment getInstance(String params) {
		MyShopsFragment fragment = new MyShopsFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_myproduct, container, false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		gridView = (GridView) view.findViewById(R.id.gridView);
	}

	private void initEvent() {
		list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add(i + "");
		}
		adapter = new MyShopsAdapter(list, getActivity());
		gridView.setAdapter(adapter);

		gridView.setOnScrollListener(new OnScrollListener() {
			private int pos;
			private boolean isSend = false;
			private boolean isShow = true;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (view.getFirstVisiblePosition() == 0 && pos == 0 ) {
					if (isShow) {
						Intent mIntent = new Intent(
								MyShopsFragmentActivity.ACTION_SHOW);
						getActivity().sendBroadcast(mIntent);
						isShow = false;
						isSend = false;
					}

				} else {
					if (!isSend ) {
						Intent mIntent = new Intent(
								MyShopsFragmentActivity.ACTION_HIDE);
						getActivity().sendBroadcast(mIntent);
						isSend = true;
						isShow = true;
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				    pos = firstVisibleItem;
			}
		});
	}

	@Override
	protected void lazyLoad() {

	}

}
