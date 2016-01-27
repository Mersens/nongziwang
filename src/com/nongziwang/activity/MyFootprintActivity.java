package com.nongziwang.activity;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.MyFootprintAdapter;
import com.nongziwang.adapter.MyFootprintAdapter.ViewHolder;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.FootprintBean;
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
 * @title MyFootprintActivity
 * @description:MyFootprintActivity
 * @author Mersens
 * @time 2016年1月27日
 */
public class MyFootprintActivity extends BaseActivity implements
		OnClickListener, IXListViewListener, EventListener {
	private ImageView image_back;
	private ImageView image_del;
	private TextView tv_editor, tv_title;
	private XListView listView;
	private RelativeLayout layout_del;
	private MyFootprintAdapter adapter;
	private RelativeLayout layout_loading;
	private List<FootprintBean> lists = new ArrayList<FootprintBean>();
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "history/getHistoryByUserId";
	private static final String DEL_URL = AppConstants.SERVICE_ADDRESS
			+ "history/deleteHistoryById";
	private static final String TAG = "MyFootprintActivity";
	private String userid;
	private int currpage = 1;
	private StringBuffer sbf;
	private SpotsDialog spotsdialog;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_myfootprint);
		userid = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUserId();
		initViews();
		initEvent();
		initDatas();

	}

	private void initViews() {
		layout_loading = (RelativeLayout) findViewById(R.id.layout_loading);
		tv_title = (TextView) findViewById(R.id.tv_title);
		image_back = (ImageView) findViewById(R.id.image_back);
		image_del = (ImageView) findViewById(R.id.image_del);
		tv_editor = (TextView) findViewById(R.id.tv_editor);
		layout_del = (RelativeLayout) findViewById(R.id.layout_del);
		listView = (XListView) findViewById(R.id.listView);
		listView.setPullLoadEnable(false);
		listView.setPullRefreshEnable(false);
		listView.setXListViewListener(this);

	}

	private void initEvent() {
		image_back.setOnClickListener(this);
		image_del.setOnClickListener(this);
		tv_editor.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (view.getTag() instanceof ViewHolder) {
					ViewHolder holder = (ViewHolder) view.getTag();
					holder.checkbox.toggle();
				}

			}

		});
		layout_del.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DialogTips dialog = new DialogTips(MyFootprintActivity.this,
						"温馨提示", "您确定要删除?", "确定", true, true);
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

	public void initDatas() {
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("userid", currpage + "");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					image_del.setVisibility(View.GONE);
					tv_editor.setVisibility(View.GONE);
					ShowToast("userid为空!");

				} else if ("1".equals(code)) {
					try {
						List<FootprintBean> list = JsonUtils
								.getFootprintInfo(arg2);
						if (list.size() == 0) {
							image_del.setVisibility(View.GONE);
							tv_editor.setVisibility(View.GONE);
						}
						lists.addAll(list);
						adapter = new MyFootprintAdapter(list,
								MyFootprintActivity.this);
						listView.setAdapter(adapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if ("2".equals(code)) {
					image_del.setVisibility(View.GONE);
					tv_editor.setVisibility(View.GONE);
					ShowToast("历史记录为空!");
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				ShowToast("查询失败!");
				Log.e(TAG, arg2 == null ? "" : arg2);

			}

			@Override
			public void onFinish() {
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
	}

	public void doJudgeCheck() {

		List<Integer> list = getCheckNum();
		if (list.size() == 0) {
			DialogTips dialog = new DialogTips(MyFootprintActivity.this,
					"请选择需要删除的信息！", "确定");
			dialog.show();
			dialog = null;
			return;
		}
		for (int n : list) {
			List<FootprintBean> mlist = adapter.getDatas();
			FootprintBean bean = mlist.get(n);
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
				MyFootprintActivity.this.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						spotsdialog = new SpotsDialog(MyFootprintActivity.this,
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
					Toast.makeText(MyFootprintActivity.this, "id为空!",
							Toast.LENGTH_SHORT).show();
				} else if ("1".equals(code)) {
					int count = adapter.getCount();
					for (int n : list) {
						int position = n - (count - adapter.getCount());
						adapter.getCheckMap().remove(n);
						adapter.remove(position);
					}
					DialogTips dialog = new DialogTips(
							MyFootprintActivity.this, "删除成功！", "确定");
					dialog.show();
					dialog = null;
					adapter.notifyDataSetChanged();
					if (adapter.getDatas().size() == 0) {
						image_del.setVisibility(View.GONE);
						tv_editor.setVisibility(View.GONE);
					}
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				ShowToast("删除失败!");
				Log.e(TAG, arg2 == null ? "" : arg2);
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

		int count = adapter.getCount();

		for (int i = 0; i < count; i++) {
			if (map.get(i) != null && map.get(i)) {
				mlist.add(i);
			}
		}
		return mlist;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_back:
			finishActivity();
			break;
		case R.id.image_del:
			do_DelDetail();
			break;
		case R.id.tv_editor:
			do_CancelDetail();
			break;
		}

	}

	public void do_DelDetail() {
		tv_title.setText("批量删除");
		layout_del.setVisibility(View.VISIBLE);
		tv_editor.setEnabled(true);
		tv_editor.setVisibility(View.VISIBLE);
		image_del.setVisibility(View.GONE);
		adapter.configHideCheckMap(false);
		adapter.notifyDataSetChanged();

	}

	public void do_CancelDetail() {
		tv_title.setText("我的足迹");
		layout_del.setVisibility(View.GONE);
		tv_editor.setEnabled(false);
		tv_editor.setVisibility(View.GONE);
		image_del.setVisibility(View.VISIBLE);
		adapter.configHideCheckMap(true);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onRefresh() {

	}

	@Override
	public void onLoadMore() {

	}

}
