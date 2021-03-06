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
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.ProductManagementFragmentActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.UserBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.BitmapUtils;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.utils.ToastUtils;
import com.nongziwang.view.DialogTips;
import com.nongziwang.view.DialogWaiting;
import com.nongziwang.view.FlowLayout;
import com.nongziwang.view.ProcessImageView;

public class ReleaseProductMsgFragment extends BaseFragment {
	private View view;
	private Button btn_next;
	private String param;
	private EditText edt_title, edt_gjz, edt_yt, edt_cf, edt_pp, edt_ms,edt_jg,
			edt_xq,edt_dw;
	private FlowLayout flowlayout;
	private MarginLayoutParams lp;
	private int index = 0;
	private List<ProcessImageView> imageviewlist = new ArrayList<ProcessImageView>();
	public static final String UPLAODE_URL = AppConstants.SERVICE_ADDRESS
			+ "chanpin/gotoUpChanpinImg";
	public static final String URL = AppConstants.SERVICE_ADDRESS
			+ "chanpin/gotoAddChanpinData";
	private Map<String, String> map = new HashMap<String, String>();
	public static final String TAG = "ReleaseProductMsgFragment";
	private String userid;
	private StringBuffer sbf;
	private UserBean user;
	private NongziDao dao;
	private DialogWaiting dialog;
	private ImageView img_add;
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_rele_pro_msg, null);
		param = getArguments().getString("params");
		context=view.getContext();
		dao = new NongziDaoImpl(context);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		edt_dw=(EditText) view.findViewById(R.id.edt_dw);
		img_add=(ImageView) view.findViewById(R.id.img_add);
		edt_jg=(EditText) view.findViewById(R.id.edt_jg);
		edt_xq = (EditText) view.findViewById(R.id.edt_xq);
		flowlayout = (FlowLayout) view.findViewById(R.id.flowlayout);
		edt_title = (EditText) view.findViewById(R.id.edt_title);
		edt_gjz = (EditText) view.findViewById(R.id.edt_gjz);
		edt_yt = (EditText) view.findViewById(R.id.edt_yt);
		edt_cf = (EditText) view.findViewById(R.id.edt_cf);
		edt_pp = (EditText) view.findViewById(R.id.edt_pp);
		edt_ms = (EditText) view.findViewById(R.id.edt_ms);
		btn_next = (Button) view.findViewById(R.id.btn_next);
		lp = new MarginLayoutParams(new LayoutParams(160, 160));
		lp.bottomMargin = 5;
		lp.leftMargin = 10;
		lp.rightMargin = 10;
		lp.topMargin = 5;
		img_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (index > 7) {
					return;
				}
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 1);				
			}
		});
	}

	private void initEvent() {
		userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserId();
		user = dao.findUserInfoById(userid);
		sbf = new StringBuffer();
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doFinish();
			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && data != null) {
			Cursor cursor = getActivity().getContentResolver().query(
					data.getData(), null, null, null, null);
			cursor.moveToFirst();
			int dex = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			String path = cursor.getString(dex);
			Bitmap bitmap =BitmapUtils.getBitMap(path);
			cursor.close();
			upLoadeImag(path);
			setImage(bitmap);
		}

	}

	private void upLoadeImag(String path) {
		RequestParams params = new RequestParams();
		File file = new File(path);
		if (file != null) {
			try {
				params.put("chanpinimg", file);
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
						String url = jsonObject.getString("chanpinimg");
						map.put(url, url);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else if ("2".equals(code)) {
					ToastUtils.showMessage(context, "图片资源太大 !");
				} else if ("3".equals(code)) {
					ToastUtils.showMessage(context, "文件为空 !");
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				ToastUtils.showMessage(context, "上传失败!");
				Log.e(TAG, arg2 == null ? "" : arg2);
			}

			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
				imageviewlist.get(index - 1).setProgress(count);
			}
		});
	}

	private void setImage(Bitmap bitmap) {
		if (bitmap != null) {
			ProcessImageView imageview = new ProcessImageView(context);
			imageview.setScaleType(ScaleType.FIT_XY);
			imageview.setImageBitmap(bitmap);
			flowlayout.addView(imageview, lp);
			imageviewlist.add(imageview);
			index++;
		}
	}

	public static Fragment getInstance(String params) {
		ReleaseProductMsgFragment fragment = new ReleaseProductMsgFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	public void doFinish() {
		String dw=edt_dw.getText().toString().replaceAll(" ", "");
		String jg=edt_jg.getText().toString().replaceAll(" ", "");
		String xq = edt_xq.getText().toString().replaceAll(" ", "");
		String title = edt_title.getText().toString().replaceAll(" ", "");
		String gjz = edt_gjz.getText().toString().replaceAll(" ", "");
		String yt = edt_yt.getText().toString().replaceAll(" ", "");
		String cf = edt_cf.getText().toString().replaceAll(" ", "");
		String pp = edt_pp.getText().toString().replaceAll(" ", "");
		String ms = edt_ms.getText().toString().replaceAll(" ", "");
		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(gjz) || TextUtils.isEmpty(jg)
				|| TextUtils.isEmpty(yt) || TextUtils.isEmpty(cf) || TextUtils.isEmpty(dw)
				|| TextUtils.isEmpty(pp) || TextUtils.isEmpty(ms)
				|| TextUtils.isEmpty(xq)) {
			ToastUtils.showMessage(context, "请完善信息!");
			return;
		}

		if (!isSuitLen(title, 60)) {
			ToastUtils.showMessage(context, "商品标题内容过长!");
			return;
		}
		if (!isSuitLen(gjz, 30)) {
			ToastUtils.showMessage(context, "关键字内容过长!");
			return;
		}
		if (!isSuitLen(yt, 30)) {
			ToastUtils.showMessage(context, "主要用途内容过长!");
			return;
		}
		if (!isSuitLen(cf, 30)) {
			ToastUtils.showMessage(context, "主要成分内容过长!");
			return;
		}
		if (!isSuitLen(pp, 30)) {
			ToastUtils.showMessage(context, "品牌内容过长!");
			return;
		}
		if (!isSuitLen(ms, 200)) {
			ToastUtils.showMessage(context, "商品描述内容过长!");
			return;
		}
		if (!isSuitLen(xq, 200)) {
			ToastUtils.showMessage(context, "商品详情内容过长!");
			return;
		}
		if (map.size() == 0) {
			ToastUtils.showMessage(context, "请上传图片!");
			return;
		}
		if (TextUtils.isEmpty(param)) {
			ToastUtils.showMessage(context, "类目ID为空!");
			return;
		}
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sbf.append(entry.getValue() + ",");
		}
		String ids[] = param.split(":");
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("gongsiid", user.getCompanyid());
		params.put("title", title);
		if (ids.length == 2) {
			params.put("chanpinleimu2", ids[0]);
			params.put("chanpinleimu3", ids[1]);
		} else {
			params.put("chanpinleimu2", ids[0]);
			params.put("chanpinleimu3", "");
		}

		params.put("keyword", gjz);
		params.put("yongtuname", yt);
		params.put("chengfenname", cf);
		params.put("miaoshu", ms);
		params.put("pinpainame", pp);
		params.put("offerDetail", xq);
		params.put("jiage", jg);
		params.put("unit", dw);
		params.put("chanpinimgs", sbf.toString());
		doCompleat(params);
	}

	private void doCompleat(RequestParams params) {
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {

			@Override
			public void onStart() {
				super.onStart();
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog = new DialogWaiting(getActivity());
						dialog.show();
					}
				});

			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					ToastUtils.showMessage(context, "用户id为空！");
				} else if ("1".equals(code)) {
					DialogTips dialog = new DialogTips(getActivity(), "发布成功!",
							"确定");
					dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogInterface,
								int userId) {
							intentAction(getActivity(),
									ProductManagementFragmentActivity.class);
							getActivity().finish();
						}
					});
					dialog.show();
					dialog = null;
				} else if ("2".equals(code)) {
					ToastUtils.showMessage(context, "用户信息不存在！");
				} else if ("3".equals(code)) {
					ToastUtils.showMessage(context, "公司id为空");
				} else if ("4".equals(code)) {
					ToastUtils.showMessage(context, "公司信息不存在！");
				} else if ("5".equals(code)) {
					ToastUtils.showMessage(context, "产品信息填写不完整！");
				} else if ("6".equals(code)) {
					ToastUtils.showMessage(context, "产品分类选择不正确！");
				} else if ("7".equals(code)) {
					ToastUtils.showMessage(context, "产品分类不存在！");
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ToastUtils.showMessage(context, "添加失败！");
			}

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				dialog.dismiss();
			}
		});

	}

	public boolean isSuitLen(String str, int limit) {
		if (str.length() > limit) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	protected void lazyLoad() {

	}



}
