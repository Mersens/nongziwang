package com.nongziwang.fragment;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;

import android.content.Context;
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
import com.nongziwang.activity.ProductDetailFragmentActivity;
import com.nongziwang.adapter.MyProductAdapter;
import com.nongziwang.adapter.MyProductAdapter.ViewHolder;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.view.DialogTips;
import com.nongziwang.view.DialogWaiting;
import com.nongziwang.view.XListView;
import com.nongziwang.view.XListView.IXListViewListener;
/**
 * 
 * @title ProductManagementFragment
 * @description:ProductManagementFragment
 * @author Mersens
 * @time 2016��1��27��
 */
public class ProductManagementFragment extends BaseFragment implements
		IXListViewListener, EventListener {
	private View view;
	private XListView listView;
	private String param;
	public static int xinxiststic = 0;
	private boolean isPrepared;
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
	private DialogWaiting dialog;
	private boolean isShangjia = false;
	private RelativeLayout layout_cz,layout_tool;
	private Context  context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.listview_pro_manage, container, false);
		param = getArguments().getString("params");
		userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserId();
		context=view.getContext();
		isPrepared = true;
		initViews();
		initEvent();
		lazyLoad();
		return view;
	}

	private void initViews() {
		tv_del = (TextView) view.findViewById(R.id.tv_del);
		tv_plxj = (TextView) view.findViewById(R.id.tv_plxj);
		btn_check_all = (CheckBox) view.findViewById(R.id.btn_check_all);
		layout_loading = (RelativeLayout) view
				.findViewById(R.id.layout_loading);
		layout_cz=(RelativeLayout) view.findViewById(R.id.layout_cz);
		layout_tool=(RelativeLayout) view.findViewById(R.id.layout_tool);
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
				String chanpinid=lists.get(position-1).getChanpinid();
				intentAction(getActivity(),ProductDetailFragmentActivity.class,chanpinid);
			}
		});
		btn_check_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if(adapter!=null){
					adapter.configCheckMap(isChecked);
					adapter.notifyDataSetChanged();
				}

			}
		});

		tv_plxj.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(adapter==null){
					return;
				}
				if (isShangjia) {
					DialogTips dialog = new DialogTips(context, "��ܰ��ʾ",
							"��ȷ��Ҫ�ϼ�?", "ȷ��", true, true);
					dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogInterface,
								int userId) {
							doShangJiaCheck();
						}
					});
					dialog.show();
					dialog = null;
				} else {
					DialogTips dialog = new DialogTips(context, "��ܰ��ʾ",
							"��ȷ��Ҫ�¼�?", "ȷ��", true, true);
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
				if(adapter==null){
					return;
				}
				DialogTips dialog = new DialogTips(context, "��ܰ��ʾ",
						"��ȷ��Ҫɾ��?", "ȷ��", true, true);
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
			DialogTips dialog = new DialogTips(context, "��ѡ����Ҫ�ϼܵ���Ϣ��",
					"ȷ��");
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
				dialog = new DialogWaiting(context
						);
				dialog.show();

				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(context, "��ƷidΪ��!", Toast.LENGTH_SHORT)
							.show();
				} else if ("1".equals(code)) {
					int count = adapter.getCount();
					for (int n : list) {
						int position = n - (count - adapter.getCount());
						adapter.getCheckMap().remove(n);
						adapter.remove(position);
					}
					DialogTips dialog = new DialogTips(context, "�ϼܳɹ���",
							"ȷ��");
					dialog.show();
					dialog = null;
					adapter.notifyDataSetChanged();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(context, "�ϼ�ʧ��!", Toast.LENGTH_SHORT)
				.show();
				Log.e(TAG, arg2==null?"":arg2);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		});

	}

	public void doXiaJiaCheck() {

		List<Integer> list = getCheckNum();
		if (list.size() == 0) {
			DialogTips dialog = new DialogTips(context, "��ѡ����Ҫ�¼ܵ���Ϣ��",
					"ȷ��");
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
						dialog = new DialogWaiting(context
								);
						dialog.show();
					}
				});

				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(context, "��ƷidΪ��!", Toast.LENGTH_SHORT)
							.show();
				} else if ("1".equals(code)) {
					int count = adapter.getCount();
					for (int n : list) {
						int position = n - (count - adapter.getCount());
						adapter.getCheckMap().remove(n);
						adapter.remove(position);
					}
					DialogTips dialog = new DialogTips(context, "�¼ܳɹ���",
							"ȷ��");
					dialog.show();
					dialog = null;
					adapter.notifyDataSetChanged();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(context, "�¼�ʧ��!", Toast.LENGTH_SHORT)
				.show();
				Log.e(TAG, arg2==null?"":arg2);

			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		});

	}

	public void doJudgeCheck() {

		List<Integer> list = getCheckNum();
		if (list.size() == 0) {
			DialogTips dialog = new DialogTips(context, "��ѡ����Ҫɾ������Ϣ��",
					"ȷ��");
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
						dialog = new DialogWaiting(context);
						dialog.show();
					}
				});

				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(context, "��ƷidΪ��!", Toast.LENGTH_SHORT)
							.show();
				} else if ("1".equals(code)) {
					int count = adapter.getCount();
					for (int n : list) {
						int position = n - (count - adapter.getCount());
						adapter.getCheckMap().remove(n);
						adapter.remove(position);
					}
					DialogTips dialog = new DialogTips(context, "ɾ���ɹ���",
							"ȷ��");
					dialog.show();
					dialog = null;
					adapter.notifyDataSetChanged();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(context, "ɾ��ʧ��!", Toast.LENGTH_SHORT)
				.show();
				Log.e(TAG, arg2==null?"":arg2);
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
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
		// ��ȡ��ǰ����������
		int count = adapter.getCount();
		// ���б���
		for (int i = 0; i < count; i++) {
			if (map.get(i) != null && map.get(i)) {
				mlist.add(i);
			}
		}
		return mlist;
	}

	@Override
	protected void lazyLoad() {
		if (!isPrepared || !isVisible) {
			return;
		}
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
			tv_plxj.setText("�����ϼ�");
			isShangjia = true;
			xinxiststic = 3;
			break;
		}
		if(xinxiststic==0){
			layout_tool.setVisibility(View.GONE);
			layout_cz.setVisibility(View.GONE);
		}
		if(xinxiststic==2){
			tv_plxj.setVisibility(View.GONE);
		}
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("xinxiststic", xinxiststic);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(context, "���û�û�е���!",
							Toast.LENGTH_SHORT).show();
				} else if ("1".equals(code)) {
					try {
						List<ChanPinBean> list = JsonUtils.getChanPinInfo(arg2);
						lists.addAll(list);
						adapter = new MyProductAdapter(list, getActivity());
						listView.setAdapter(adapter);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if ("2".equals(code)) {
					Toast.makeText(context, "û�в�Ʒ��Ϣ!", Toast.LENGTH_SHORT)
							.show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				Toast.makeText(context, "��ȡʧ��!", Toast.LENGTH_SHORT)
						.show();
			}

			@Override
			public void onFinish() {
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
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

	}

}
