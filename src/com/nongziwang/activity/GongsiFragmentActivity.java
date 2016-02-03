package com.nongziwang.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.MyArrayAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.GongsiBean;
import com.nongziwang.entity.MyRegion;
import com.nongziwang.entity.UserBean;
import com.nongziwang.fragment.SellerAccountVipFragment;
import com.nongziwang.main.R;
import com.nongziwang.utils.BitmapUtils;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.ImageLoadOptions;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.ListUtils;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.view.DialogTips;
import com.nongziwang.view.DialogWaiting;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.ProcessImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class GongsiFragmentActivity extends BaseActivity implements
OnClickListener{
	private EditText edt_gongsiname, edt_gongsimiaoshu, edt_xxdz, edt_gsdh,
			edt_gscz, edt_lxr, edt_lxdh, edt_gsjc;
	private Spinner spinner_province, spinner_city, spinner_area;
	private List<MyRegion> provinces;
	private List<MyRegion> citys;
	private List<MyRegion> areas;
	private MyArrayAdapter provincesAdapter, citysAdapter, areasAdapter;
	private NongziDao dao;
	private ProcessImageView image_yyzz, image_swdj, image_zzjg;
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
	private static final String UPDATE_URL = AppConstants.SERVICE_ADDRESS
			+ "gongsi/updateGongsiData";
	private static final String TAG = "SellerAccountVipFragment";
	private String userid;
	private GongsiBean gongsibean;
	private RelativeLayout layout_loading;
	private boolean isUpdate=false;
	private DialogWaiting dialog;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_mycompany);
		dao = new NongziDaoImpl(this);
		userid = SharePreferenceUtil.getInstance(
				getApplicationContext()).getUserId();
		context = this;
		initViews();
		initEvent();
		initDatas();
	}


	private void initViews() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "公司管理",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
		layout_loading=(RelativeLayout) findViewById(R.id.layout_loading);
		layout_loading.setVisibility(View.GONE);
		edt_gsjc = (EditText) findViewById(R.id.edt_gsjc);
		edt_gongsiname = (EditText)findViewById(R.id.edt_gongsiname);
		edt_gongsimiaoshu = (EditText) 
				findViewById(R.id.edt_gongsimiaoshu);
		edt_xxdz = (EditText) findViewById(R.id.edt_xxdz);
		edt_gsdh = (EditText) findViewById(R.id.edt_gsdh);
		edt_gscz = (EditText) findViewById(R.id.edt_gscz);
		edt_lxr = (EditText)findViewById(R.id.edt_lxr);
		edt_lxdh = (EditText) findViewById(R.id.edt_lxdh);
		image_zzjg = (ProcessImageView) findViewById(R.id.image_zzjg);
		image_yyzz = (ProcessImageView) findViewById(R.id.image_yyzz);
		image_swdj = (ProcessImageView) findViewById(R.id.image_swdj);
		image_yyzz.isProgress(false);
		image_swdj.isProgress(false);
		image_zzjg.isProgress(false);
		spinner_province = (Spinner) findViewById(R.id.spinner_province);
		spinner_city = (Spinner) findViewById(R.id.spinner_city);
		spinner_area = (Spinner) findViewById(R.id.spinner_area);
		btn_submit = (Button) findViewById(R.id.btn_submit);

	}

	private void initEvent() {
		provinces = dao.findAllProvinces();
		citys = new ArrayList<MyRegion>();
		citys.add(new MyRegion("1", "请选择", null));
		areas = new ArrayList<MyRegion>();
		areas.add(new MyRegion("1", "请选择", null));

		provincesAdapter = new MyArrayAdapter(context,
				android.R.layout.simple_spinner_item, provinces);
		provincesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		citysAdapter = new MyArrayAdapter(context,
				android.R.layout.simple_spinner_item, citys);
		citysAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		areasAdapter = new MyArrayAdapter(context,
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

	}

	private void initDatas() {
		UserBean user = dao.findUserInfoById(userid);
		if (!TextUtils.isEmpty(user.getCompanyid())) {
			isUpdate=true;
			doGetDatas(user.getCompanyid());
			btn_submit.setText("修改");
		}else{
			isUpdate=false;
		}
	}

	private void doGetDatas(String gongsiid) {
		RequestParams params = new RequestParams();
		params.put("gongsiid", gongsiid);

		HttpUtils.doPost(GONGSIDATA_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
				layout_loading.setVisibility(View.VISIBLE);
			}
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
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				layout_loading.setVisibility(View.GONE);
			}
		});
	}

	public void setDatas() {
		edt_gongsiname.setText(gongsibean.getGongsiname());
		edt_gongsiname.setOnKeyListener(null);
		edt_gongsiname.setEnabled(false);
		edt_gongsimiaoshu.setText(gongsibean.getMiaoshu());
		edt_xxdz.setText(gongsibean.getDizhi());
		edt_gsdh.setText(gongsibean.getDianhua());
		edt_gscz.setText(gongsibean.getChuanzhen());
		edt_lxr.setText(gongsibean.getLianxiren());
		edt_lxdh.setText(gongsibean.getLianxidianhua());
		edt_gsjc.setText(gongsibean.getJiancheng());

		ImageLoader.getInstance().displayImage(gongsibean.getYingyezhizhao(),
				image_yyzz, ImageLoadOptions.getOptions());
		ImageLoader.getInstance().displayImage(
				gongsibean.getShuiwudengjizheng(), image_swdj,
				ImageLoadOptions.getOptions());
		ImageLoader.getInstance().displayImage(
				gongsibean.getZuzhijigoudaimazheng(), image_zzjg,
				ImageLoadOptions.getOptions());
		initProvinceIndex = ListUtils.getInitIndex(provinces, gongsibean.getProvince());
		if(initProvinceIndex!=0){
			spinner_province.setSelection(initProvinceIndex, true);
		}
		List<MyRegion> list=dao.findAllCitysByProvincesId(provinces.get(initProvinceIndex).getId());
		citys.clear();
		citys.addAll(list);
		initCityIndex= ListUtils.getInitIndex(citys, gongsibean.getCity());
		if(initCityIndex!=0){
			spinner_city.setSelection(initCityIndex, true);
		}
		List<MyRegion> list1=dao.findAllAreasByCityId(citys.get(initCityIndex).getId());
		areas.clear();
		areas.addAll(list1);
		initAreaIndex = ListUtils.getInitIndex(areas, gongsibean.getArea());
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
			doSubmit();
			break;
		}
	}
	
	private boolean isYyzz = false;
	private boolean isSwdj = false;
	private boolean isZzjg = false;

	private boolean isYyzzSuccess = false;
	private boolean isSwdjSuccess = false;
	private boolean isZzjgSuccess = false;
	public void selectImage() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent,1);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && data!=null) {
			Cursor cursor = getContentResolver().query(data.getData(),
					null, null, null, null);
			cursor.moveToFirst();
			int dex = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			String path = cursor.getString(dex);
			bitmap = BitmapUtils.getBitMap(path);
			cursor.close();
			if (bitmap != null) {
				if (isYyzz) {
					image_yyzz.setImageBitmap(bitmap);
					image_yyzz.isProgress(true);
					upLoadeYyzzImage(path);
					isYyzz = false;
				}
				if (isSwdj) {
					image_swdj.setImageBitmap(bitmap);
					image_swdj.isProgress(true);
					upLoadeSwdjImage(path);
					isSwdj = false;
				}
				if (isZzjg) {
					image_zzjg.setImageBitmap(bitmap);
					image_zzjg.isProgress(true);
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
				if ("1".equals(code)) {
					try {

						JSONObject jsonObject = new JSONObject(arg2);
						String url = jsonObject.getString("zhengjianurl");
						isYyzzSuccess=true;
						map.put("yyzz", url);
					} catch (JSONException e) {
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
			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);  
				image_yyzz.setProgress(count);
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
						isSwdjSuccess=true;
						map.put("swdj", url);
					} catch (JSONException e) {
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

			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);  
				image_swdj.setProgress(count);
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
					    isZzjgSuccess = true;
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
			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				super.onProgress(bytesWritten, totalSize);
				int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);  
				image_zzjg.setProgress(count);
			}
		});
	}

	public void doSubmit() {
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

		if(!isYyzzSuccess || !isSwdjSuccess || !isZzjgSuccess){
			Toast.makeText(context, "证件照片上传未完成！", Toast.LENGTH_SHORT).show();
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

		if(isUpdate){
			params.put("gongsiid", gongsibean.getGongsiid());
			doUpdate(params);
		}else{
			doAdd(params);	
		}

	}
	
	public void doAdd(RequestParams params){
		HttpUtils.doPost(ADD_URL, params, new TextHttpResponseHandler() {
			public void onStart() {
				dialog =new DialogWaiting(GongsiFragmentActivity.this);
				dialog.show();
				super.onStart();
			}
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
						DialogTips dialog = new DialogTips(GongsiFragmentActivity.this, "添加成功！", "确定");
						dialog.show();
						dialog = null;
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
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				dialogDismiss();
			}
		});
	}
	
	public void doUpdate(RequestParams params){
		HttpUtils.doPost(UPDATE_URL, params, new TextHttpResponseHandler() {
			public void onStart() {
				dialog =new DialogWaiting(GongsiFragmentActivity.this);
				dialog.show();
				super.onStart();
			}
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
						DialogTips dialog = new DialogTips(GongsiFragmentActivity.this, "修改成功！", "确定");
						dialog.show();
						dialog = null;
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else if ("2".equals(code)) {
					Toast.makeText(context, "找不到该公司信息！", Toast.LENGTH_SHORT)
							.show();
				} else if ("3".equals(code)) {
					Toast.makeText(context, "该公司名称已被使用！", Toast.LENGTH_SHORT)
							.show();
				} else if ("4".equals(code)) {
					Toast.makeText(context, "该公司简称已被使用！", Toast.LENGTH_SHORT).show();
				}
				else if ("5".equals(code)) {
					Toast.makeText(context, "修改失败！", Toast.LENGTH_SHORT).show();
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Toast.makeText(context, "修改失败!", Toast.LENGTH_SHORT).show();
				Log.e(TAG, arg2 == null ? "" : arg2);
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				dialogDismiss();
			}
		});
	
	}
	
	public void dialogDismiss(){
		if(dialog!=null && dialog.isShowing()){
			dialog.dismiss();
		}
		
	}
}
