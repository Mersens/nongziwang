package com.nongziwang.fragment;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import com.nongziwang.activity.IndustryInfoDetialActivity;
import com.nongziwang.adapter.IndustryInfoAdapter;
import com.nongziwang.main.R;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author Mersens 行业资讯
 */
public class IndustryInfoFragment extends BaseFragment implements
IXListViewListener, EventListener{
	private View view;
	private XListView listView;
	private boolean isPrepared;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_industry_info, container,false);
		isPrepared=true;
		initViews();
		lazyLoad();
		initEvent();
		return view;
	}

	private void initViews() {
		listView=(XListView) view.findViewById(R.id.listView);
		//不允许上拉加载
		listView.setPullLoadEnable(true);
		// 允许下拉
		listView.setPullRefreshEnable(true);
		listView.setXListViewListener(this);
	}

	private void initEvent() {

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intentAction(getActivity(), IndustryInfoDetialActivity.class);
			}
		});
		
	}
	public static Fragment getInstance(String params) {
		IndustryInfoFragment fragment = new IndustryInfoFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}
	@Override
	protected void lazyLoad() {
		List<String> list=new ArrayList<String>();
		for(int i=0;i<10;i++){
			list.add(i+"");
		}
		if(!isPrepared || !isVisible) {
            return;
        }
		listView.setAdapter(new IndustryInfoAdapter(list, getActivity()));
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}

}
