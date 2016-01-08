package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nongziwang.main.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductFragment extends BaseFragment {
	private View view;
	private ExpandableListView listView;
	private List<String> grouplist;
	private Map<String, List<String>> map;
	private LayoutInflater mInflater;
	public static final String HUAFEI = "化肥";
	public static final String NONGYAO = "农药";
	public static final String ZHONGZI = "种子";
	public static final String NONGJI = "农机";
	public static final String NONGMO = "农膜";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_find_product, container, false);
		mInflater = LayoutInflater.from(getActivity());
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {

		setOnlyTileViewMethod(view, "找产品");
		setHeadViewBg(R.color.actionbar_blue_color);
		setHeadViewTitleColor(getResources().getColor(R.color.white_color));
		listView = (ExpandableListView) view.findViewById(R.id.listView);
		listView.setGroupIndicator(null);
		listView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				for (int i = 0, count = listView.getExpandableListAdapter()
						.getGroupCount(); i < count; i++) {
					if (groupPosition != i) {
						// 关闭其他分组
						listView.collapseGroup(i);
					}
				}
			}
		});
	}

	private void initEvent() {
		grouplist = new ArrayList<String>();
		grouplist.add("种子");
		grouplist.add("农药");
		grouplist.add("化肥");
		grouplist.add("农机");
		grouplist.add("农膜");
		map = new HashMap<String, List<String>>();

		List<String> list1 = new ArrayList<String>();
		list1.add("种子");
		list1.add("农药");
		list1.add("化肥");
		list1.add("农机");
		list1.add("农膜");
		map.put(grouplist.get(0), list1);
		List<String> list2 = new ArrayList<String>();
		list2.add("种子");
		list2.add("农药");
		list2.add("化肥");
		list2.add("农机");
		list2.add("农膜");
		map.put(grouplist.get(1), list2);
		List<String> list3 = new ArrayList<String>();
		list3.add("种子");
		list3.add("农药");
		list3.add("化肥");
		list3.add("农机");
		list3.add("农膜");
		map.put(grouplist.get(2), list3);
		List<String> list4 = new ArrayList<String>();
		list4.add("种子");
		list4.add("农药");
		list4.add("化肥");
		list4.add("农机");
		list4.add("农膜");
		map.put(grouplist.get(3), list4);
		List<String> list5 = new ArrayList<String>();
		list5.add("种子");
		list5.add("农药");
		list5.add("化肥");
		list5.add("农机");
		list5.add("农膜");
		map.put(grouplist.get(4), list4);
		listView.setAdapter(adapter);

	}

	final ExpandableListAdapter adapter = new BaseExpandableListAdapter() {

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return grouplist.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			String key = grouplist.get(groupPosition);
			return map.get(key).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return grouplist.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String key = grouplist.get(groupPosition);

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
			GroupHolder holder = null;
			if (convertView == null) {
				holder = new GroupHolder();
				convertView = mInflater.inflate(
						R.layout.find_product_group_item, parent, false);
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.tv_name);
				holder.image_indicator = (ImageView) convertView
						.findViewById(R.id.image_indicator);
				holder.image_type = (ImageView) convertView
						.findViewById(R.id.image_type);
				convertView.setTag(holder);
			} else {
				holder = (GroupHolder) convertView.getTag();
			}
			String type = grouplist.get(groupPosition);
			holder.tv_name.setText(type);
			if (type.equals(HUAFEI)) {
				holder.image_type.setImageResource(R.drawable.icon_huafei);
			} else if (type.equals(NONGYAO)) {
				holder.image_type.setImageResource(R.drawable.icon_nongyao);
			} else if (type.equals(ZHONGZI)) {
				holder.image_type.setImageResource(R.drawable.icon_zhongzi);
			} else if (type.equals(NONGJI)) {
				holder.image_type.setImageResource(R.drawable.icon_tuolaji);
			} else if (type.equals(NONGMO)) {
				holder.image_type.setImageResource(R.drawable.icon_nongmo);
			}
			if (isExpanded) {
				holder.image_indicator.setImageResource(R.drawable.group_up);
			} else {
				holder.image_indicator.setImageResource(R.drawable.group_down);
			}
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			ChildHolder holder = null;

			if (convertView == null) {
				holder = new ChildHolder();
				convertView = mInflater.inflate(
						R.layout.layout_search_history_item, parent, false);
				holder.child_name = (TextView) convertView
						.findViewById(R.id.search_history_name);
				convertView.setTag(holder);
			} else {
				holder = (ChildHolder) convertView.getTag();
			}
			String key = grouplist.get(groupPosition);
			holder.child_name.setText(map.get(key).get(childPosition));
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {

			return true;
		}

	};

	class GroupHolder {
		private ImageView image_type;
		private TextView tv_name;
		private ImageView image_indicator;
	}

	class ChildHolder {
		TextView child_name;
	}


	@Override
	protected void lazyLoad() {

	}
}
