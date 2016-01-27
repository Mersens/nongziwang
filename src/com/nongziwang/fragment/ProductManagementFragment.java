package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.MyProductAdapter;
import com.nongziwang.adapter.MyProductAdapter.ViewHolder;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.view.DialogTips;
import com.nongziwang.view.SpotsDialog;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;
/**
 * 
 * @title ProductManagementFragment
 * @description:ProductManagementFragment
 * @author Mersens
 * @time 2016年1月27日
 */
public class ProductManagementFragment extends BaseFragment implements
		IXListViewListener, EventListener {
	private View view;
	private XListView listView;
	private String param;
	public static int xinxiststic = 0;
	private String userid;
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "chanpin/getChanpinByStatic";
	private static final String DEL_URL = AppConstants.SERVICE_ADDRESS
			+ "chanpin/gotoDeleteChanpin";
	public static final String XIAJIA_URL = AppConstants.SERVICE_ADDRESS
			+ "chanpin/gotoXiajiaChanpin";
	public static final String SHANGJIA_URL = AppConstants.SERVICE_ADDRESS
			+ "chanpin/gotoShangjiaChanpin";
	private static final String TAG = "ProductManagementFragment";
	private List<ChanPinBean> lists = new ArrayList<ChanPinBean>();
	private MyProductAdapter adapter;
	private RelativeLayout layout_loading;
	private CheckBox btn_check_all;
	private TextView tv_plxj, tv_del;
	private StringBuffer sbf;
	private SpotsDialog spotsdialog;
	private boolean isShangjia = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.listview_pro_manage, container, false);
		param = getArguments().getString("params");
		userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserId();
		initViews();
		initEvent();
		initDatas();
		return view;
	}

	private void initViews() {
		tv_del = (TextView) view.findViewById(R.id.tv_del);
		tv_plxj = (TextView) view.findViewById(R.id.tv_plxj);
		btn_check_all = (CheckBox) view.findViewById(R.id.btn_check_all);
		layout_loading = (RelativeLayout) view
				.findViewById(R.id.layout_loading);
		listView = (XListView) view.findViewById(R.id.listView);
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(false);
		listView.setXListViewListener(this);

	}

	private void initEvent() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (view.getTag() instanceof ViewHolder) {
					ViewHolder holder = (ViewHolder) view.getTag();
					// 会自动出发CheckBox的checked事件
					holder.checkbox.toggle();

				}

			}

		});
		btn_check_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				adapter.configCheckMap(isChecked);
				adapter.notifyDataSetChanged();

			}
		});

		tv_plxj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isShangjia) {
					DialogTips dialog = new DialogTips(getActivity(), "温馨提示",
							"您确定要上架?", "确定", true, true);
					dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogInterface,
								int userId) {
							doShangJiaCheck();
						}
					});
					dialog.show();
					dialog = null;
				} else {
					DialogTips dialog = new DialogTips(getActivity(), "温馨提示",
							"您确定要下架?", "确定", true, true);
					dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogInterface,
								int userId) {
							doXiaJiaCheck();
						}
					});
					dialog.show();
					dialog = null;
				}

			}
		});
		tv_del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogTips dialog = new DialogTips(getActivity(), "温馨提示",
						"您确定要删除?", "确定", true, true);
				dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialogInterface,
							int userId) {
						doJudgeCheck();
					}
				});
				dialog.show();
				dialog = null;

			}

		});

	}

	public void doShangJiaCheck() {
		List<Integer> list = getCheckNum();
		if (list.size() == 0) {
			DialogTips dialog = new DialogTips(getActivity(), "请选择需要上架的信息！",
					"确定");
			dialog.show();
			dialog = null;
			return;
		}
		for (int n : list) {
			List<ChanPinBean> mlist = adapter.getDatas();
			ChanPinBean bean = mlist.get(n);
			sbf.append(bean.getChanpinid() + ",");
		}
		if (sbf.toString().length() > 0) {
			doShangJia(sbf.toString(), list);
		}
	}

	private void doShangJia(String ids, final List<Integer> list) {
		RequestParams params = new RequestParams();
		params.put("chanpinids", ids);
		HttpUtils.doPost(SHANGJIA_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						spotsdialog = new SpotsDialog(getActivity(),
								R.style.SpotsDialogWaiting);
						spotsdialog.show();
					}
				});

				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(getActivity(), "产品id为空!", Toast.LENGTH_SHORT)
							.show();
				} else if ("1".equals(code)) {
					int count = adapter.getCount();
					for (int n : list) {
						int position = n - (count - adapter.getCount());
						adapter.getCheckMap().remove(n);
						adapter.remove(position);
					}
					DialogTips dialog = new DialogTips(getActivity(), "上架成功！",
							"确定");
					dialog.show();
					dialog = null;
					adapter.notifyDataSetChanged();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(getActivity(), "上架失败!", Toast.LENGTH_SHORT)
				.show();
				Log.e(TAG, arg2==null?"":arg2);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (spotsdialog != null && spotsdialog.isShowing()) {
					spotsdialog.dismiss();
				}
			}
		});

	}

	public void doXiaJiaCheck() {

		List<Integer> list = getCheckNum();
		if (list.size() == 0) {
			DialogTips dialog = new DialogTips(getActivity(), "请选择需要下架的信息！",
					"确定");
			dialog.show();
			dialog = null;
			return;
		}
		for (int n : list) {
			List<ChanPinBean> mlist = adapter.getDatas();
			ChanPinBean bean = mlist.get(n);
			sbf.append(bean.getChanpinid() + ",");
		}
		if (sbf.toString().length() > 0) {
			doXiaJia(sbf.toString(), list);
		}
	}

	private void doXiaJia(String ids, final List<Integer> list) {
		RequestParams params = new RequestParams();
		params.put("chanpinids", ids);
		HttpUtils.doPost(XIAJIA_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						spotsdialog = new SpotsDialog(getActivity(),
								R.style.SpotsDialogWaiting);
						spotsdialog.show();
					}
				});

				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(getActivity(), "产品id为空!", Toast.LENGTH_SHORT)
							.show();
				} else if ("1".equals(code)) {
					int count = adapter.getCount();
					for (int n : list) {
						int position = n - (count - adapter.getCount());
						adapter.getCheckMap().remove(n);
						adapter.remove(position);
					}
					DialogTips dialog = new DialogTips(getActivity(), "下架成功！",
							"确定");
					dialog.show();
					dialog = null;
					adapter.notifyDataSetChanged();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(getActivity(), "下架失败!", Toast.LENGTH_SHORT)
				.show();
				Log.e(TAG, arg2==null?"":arg2);

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (spotsdialog != null && spotsdialog.isShowing()) {
					spotsdialog.dismiss();
				}
			}
		});

	}

	public void doJudgeCheck() {

		List<Integer> list = getCheckNum();
		if (list.size() == 0) {
			DialogTips dialog = new DialogTips(getActivity(), "请选择需要删除的信息！",
					"确定");
			dialog.show();
			dialog = null;
			return;
		}
		for (int n : list) {
			List<ChanPinBean> mlist = adapter.getDatas();
			ChanPinBean bean = mlist.get(n);
			sbf.append(bean.getChanpinid() + ",");
		}
		if (sbf.toString().length() > 0) {
			doDel(sbf.toString(), list);
		}
	}

	public void doDel(String ids, final List<Integer> list) {
		RequestParams params = new RequestParams();
		params.put("chanpinids", ids);
		HttpUtils.doPost(DEL_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						spotsdialog = new SpotsDialog(getActivity(),
								R.style.SpotsDialogWaiting);
						spotsdialog.show();
					}
				});

				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(getActivity(), "产品id为空!", Toast.LENGTH_SHORT)
							.show();
				} else if ("1".equals(code)) {
					int count = adapter.getCount();
					for (int n : list) {
						int position = n - (count - adapter.getCount());
						adapter.getCheckMap().remove(n);
						adapter.remove(position);
					}
					DialogTips dialog = new DialogTips(getActivity(), "删除成功！",
							"确定");
					dialog.show();
					dialog = null;
					adapter.notifyDataSetChanged();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(getActivity(), "删除失败!", Toast.LENGTH_SHORT)
				.show();
				Log.e(TAG, arg2==null?"":arg2);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (spotsdialog != null && spotsdialog.isShowing()) {
					spotsdialog.dismiss();
				}
			}
		});

	}

	public List<Integer> getCheckNum() {
		List<Integer> mlist = new ArrayList<Integer>();
		if (sbf == null) {
			sbf = new StringBuffer();
		} else {
			sbf.delete(0, sbf.length());
		}
		Map<Integer, Boolean> map = adapter.getCheckMap();
		// 获取当前的数据数量
		int count = adapter.getCount();

		// 进行遍历
		for (int i = 0; i < count; i++) {
			if (map.get(i) != null && map.get(i)) {
				mlist.add(i);
			}
		}
		return mlist;

	}

	private void initDatas() {
		switch (Integer.parseInt(param)) {
		case 0:
			xinxiststic = 1;
			break;
		case 1:
			xinxiststic = 0;
			break;
		case 2:
			xinxiststic = 2;
			break;
		case 3:
			tv_plxj.setText("批量上架");
			isShangjia = true;
			xinxiststic = 3;
			break;
		}
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("xinxiststic", xinxiststic);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(getActivity(), "该用户没有店铺!",
							Toast.LENGTH_SHORT).show();
				} else if ("1".equals(code)) {
					try {
						List<ChanPinBean> list = JsonUtils.getChanPinInfo(arg2);
						lists.addAll(list);
						adapter = new MyProductAdapter(list, getActivity());
						listView.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("2".equals(code)) {
					Toast.makeText(getActivity(), "没有产品信息!", Toast.LENGTH_SHORT)
							.show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				Toast.makeText(getActivity(), "获取失败!", Toast.LENGTH_SHORT)
						.show();

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});

	}

	@Override
	protected void lazyLoad() {

	}

	public static Fragment getInstance(String params) {
		ProductManagementFragment fragment = new ProductManagementFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onRefresh() {

	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

}
