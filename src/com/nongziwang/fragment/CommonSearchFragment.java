package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.SearchResultsFragmentActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.LeiMuBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.SharePreferenceUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupExpandListener;
/**
 * 
 * @title CommonSearchFragment
 * @description:Type类型数据
 * @author Mersens
 * @time 2016年2月19日
 */
public class CommonSearchFragment extends BaseFragment {
	private View view;
	private TextView tv_type;
	private ImageView type_img;
	private ExpandableListView type_listview;
	private String params;
	public static final String HUAFEI = "化肥";
	public static final String NONGYAO = "农药";
	public static final String ZHONGZI = "种子";
	public static final String NONGJI = "农机";
	public static final String NONGMO = "农膜";
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "leimu/getLeimuByParentId";
	private static final String TAG = "CommonSearchFragment";
	private List<LeiMuBean> grouplist;
	private List<LeiMuBean> childlist=new ArrayList<LeiMuBean>();
	private Map<String, List<LeiMuBean>> map;
	private LayoutInflater mInflater;
	private ExpandableListAdapter adapter;
	private NongziDao dao;
	 private String userid = null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_type_search, container, false);
		mInflater = LayoutInflater.from(getActivity());
		dao = new NongziDaoImpl(getActivity().getApplicationContext());
		userid = SharePreferenceUtil.getInstance(getActivity().getApplicationContext())
				.getUserId();
		initViews();
		initDatas();
		return view;
	}

	private void initViews() {
		tv_type = (TextView) view.findViewById(R.id.tv_type);
		type_img = (ImageView) view.findViewById(R.id.type_img);
		type_listview = (ExpandableListView) view.findViewById(R.id.type_listview);
		type_listview.setGroupIndicator(null);
		type_listview.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				
				for (int i = 0, count = type_listview.getExpandableListAdapter()
						.getGroupCount(); i < count; i++) {
					if (groupPosition != i) {
						type_listview.collapseGroup(i);
					}
				}
			}
		});
		type_listview.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				if(childlist!=null){
					childlist.clear();
				}
				isSuccess=false;
				doSearchChild(groupPosition);
				return false;
			}
		});
		
		type_listview.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				if(isSuccess && childlist!=null && childlist.size()>0){
					String key = grouplist.get(groupPosition).getName();
					String name=map.get(key).get(childPosition).getName();
					intentAction(name,map.get(key).get(childPosition).getLeimuid());
				}
				return false;
			}
		});
	}
	boolean isSuccess=false;
	public void doSearchChild(final int groupPosition){
		
		RequestParams params = new RequestParams();
		params.put("parentid",grouplist.get(groupPosition).getLeimuid());
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code=JsonUtils.getCode(arg2);
				if(!TextUtils.isEmpty(code)){
					if("0".equals(code)){
						isSuccess=false;
					}else if("1".equals(code)){
						try {
							isSuccess=true;
							childlist=JsonUtils.getLeiMuByInfo(arg2);
							map.put(grouplist.get(groupPosition).getName(), childlist);
							adapter.notifyDataSetChanged();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				
			}
			@Override
			public void onFinish() {
				super.onFinish();
				if(!isSuccess){
					intentAction(grouplist.get(groupPosition).getName(),grouplist.get(groupPosition).getLeimuid());
				}
			}
		});
		//首先添加伪数据，避免出错，异步加载完成后刷新
		map.put(grouplist.get(groupPosition).getName(), childlist);
	}
	
	
	public void intentAction(String params,String leimuid){
		if (!dao.findHistoryIsExist(params)) {
			if (TextUtils.isEmpty(userid)) {
				dao.addSearchHistory("1", params);
			} else {
				dao.addSearchHistory(userid, params);
			}
		}
		Intent intent = new Intent(getActivity(),
				SearchResultsFragmentActivity.class);
		intent.putExtra("params",params);
		intent.putExtra("leimuid",leimuid);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.bottom_open, 0);

	}
	
	public static Fragment getInstance(String type) {
		CommonSearchFragment fragment = new CommonSearchFragment();
		Bundle bundle = new Bundle();
		bundle.putString("type", type);
		fragment.setArguments(bundle);
		return fragment;
	}

	public void initDatas() {
		String id = null;
		params = getArguments().getString("type");
		if (!TextUtils.isEmpty(params)) {
			tv_type.setText(params);
			if (params.equals(HUAFEI)) {
				id = "1";
				type_img.setBackgroundResource(R.drawable.icon_huafei);
			} else if (params.equals(NONGYAO)) {
				id = "2";
				type_img.setBackgroundResource(R.drawable.icon_nongyao);
			} else if (params.equals(ZHONGZI)) {
				id = "3";
				type_img.setBackgroundResource(R.drawable.icon_zhongzi);
			} else if (params.equals(NONGJI)) {
				id = "4";
				type_img.setBackgroundResource(R.drawable.icon_tuolaji);
			} else if (params.equals(NONGMO)) {
				id = "5";
				type_img.setBackgroundResource(R.drawable.icon_nongmo);
			}
		}
		RequestParams params = new RequestParams();
		params.put("parentid", id);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code=JsonUtils.getCode(arg2);
				if(!TextUtils.isEmpty(code)){
					if("0".equals(code)){
						Toast.makeText(getActivity(), "没有对应的类目信息!", Toast.LENGTH_SHORT).show();
					}else if("1".equals(code)){
						try {
							grouplist=JsonUtils.getLeiMuByInfo(arg2);
							map = new HashMap<String, List<LeiMuBean>>();
							adapter=new ExpandableListAdapter();
							type_listview.setAdapter(adapter);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				    Log.e(TAG, arg2 == null ? "" : arg2);

			}
		});
	}

	class ExpandableListAdapter extends BaseExpandableListAdapter{
		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return grouplist.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			String key = grouplist.get(groupPosition).getName();
			return map.get(key).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			// TODO Auto-generated method stub
			return grouplist.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String key = grouplist.get(groupPosition).getName();

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
						R.layout.layout_search_history_item, parent, false);
				holder.group_name = (TextView) convertView
						.findViewById(R.id.search_history_name);
				holder.image_indicator=(ImageView) convertView.findViewById(R.id.image_indicator);
				convertView.setTag(holder);
			} else {
				holder = (GroupHolder) convertView.getTag();
			}
			String key = grouplist.get(groupPosition).getName();
				if (isExpanded) {
					holder.image_indicator.setImageResource(R.drawable.group_up);
				} else {
					holder.image_indicator.setImageResource(R.drawable.group_down);
				}
				holder.image_indicator.setVisibility(View.VISIBLE);
			holder.group_name.setText(key);
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
			String key = grouplist.get(groupPosition).getName();
			holder.child_name.setText(map.get(key).get(childPosition).getName());
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

	class GroupHolder {
		TextView group_name;
		ImageView image_indicator;
	}

	class ChildHolder {
		TextView child_name;
	}

	@Override
	protected void lazyLoad() {

	}

}
