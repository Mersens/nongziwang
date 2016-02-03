package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.SearchFragmentActivity;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductFragment extends BaseFragment {
	private View view;
	private ExpandableListView listView;
	private List<LeiMuBean> grouplist;
	private Map<String, List<LeiMuBean>> map;
	private LayoutInflater mInflater;
	public static final String HUAFEI = "化肥";
	public static final String NONGYAO = "农药";
	public static final String ZHONGZI = "种子";
	public static final String NONGJI = "农机";
	public static final String NONGMO = "农膜";
	private ExpandableListAdapter adapter;
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "leimu/getLeimuByParentId";
	private static final String TAG = "ProductFragment";
	private List<LeiMuBean> feiliaolist = new ArrayList<LeiMuBean>();
	private List<LeiMuBean> nongyaolist = new ArrayList<LeiMuBean>();
	private List<LeiMuBean> zhongzilist = new ArrayList<LeiMuBean>();
	private List<LeiMuBean> nongjilist = new ArrayList<LeiMuBean>();
	private List<LeiMuBean> nongmolist = new ArrayList<LeiMuBean>();
	private NongziDao dao;
	 private String userid = null;
	 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.layout_find_product, container, false);
		mInflater = LayoutInflater.from(getActivity());
		dao = new NongziDaoImpl(getActivity().getApplicationContext());
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
		listView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				userid = SharePreferenceUtil.getInstance(getActivity().getApplicationContext())
						.getUserId();
				String key = grouplist.get(groupPosition).getName();
				String name=map.get(key).get(childPosition).getName();
				if (!dao.findHistoryIsExist(name)) {
					if (TextUtils.isEmpty(userid)) {
						dao.addSearchHistory("1", name);
					} else {
						dao.addSearchHistory(userid, name);
					}
				}

				Intent intent = new Intent(getActivity(),
						SearchResultsFragmentActivity.class);
				intent.putExtra("params",name);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.bottom_open, 0);

				return false;
			}
		});
	}

	private void initEvent() {
		grouplist = new ArrayList<LeiMuBean>();
		grouplist.add(new LeiMuBean("1", "肥料", "0"));
		grouplist.add(new LeiMuBean("2", "农药", "0"));
		grouplist.add(new LeiMuBean("3", "种子", "0"));
		grouplist.add(new LeiMuBean("4", "农机", "0"));
		grouplist.add(new LeiMuBean("5", "农膜", "0"));
		map = new HashMap<String, List<LeiMuBean>>();
		adapter = new ExpandableListAdapter();
		listView.setAdapter(adapter);

		doSearchFeiliao("1");
		doSearchNongyao("2");
		doSearchZhongzi("3");
		doSearchNongji("4");
		doSearchNongmo("5");
	}

	public void doSearchFeiliao(final String id) {
		RequestParams params = new RequestParams();
		params.put("parentid", id);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						Toast.makeText(getActivity(), "没有对应的类目信息!",
								Toast.LENGTH_SHORT).show();
					} else if ("1".equals(code)) {
						try {
							feiliaolist = JsonUtils.getLeiMuByInfo(arg2);
							map.put(grouplist.get(Integer.parseInt(id) - 1)
									.getName(), feiliaolist);
							adapter.notifyDataSetChanged();
						} catch (JSONException e) {
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
		map.put(grouplist.get(Integer.parseInt(id) - 1).getName(), feiliaolist);

	}

	public void doSearchNongyao(final String id) {
		RequestParams params = new RequestParams();
		params.put("parentid", id);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						Toast.makeText(getActivity(), "没有对应的类目信息!",
								Toast.LENGTH_SHORT).show();
					} else if ("1".equals(code)) {
						try {
							nongyaolist = JsonUtils.getLeiMuByInfo(arg2);
							map.put(grouplist.get(Integer.parseInt(id) - 1)
									.getName(), nongyaolist);
							adapter.notifyDataSetChanged();
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
		map.put(grouplist.get(Integer.parseInt(id) - 1).getName(), nongyaolist);
	}

	public void doSearchZhongzi(final String id) {
		RequestParams params = new RequestParams();
		params.put("parentid", id);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						Toast.makeText(getActivity(), "没有对应的类目信息!",
								Toast.LENGTH_SHORT).show();
					} else if ("1".equals(code)) {
						try {
							zhongzilist = JsonUtils.getLeiMuByInfo(arg2);
							map.put(grouplist.get(Integer.parseInt(id) - 1)
									.getName(), zhongzilist);
							adapter.notifyDataSetChanged();
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
		map.put(grouplist.get(Integer.parseInt(id) - 1).getName(), zhongzilist);
	}

	public void doSearchNongji(final String id) {
		RequestParams params = new RequestParams();
		params.put("parentid", id);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						Toast.makeText(getActivity(), "没有对应的类目信息!",
								Toast.LENGTH_SHORT).show();
					} else if ("1".equals(code)) {
						try {
							nongjilist = JsonUtils.getLeiMuByInfo(arg2);
							map.put(grouplist.get(Integer.parseInt(id) - 1)
									.getName(), nongjilist);
							adapter.notifyDataSetChanged();
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
		map.put(grouplist.get(Integer.parseInt(id) - 1).getName(), nongjilist);
	}

	public void doSearchNongmo(final String id) {
		RequestParams params = new RequestParams();
		params.put("parentid", id);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						Toast.makeText(getActivity(), "没有对应的类目信息!",
								Toast.LENGTH_SHORT).show();
					} else if ("1".equals(code)) {
						try {
							nongmolist = JsonUtils.getLeiMuByInfo(arg2);
							map.put(grouplist.get(Integer.parseInt(id) - 1)
									.getName(), nongmolist);
							adapter.notifyDataSetChanged();
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
		map.put(grouplist.get(Integer.parseInt(id) - 1).getName(), nongmolist);
	}

	class ExpandableListAdapter extends BaseExpandableListAdapter {
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
			String type = grouplist.get(groupPosition).getName();
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
			String key = grouplist.get(groupPosition).getName();
			holder.child_name
					.setText(map.get(key).get(childPosition).getName());
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {

			return true;
		}
	}

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
