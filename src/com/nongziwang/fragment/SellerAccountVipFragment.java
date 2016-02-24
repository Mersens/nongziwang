package com.nongziwang.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.MyArrayAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.GongsiBean;
import com.nongziwang.entity.MyRegion;
import com.nongziwang.entity.UserBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.BitmapUtils;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.ImageLoadOptions;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.ListUtils;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

public class SellerAccountVipFragment extends BaseFragment implements
		OnClickListener {
	private View view;
	private EditText edt_gongsiname, edt_gongsimiaoshu, edt_xxdz, edt_gsdh,
			edt_gscz, edt_lxr, edt_lxdh, edt_gsjc;
	private Spinner spinner_province, spinner_city, spinner_area;
	private List<MyRegion> provinces;
	private List<MyRegion> citys;
	private List<MyRegion> areas;
	private MyArrayAdapter provincesAdapter, citysAdapter, areasAdapter;
	private NongziDao dao;
	private ImageView image_yyzz, image_swdj, image_zzjg;
	private Bitmap bitmap;
	private Context context;
	private Button btn_submit;
	private int initProvinceIndex = 0;
	private int initCityIndex = 0;
	private int initAreaIndex = 0;
	private Map<String, String> map = new HashMap<String, String>();
	private static final String UPLAODE_URL = AppConstants.SERVICE_ADDRESS
			+ "gongsi/gotoUpGongsiZhengjian";
	private static final String ADD_URL = AppConstants.SERVICE_ADDRESS
			+ "gongsi/gotoAddGongsiXinxi";
	private static final String GONGSIDATA_URL = AppConstants.SERVICE_ADDRESS
			+ "gongsi/getGongsiDataById";
	private static final String TAG = "SellerAccountVipFragment";
	private String userid;
	private GongsiBean gongsibean;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_seller_account_vip, container,
				false);
		dao = new NongziDaoImpl(getActivity());
		userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserId();
		context = view.getContext();
		initViews();
		initEvent();
		initDatas();
		return view;
	}

	private void initViews() {
		edt_gsjc = (EditText) view.findViewById(R.id.edt_gsjc);
		edt_gongsiname = (EditText) view.findViewById(R.id.edt_gongsiname);
		edt_gongsimiaoshu = (EditText) view
				.findViewById(R.id.edt_gongsimiaoshu);
		edt_xxdz = (EditText) view.findViewById(R.id.edt_xxdz);
		edt_gsdh = (EditText) view.findViewById(R.id.edt_gsdh);
		edt_gscz = (EditText) view.findViewById(R.id.edt_gscz);
		edt_lxr = (EditText) view.findViewById(R.id.edt_lxr);
		edt_lxdh = (EditText) view.findViewById(R.id.edt_lxdh);
		image_zzjg = (ImageView) view.findViewById(R.id.image_zzjg);
		image_yyzz = (ImageView) view.findViewById(R.id.image_yyzz);
		image_swdj = (ImageView) view.findViewById(R.id.image_swdj);
		spinner_province = (Spinner) view.findViewById(R.id.spinner_province);
		spinner_city = (Spinner) view.findViewById(R.id.spinner_city);
		spinner_area = (Spinner) view.findViewById(R.id.spinner_area);
		btn_submit = (Button) view.findViewById(R.id.btn_submit);

	}

	private void initEvent() {
		provinces = dao.findAllProvinces();
		citys = new ArrayList<MyRegion>();
		citys.add(new MyRegion("1", "请选择", null));
		areas = new ArrayList<MyRegion>();
		areas.add(new MyRegion("1", "请选择", null));

		provincesAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, provinces);
		provincesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		citysAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, citys);
		citysAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		areasAdapter = new MyArrayAdapter(getActivity(),
				android.R.layout.simple_spinner_item, areas);
		areasAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_province.setAdapter(provincesAdapter);
		spinner_province.setSelection(0, true);

		spinner_city.setAdapter(citysAdapter);
		spinner_city.setSelection(0, true);

		spinner_area.setAdapter(areasAdapter);
		spinner_area.setSelection(0, true);

		spinner_province
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						if (position != 0) {
							String parintid = provinces.get(position).getId();
							citys.clear();
							citys = dao.findAllCitysByProvincesId(parintid);
							citysAdapter.clear();
							citysAdapter.addAll(citys);
							citysAdapter.notifyDataSetChanged();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});
		spinner_city.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (position != 0) {
					String parintid = citys.get(position).getId();
					areas.clear();
					areas = dao.findAllAreasByCityId(parintid);
					areasAdapter.clear();
					areasAdapter.addAll(areas);
					areasAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		image_yyzz.setOnClickListener(this);
		image_swdj.setOnClickListener(this);
		image_zzjg.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
		// edt_gongsiname.setKeyListener(null);

	}

	private void initDatas() {
		UserBean user = dao.findUserInfoById(userid);
		if (!TextUtils.isEmpty(user.getCompanyid())) {
			doGetDatas(user.getCompanyid());
		}
	}

	private void doGetDatas(String gongsiid) {
		RequestParams params = new RequestParams();
		params.put("gongsiid", gongsiid);

		HttpUtils.doPost(GONGSIDATA_URL, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(context, "找不到该公司信息!", Toast.LENGTH_SHORT)
							.show();
				} else if ("1".equals(code)) {
					try {
						gongsibean = JsonUtils.getGongsiInfo(arg2);
						setDatas();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(context, "获取信息失败!", Toast.LENGTH_SHORT).show();
				Log.e(TAG, arg2 == null ? "" : arg2);
			}
		});
	}

	public void setDatas() {
		edt_gongsiname.setText(gongsibean.getGongsiname());
		edt_gongsiname.setOnKeyListener(null);
		edt_gongsimiaoshu.setText(gongsibean.getMiaoshu());
		edt_xxdz.setText(gongsibean.getDizhi());
		edt_gsdh.setText(gongsibean.getDianhua());
		edt_gscz.setText(gongsibean.getChuanzhen());
		edt_lxr.setText(gongsibean.getLianxiren());
		edt_lxdh.setText(gongsibean.getLianxidianhua());
		edt_gsjc.setText(gongsibean.getJiancheng());

		ImageLoader.getInstance().displayImage(gongsibean.getYingyezhizhao(),
				image_yyzz, ImageLoadOptions.getOptionsLoading());
		ImageLoader.getInstance().displayImage(
				gongsibean.getShuiwudengjizheng(), image_swdj,
				ImageLoadOptions.getOptionsLoading());
		ImageLoader.getInstance().displayImage(
				gongsibean.getZuzhijigoudaimazheng(), image_zzjg,
				ImageLoadOptions.getOptionsLoading());
		initProvinceIndex = ListUtils.getInitIndex(provinces, gongsibean.getProvince());
		initCityIndex= ListUtils.getInitIndex(citys, gongsibean.getCity());
		initAreaIndex = ListUtils.getInitIndex(areas, gongsibean.getArea());

		if(initProvinceIndex!=0){
			spinner_province.setSelection(initProvinceIndex, true);
		}
		if(initCityIndex!=0){
			spinner_city.setSelection(initCityIndex, true);
		}
		if(initCityIndex!=0){
			spinner_area.setSelection(initAreaIndex,true);
		}
		
		
	}

	public static Fragment getInstance(String params) {
		SellerAccountVipFragment fragment = new SellerAccountVipFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_yyzz:
			isYyzz = true;
			selectImage();
			break;
		case R.id.image_swdj:
			isSwdj = true;
			selectImage();
			break;
		case R.id.image_zzjg:
			isZzjg = true;
			selectImage();
			break;
		case R.id.btn_submit:
			doAddSubmit();
			break;

		}
	}

	private boolean isYyzz = false;
	private boolean isSwdj = false;
	private boolean isZzjg = false;

	public void selectImage() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, getActivity().RESULT_FIRST_USER);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == getActivity().RESULT_FIRST_USER) {
			Cursor cursor = getActivity().getContentResolver().query(data.getData(),
					null, null, null, null);
			cursor.moveToFirst();
			int dex = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			String path = cursor.getString(dex);
			System.out.println("=========================="+path);
			bitmap = BitmapUtils.getBitMap(path);
			cursor.close();
			if (bitmap != null) {
				if (isYyzz) {
					image_yyzz.setImageBitmap(bitmap);
					upLoadeYyzzImage(path);
					isYyzz = false;
				}
				if (isSwdj) {
					image_swdj.setImageBitmap(bitmap);
					upLoadeSwdjImage(path);
					isSwdj = false;
				}
				if (isZzjg) {
					image_zzjg.setImageBitmap(bitmap);
					upLoadeZzjgImage(path);
					isZzjg = false;

				}
			}


		}

	}

	public void upLoadeYyzzImage(String path) {
		RequestParams params = new RequestParams();
		File file = new File(path);
		if (file != null) {
			try {
				params.put("zhengjianimg", file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		HttpUtils.doPost(UPLAODE_URL, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				System.out.println("-----------------------"+arg2);
				if ("1".equals(code)) {
					try {
						JSONObject jsonObject = new JSONObject(arg2);
						String url = jsonObject.getString("zhengjianurl");
						map.put("yyzz", url);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if ("2".equals(code)) {
					Toast.makeText(context, "图片资源太大 !", Toast.LENGTH_SHORT)
							.show();

				} else if ("3".equals(code)) {
					Toast.makeText(context, "文件为空 !", Toast.LENGTH_SHORT)
							.show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(context, "上传失败!", Toast.LENGTH_SHORT).show();
				Log.e(TAG, arg2 == null ? "" : arg2);
			}

		});
	}

	public void upLoadeSwdjImage(String path) {
		RequestParams params = new RequestParams();
		File file = new File(path);
		if (file != null) {
			try {
				params.put("zhengjianimg", file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		HttpUtils.doPost(UPLAODE_URL, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("1".equals(code)) {
					try {
						JSONObject jsonObject = new JSONObject(arg2);
						String url = jsonObject.getString("zhengjianurl");
						map.put("swdj", url);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if ("2".equals(code)) {
					Toast.makeText(context, "图片资源太大 !", Toast.LENGTH_SHORT)
							.show();

				} else if ("3".equals(code)) {
					Toast.makeText(context, "文件为空 !", Toast.LENGTH_SHORT)
							.show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(context, "上传失败!", Toast.LENGTH_SHORT).show();
				Log.e(TAG, arg2 == null ? "" : arg2);
			}

		});
	}

	public void upLoadeZzjgImage(String path) {
		RequestParams params = new RequestParams();
		File file = new File(path);
		if (file != null) {
			try {
				params.put("zhengjianimg", file);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		HttpUtils.doPost(UPLAODE_URL, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("1".equals(code)) {
					try {
						JSONObject jsonObject = new JSONObject(arg2);
						String url = jsonObject.getString("zhengjianurl");
						map.put("zzjg", url);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if ("2".equals(code)) {
					Toast.makeText(context, "图片资源太大 !", Toast.LENGTH_SHORT)
							.show();

				} else if ("3".equals(code)) {
					Toast.makeText(context, "文件为空 !", Toast.LENGTH_SHORT)
							.show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(context, "上传失败!", Toast.LENGTH_SHORT).show();
				Log.e(TAG, arg2 == null ? "" : arg2);
			}

		});
	}

	public void doAddSubmit() {
		String edt_gsjcs = edt_gsjc.getText().toString().trim();
		String edt_gongsinames = edt_gongsiname.getText().toString().trim();
		String edt_gongsimiaoshus = edt_gongsimiaoshu.getText().toString()
				.trim();
		String edt_xxdzs = edt_xxdz.getText().toString().trim();
		String edt_gsdhs = edt_gsdh.getText().toString().trim();
		String edt_gsczs = edt_gscz.getText().toString().trim();
		String edt_lxrs = edt_lxr.getText().toString().trim();
		String edt_lxdhs = edt_lxdh.getText().toString().trim();
		if (TextUtils.isEmpty(edt_gongsinames)
				|| TextUtils.isEmpty(edt_gongsimiaoshus)
				|| TextUtils.isEmpty(edt_xxdzs) || TextUtils.isEmpty(edt_gsdhs)
				|| TextUtils.isEmpty(edt_gsczs) || TextUtils.isEmpty(edt_lxrs)
				|| TextUtils.isEmpty(edt_lxdhs) || TextUtils.isEmpty(edt_gsjcs)) {
			Toast.makeText(context, "请完善公司信息！", Toast.LENGTH_SHORT).show();
			return;

		}

		int provinceIdex = spinner_province.getSelectedItemPosition();
		int cityIndex = spinner_city.getSelectedItemPosition();
		int areaIndex = spinner_area.getSelectedItemPosition();
		if (provinceIdex == 0 || cityIndex == 0 || areaIndex == 0) {
			Toast.makeText(context, "请选择所属区域！", Toast.LENGTH_SHORT).show();
			return;
		}
		if (map.size() != 3) {
			Toast.makeText(context, "证件照片地址不完善！", Toast.LENGTH_SHORT).show();
			return;
		}
		String provinceid = provinces.get(provinceIdex).getId();
		String cityid = citys.get(cityIndex).getId();
		String areaid = areas.get(areaIndex).getId();
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("gongsiname", edt_gongsinames);
		params.put("jiancheng", edt_gsjcs);
		params.put("miaoshu", edt_gongsimiaoshus);
		params.put("provinceid", provinceid);
		params.put("cityid", cityid);
		params.put("areaid", areaid);
		params.put("dizhi", edt_xxdzs);
		params.put("dianhua", edt_gsdhs);
		params.put("chuanzhen", edt_gsczs);
		params.put("lianxiren", edt_lxrs);
		params.put("lianxidianhua", edt_lxdhs);
		params.put("yingyezhizhao", map.get("yyzz"));
		params.put("shuiwudengjizheng", map.get("swdj"));
		params.put("zuzhijigoudaimazheng", map.get("zzjg"));

		HttpUtils.doPost(ADD_URL, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(context, "公司信息填写不完整！", Toast.LENGTH_SHORT)
							.show();
				} else if ("1".equals(code)) {
					try {
						JSONObject jsonObject = new JSONObject(arg2);
						String gongsiid = jsonObject.getString("gongsiid");
						dao.updateCompanyId(userid, gongsiid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if ("2".equals(code)) {
					Toast.makeText(context, "该公司名称已被使用！", Toast.LENGTH_SHORT)
							.show();
				} else if ("3".equals(code)) {
					Toast.makeText(context, "该公司简称已被使用！", Toast.LENGTH_SHORT)
							.show();
				} else if ("4".equals(code)) {
					Toast.makeText(context, "添加失败！", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(context, "修改失败!", Toast.LENGTH_SHORT).show();
				Log.e(TAG, arg2 == null ? "" : arg2);
			}
		});

	}


}
