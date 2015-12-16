package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductFragment extends BaseFragment{
	private View view;
	private ExpandableListView listView;
	private List<String> grouplist;
	private Map<String, List<String>> map;
	private LayoutInflater mInflater;
	public static final String HUAFEI = "����";
	public static final String NONGYAO = "ũҩ";
	public static final String ZHONGZI = "����";
	public static final String NONGJI = "ũ��";
	public static final String NONGMO = "ũĤ";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.layout_find_product, container,false);
		mInflater=LayoutInflater.from(getActivity());
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		setLeftWithTitleViewMethod(view, R.drawable.ic_menu_back,"�Ҳ�Ʒ", new OnLeftClickListener() {
			
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		setHeadViewBg(R.color.actionbar_blue_color);
		setHeadViewTitleColor(getResources().getColor(R.color.white_color));
		listView=(ExpandableListView) view.findViewById(R.id.listView);
		listView.setGroupIndicator(null);
	}


	private void initEvent() {
		grouplist=new ArrayList<String>();
		grouplist.add("����");
		grouplist.add("ũҩ");
		grouplist.add("����");
		grouplist.add("ũ��");
		grouplist.add("ũĤ");
		map=new HashMap<String, List<String>>();
		
		List<String> list1=new ArrayList<String>();
		list1.add("����");
		list1.add("ũҩ");
		list1.add("����");
		list1.add("ũ��");
		list1.add("ũĤ");
		map.put(grouplist.get(0), list1);
		List<String> list2=new ArrayList<String>();
		list2.add("����");
		list2.add("ũҩ");
		list2.add("����");
		list2.add("ũ��");
		list2.add("ũĤ");
		map.put(grouplist.get(1), list2);
		List<String> list3=new ArrayList<String>();
		list3.add("����");
		list3.add("ũҩ");
		list3.add("����");
		list3.add("ũ��");
		list3.add("ũĤ");
		map.put(grouplist.get(2), list3);
		List<String> list4=new ArrayList<String>();
		list4.add("����");
		list4.add("ũҩ");
		list4.add("����");
		list4.add("ũ��");
		list4.add("ũĤ");
		map.put(grouplist.get(3), list4);
		List<String> list5=new ArrayList<String>();
		list5.add("����");
		list5.add("ũҩ");
		list5.add("����");
		list5.add("ũ��");
		list5.add("ũĤ");
		map.put(grouplist.get(4), list4);
		listView.setAdapter(adapter);
		
	}

	
	ExpandableListAdapter adapter = new BaseExpandableListAdapter(){

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return grouplist.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			String key=grouplist.get(groupPosition);
			return map.get(key).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return grouplist.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String key=grouplist.get(groupPosition);
			
			return (map.get(key).get(childPosition));
		}

		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=mInflater.inflate(R.layout.find_product_group_item, parent,false);
			}
			String type=grouplist.get(groupPosition);
			TextView tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			tv_name.setText(type);
			ImageView image_indicator=(ImageView) convertView.findViewById(R.id.image_indicator);
			if (type.equals(HUAFEI)) {
				image_indicator.setBackgroundResource(R.drawable.icon_huafei);
			} else if (type.equals(NONGYAO)) {
				image_indicator.setBackgroundResource(R.drawable.icon_nongyao);
			} else if (type.equals(ZHONGZI)) {
				image_indicator.setBackgroundResource(R.drawable.icon_zhongzi);
			} else if (type.equals(NONGJI)) {
				image_indicator.setBackgroundResource(R.drawable.icon_tuolaji);
			} else if (type.equals(NONGMO)) {
				image_indicator.setBackgroundResource(R.drawable.icon_nongmo);
			}
			
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if(convertView==null){
				convertView=mInflater.inflate(R.layout.layout_search_history_item, parent,false);
			}
			TextView search_history_name=(TextView) convertView.findViewById(R.id.search_history_name);
			String key=grouplist.get(groupPosition);
			search_history_name.setText(map.get(key).get(childPosition));
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {

			return true;
		}
		
	};
	
	@Override
	protected void lazyLoad() {

		
	}
}
