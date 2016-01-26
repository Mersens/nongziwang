package com.nongziwang.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.MyArrayAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.LeiMuBean;
import com.nongziwang.entity.MyRegion;
import com.nongziwang.main.MainActivity;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.ListUtils;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.view.DatePickerPopWindow;
import com.nongziwang.view.DialogTips;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.SpotsDialog;

public class FbxjdActivity extends BaseActivity {
	private Spinner spinner_dw, spinner_gys_province, spinner_gys_city,
			spinner_address_province, spinner_address_city;
	private List<MyRegion> provinces;
	private List<MyRegion> address_citys;
	private List<MyRegion> gys_citys;
	private MyArrayAdapter adapter, dwAdapter;
	private MyArrayAdapter address_citys_adapter;
	private MyArrayAdapter gys_citys_adapter;
	private EditText edt_time, edt_title, edt_num;
	public static final String ACTION_OK = "ok";
	public static final String ACTION_CANCEL = "cancel";
	private DatePickerPopWindow popWindow = null;
	private String[] types = { "种子", "农药", "化肥", "农机", "农膜" };
	private RadioGroup radiogroup_type;
	private NongziDao dao;
	private Button btn_fabu;
	private List<MyRegion> dws;
	private String userid;
	private SpotsDialog dialog;

	private static final String TAG = "FbxjdActivity";
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "xunjia/gotoAddXunjiaData";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_fbxjd);
		registerBoradcastReceiver();
		userid = SharePreferenceUtil.getInstance(getApplicationContext())
				.getUserId();
		dao = new NongziDaoImpl(this);
		initViews();
		initEvents();
		initType();
	}

	private void initType() {
		for (int i = 0; i < types.length; i++) {
			RadioButton rb = (RadioButton) LayoutInflater.from(
					FbxjdActivity.this).inflate(R.layout.layout_radiobutton,
					null);
			if (i == 0) {
				rb.setChecked(true);
			}
			rb.setId(i + 1);
			rb.setText(types[i]);
			MarginLayoutParams lp = new MarginLayoutParams(new LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			lp.rightMargin = 4;
			radiogroup_type.addView(rb, lp);

		}
	}

	private void initViews() {
		edt_title = (EditText) findViewById(R.id.edt_title);
		edt_num = (EditText) findViewById(R.id.edt_num);
		btn_fabu = (Button) findViewById(R.id.btn_fabu);
		radiogroup_type = (RadioGroup) findViewById(R.id.radiogroup_type);
		edt_time = (EditText) findViewById(R.id.edt_time);
		spinner_dw = (Spinner) findViewById(R.id.spinner_dw);
		spinner_gys_province = (Spinner) findViewById(R.id.spinner_gys_province);
		spinner_gys_city = (Spinner) findViewById(R.id.spinner_gys_city);
		spinner_address_province = (Spinner) findViewById(R.id.spinner_address_province);
		spinner_address_city = (Spinner) findViewById(R.id.spinner_address_city);
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "发布询价单",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
		setHeadViewBg(R.color.white_color);
		dialog=new SpotsDialog(FbxjdActivity.this,R.style.SpotsDialogWaiting);
		dialog.setCanceledOnTouchOutside(false);

	}

	private void showPop() {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		popWindow = new DatePickerPopWindow(FbxjdActivity.this,
				df.format(new Date()), 0);
		popWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		popWindow.setTouchable(true);
		popWindow.setFocusable(true);
		popWindow.setOutsideTouchable(false);
		popWindow.setAnimationStyle(R.style.GrowFromBottom);
		popWindow.showAtLocation(findViewById(R.id.layout_popu_main),
				Gravity.BOTTOM, 0, 0);
	}

	private void initEvents() {
		btn_fabu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doFabu();

			}
		});
		address_citys = new ArrayList<MyRegion>();
		address_citys.add(new MyRegion("1", "请选择", ""));
		gys_citys = new ArrayList<MyRegion>();
		gys_citys.add(new MyRegion("1", "请选择", ""));
		provinces = dao.findAllProvinces();
		edt_time.setKeyListener(null);
		edt_time.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPop();
			}
		});

		spinner_gys_province
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (position != 0) {
							String parintid = provinces.get(position).getId();
							gys_citys.clear();
							gys_citys = dao.findAllCitysByProvincesId(parintid);
							gys_citys_adapter.clear();
							gys_citys_adapter.addAll(gys_citys);
							gys_citys_adapter.notifyDataSetChanged();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		spinner_address_province
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (position != 0) {
							String parintid = provinces.get(position).getId();
							address_citys.clear();
							address_citys = dao
									.findAllCitysByProvincesId(parintid);
							address_citys_adapter.clear();
							address_citys_adapter.addAll(address_citys);
							address_citys_adapter.notifyDataSetChanged();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		dws = new ArrayList<MyRegion>();
		dws.add(new MyRegion(null, "单位", null));
		dws.add(new MyRegion(null, "袋", null));
		dws.add(new MyRegion(null, "瓶", null));
		dws.add(new MyRegion(null, "吨", null));
		dws.add(new MyRegion(null, "K", null));
		dws.add(new MyRegion(null, "KG", null));

		adapter = new MyArrayAdapter(FbxjdActivity.this,
				android.R.layout.simple_spinner_item, provinces);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		address_citys_adapter = new MyArrayAdapter(FbxjdActivity.this,
				android.R.layout.simple_spinner_item, address_citys);
		address_citys_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		gys_citys_adapter = new MyArrayAdapter(FbxjdActivity.this,
				android.R.layout.simple_spinner_item, gys_citys);
		gys_citys_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		dwAdapter = new MyArrayAdapter(FbxjdActivity.this,
				android.R.layout.simple_spinner_item, dws);
		dwAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_dw.setAdapter(dwAdapter);
		spinner_dw.setSelection(0, true);
		spinner_gys_province.setAdapter(adapter);
		spinner_gys_city.setAdapter(gys_citys_adapter);
		spinner_gys_city.setSelection(0, true);
		spinner_address_province.setAdapter(adapter);
		spinner_address_city.setAdapter(address_citys_adapter);
		spinner_address_city.setSelection(0, true);
	}

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_OK)) {
				String time = intent.getStringExtra("time");
				if (!TextUtils.isEmpty(time)) {
					edt_time.setText(time);
				}
				popWindow.dismiss();
			} else if (action.equals(ACTION_CANCEL)) {
				popWindow.dismiss();
			}
		}
	};

	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_OK);
		myIntentFilter.addAction(ACTION_CANCEL);
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}

	public void doFabu() {
		String spinner_dws = spinner_dw.getSelectedItem().toString();
		if ("单位".equals(spinner_dws)) {
			spinner_dws = null;
		}
		String spinner_gys_provinces = spinner_gys_province.getSelectedItem()
				.toString();
		if ("请选择".equals(spinner_gys_provinces)) {
			spinner_gys_provinces = null;
		}
		String spinner_gys_citys = spinner_gys_city.getSelectedItem()
				.toString();
		if ("请选择".equals(spinner_gys_citys)) {
			spinner_gys_citys = null;
		}
		String spinner_address_provinces = spinner_address_province
				.getSelectedItem().toString();
		if ("请选择".equals(spinner_address_provinces)) {
			spinner_address_provinces = null;
		}
		String spinner_address_citys = spinner_address_city.getSelectedItem()
				.toString();
		if ("请选择".equals(spinner_address_citys)) {
			spinner_address_citys = null;
		}
		String edt_times = edt_time.getText().toString().trim();
		String edt_titles = edt_title.getText().toString().trim();
		String edt_nums = edt_num.getText().toString().trim();
		if (TextUtils.isEmpty(spinner_dws)
				|| TextUtils.isEmpty(spinner_gys_provinces)
				|| TextUtils.isEmpty(spinner_gys_citys)
				|| TextUtils.isEmpty(spinner_address_provinces)
				|| TextUtils.isEmpty(spinner_address_citys)
				|| TextUtils.isEmpty(edt_times)
				|| TextUtils.isEmpty(edt_titles) || TextUtils.isEmpty(edt_nums)) {
			ShowToast("请完善信息！");
			return;
		}

		String gongyingprovinceid = ListUtils.getMyRegionId(provinces,
				spinner_gys_provinces);

		List<MyRegion> gongyingcitys = dao
				.findAllCitysByProvincesId(gongyingprovinceid == null ? ""
						: gongyingprovinceid);
		String gongyingcityid = ListUtils.getMyRegionId(gongyingcitys,
				spinner_gys_citys);
		String shouhuoprovinceid = ListUtils.getMyRegionId(provinces,
				spinner_address_provinces);
		List<MyRegion> shouhuocitys = dao
				.findAllCitysByProvincesId(shouhuoprovinceid == null ? ""
						: shouhuoprovinceid);
		String shouhuocityid = ListUtils.getMyRegionId(shouhuocitys,
				spinner_address_citys);

		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("title", edt_titles);
		params.put("leimuid", radiogroup_type.getCheckedRadioButtonId() + "");
		params.put("shuliang", edt_nums);
		params.put("danwei", spinner_dws);
		params.put("gongyingprovinceid", gongyingprovinceid);
		params.put("gongyingcityid", gongyingcityid);
		params.put("shouhuoprovinceid", shouhuoprovinceid);
		params.put("shouhuocityid", shouhuocityid);
		params.put("jiezhiriqi", edt_times);

		doHttp(params);
	}

	public void doHttp(RequestParams params) {
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				dialog.show();
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					ShowToast("有空值!");
				} else if ("1".equals(code)) {
					DialogTips dialog = new DialogTips(FbxjdActivity.this,
							"添加成功？", "确定");
					dialog.show();
					dialog = null;
					resetDatas();
				} else if ("2".equals(code)) {
					ShowToast("询价数量不是一个整数!");
				} else if ("3".equals(code)) {
					ShowToast("供应商省份id不正确!");
				} else if ("4".equals(code)) {
					ShowToast("供应商城市id不正确!");
				} else if ("5".equals(code)) {
					ShowToast("收货方省份id不正确!");
				} else if ("6".equals(code)) {
					ShowToast("收货方城市id不正确!");
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ShowToast("添加失败！");

			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				dialog.dismiss();

			}
		});
	}

	public void resetDatas() {
		edt_time.setText(R.string.tv_select_time);
		edt_title.setText("");
		edt_num.setText(R.string.tv_num);

		spinner_dw.setSelection(0, true);
		spinner_gys_province.setSelection(0, true);
		spinner_gys_city.setSelection(0, true);
		spinner_address_province.setSelection(0, true);
		spinner_address_city.setSelection(0, true);
	}

}
