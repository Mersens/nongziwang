package com.nongziwang.fragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.FbxjdActivity;
import com.nongziwang.activity.GongsiFragmentActivity;
import com.nongziwang.activity.LoginActivity;
import com.nongziwang.activity.MyAccountBuyerFragmentActivity;
import com.nongziwang.activity.MyFootprintActivity;
import com.nongziwang.activity.MyShopsFragmentActivity;
import com.nongziwang.activity.ProductManagementFragmentActivity;
import com.nongziwang.activity.ReleaseProductFragmentActivity;
import com.nongziwang.activity.SettingActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.UserBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.ImageLoadOptions;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.PhotoUtil;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.view.CircleImageView;
import com.nongziwang.view.DialogTips;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @title BuyerFragment
 * @description:Mersens
 * @author Mersens
 * @time 2016年1月22日
 */
public class BuyerFragment extends BaseFragment implements OnClickListener {
	private View view;
	private TextView tv_login, tv_name;
	private CircleImageView buyer_user_head;
	private RelativeLayout layout_fbxjd;
	private RelativeLayout layout_myfootprint;
	private RelativeLayout layout_setting;
	private RelativeLayout layout_myaccount;
	private RelativeLayout layout_rele_pro;
	private RelativeLayout layout_pro;
	private RelativeLayout layout_ckdp;
	private RelativeLayout layout_gsgl;
	public static final int FROM_XC = 0X00;
	public static final int FROM_CJ = 0X01;
	public static final String TAG = "BuyerFragment";
	public String path;
	private static final String HEADURL = AppConstants.SERVICE_ADDRESS
			+ "userinfo/gotoUpHeadImg";
	private NongziDao dao;
	private String userid = null;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_buyer, null);
		dao = new NongziDaoImpl(getActivity().getApplicationContext());
		userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserId();

		initViews();
		initEvent();
		return view;
	}

	Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.arg1==200){
				initDatas();
			}
		};
	};
	
	private void initViews() {
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		buyer_user_head = (CircleImageView) view
				.findViewById(R.id.buyer_user_head);
		if (!TextUtils.isEmpty(userid)) {
			UserBean user = dao.findUserInfoById(userid);
			ImageLoader.getInstance().displayImage(user.getTouxiang(),
					buyer_user_head, ImageLoadOptions.getOptionsLoading());
		}
		layout_myaccount = (RelativeLayout) view
				.findViewById(R.id.layout_myaccount);
		layout_setting = (RelativeLayout) view
				.findViewById(R.id.layout_setting);

		layout_myfootprint = (RelativeLayout) view
				.findViewById(R.id.layout_myfootprint);
		tv_login = (TextView) view.findViewById(R.id.tv_login);
		layout_fbxjd = (RelativeLayout) view.findViewById(R.id.layout_fbxjd);
		layout_rele_pro = (RelativeLayout) view
				.findViewById(R.id.layout_rele_pro);
		layout_pro = (RelativeLayout) view.findViewById(R.id.layout_pro);
		layout_ckdp = (RelativeLayout) view.findViewById(R.id.layout_ckdp);
		layout_gsgl = (RelativeLayout) view.findViewById(R.id.layout_gsgl);
	}

	public void initDatas() {
		userid = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserId();
		if (!TextUtils.isEmpty(userid)) {
			UserBean user = dao.findUserInfoById(userid);
			if (null != user) {
				tv_name.setText(user.getUsername());
				tv_name.setVisibility(View.VISIBLE);
				tv_login.setVisibility(View.GONE);
			}
		} else {
			tv_name.setVisibility(View.GONE);
			tv_login.setVisibility(View.VISIBLE);
		}
	}

	private void initEvent() {
		buyer_user_head.setOnClickListener(this);
		layout_myaccount.setOnClickListener(this);
		layout_setting.setOnClickListener(this);
		layout_myfootprint.setOnClickListener(this);
		layout_fbxjd.setOnClickListener(this);
		tv_login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentAction(getActivity(), LoginActivity.class);
			}
		});
		layout_rele_pro.setOnClickListener(this);
		layout_pro.setOnClickListener(this);
		layout_ckdp.setOnClickListener(this);
		layout_gsgl.setOnClickListener(this);

	}

	public static Fragment getInstance(String params) {
		BuyerFragment fragment = new BuyerFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	protected void lazyLoad() {
	}

	@Override
	public void onClick(View v) {
		if (TextUtils.isEmpty(userid)) {
			DialogTips dialog = new DialogTips(getActivity(), "温馨提示",
					"您未登录,是否选择登录?", "确定", true, true);
			dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialogInterface, int userId) {
					intentAction(getActivity(), LoginActivity.class);
				}
			});
			dialog.show();
			dialog = null;
		} else {
			doSelect(v.getId());
		}

	}

	public void doSelect(int id) {
		switch (id) {
		case R.id.buyer_user_head:
			showAvatarPop();
			break;
		case R.id.layout_gsgl:
			intentAction(getActivity(), GongsiFragmentActivity.class);
			break;
		case R.id.layout_fbxjd:
			intentAction(getActivity(), FbxjdActivity.class);
			break;
		case R.id.layout_rele_pro:
			UserBean user = dao.findUserInfoById(userid);
			if (null != user) {
				if (!TextUtils.isEmpty(user.getCompanyid())) {
					intentAction(getActivity(),
							ReleaseProductFragmentActivity.class);
				} else {
					DialogTips dialog = new DialogTips(getActivity(), "温馨提示",
							"您未开通公司,是否选择开通?", "确定", true, true);
					dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialogInterface,
								int userId) {
							intentAction(getActivity(),
									GongsiFragmentActivity.class);
						}
					});
					dialog.show();
					dialog = null;
				}

			}

			break;
		case R.id.layout_pro:
			intentAction(getActivity(), ProductManagementFragmentActivity.class);
			break;
		case R.id.layout_ckdp:
			intentAction(getActivity(), MyShopsFragmentActivity.class);
			break;
		case R.id.layout_myfootprint:
			intentAction(getActivity(), MyFootprintActivity.class);
			break;
		case R.id.layout_myaccount:
			intentAction(getActivity(), MyAccountBuyerFragmentActivity.class);
			break;
		case R.id.layout_setting:
			intentAction(getActivity(), SettingActivity.class);
			break;
		}
	}

	public void showAvatarPop() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, FROM_XC);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case FROM_XC:
			Uri uri = null;
			if (data == null) {
				return;
			}
			if (resultCode == getActivity().RESULT_OK) {
				if (!Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					Log.e(TAG, "SD不可用");
					return;
				}
				uri = data.getData();
				startImageAction(uri, 200, 200, FROM_CJ, true);
			} else {
				Log.e(TAG, "照片获取失败");
			}
			break;
		case FROM_CJ:
			if (data == null) {
				return;
			} else {
				saveCropAvator(data);
			}
			break;

		}
	}

	private void startImageAction(Uri uri, int outputX, int outputY,
			int requestCode, boolean isCrop) {
		Intent intent = null;
		if (isCrop) {
			intent = new Intent("com.android.camera.action.CROP");
		} else {
			intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		}
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", true);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}

	/**
	 * 保存裁剪的头像
	 * 
	 * @param data
	 */
	@SuppressLint("SimpleDateFormat")
	private void saveCropAvator(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap bitmap = extras.getParcelable("data");
			if (bitmap != null) {
				bitmap = PhotoUtil.toRoundCorner(bitmap, 10);
				buyer_user_head.setImageBitmap(bitmap);
				// 保存图片
				String filename = new SimpleDateFormat("yyMMddHHmmss")
						.format(new Date()) + ".png";
				path = AppConstants.MyAvatarDir + filename;
				PhotoUtil.saveBitmap(AppConstants.MyAvatarDir, filename,
						bitmap, true);
				// 上传头像
				uploadAvatar(path);
				if (bitmap != null && bitmap.isRecycled()) {
					bitmap.recycle();
				}
			}
		}
	}

	public void uploadAvatar(String path) {
		File file = new File(path);
		String id = SharePreferenceUtil.getInstance(
				getActivity().getApplicationContext()).getUserId();
		RequestParams params = new RequestParams();
		params.put("userid", id);
		try {
			params.put("touxiang", file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		HttpUtils.doPost(HEADURL, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					Toast.makeText(getActivity(), "用户不存在！", Toast.LENGTH_LONG)
							.show();
				} else if ("1".equals(code)) {
					try {
						JSONObject jsonObject = new JSONObject(arg2);
						String path = jsonObject.getString("touxiang");
						dao.updateUserHeadById(userid, path);

						Toast.makeText(getActivity(), "上传成功！",
								Toast.LENGTH_LONG).show();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if ("2".equals(code)) {
					Toast.makeText(getActivity(), "资源太大！", Toast.LENGTH_LONG)
							.show();
				} else if ("3".equals(code)) {
					Toast.makeText(getActivity(), "文件为空！", Toast.LENGTH_LONG)
							.show();
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				Toast.makeText(getActivity(), "图片上传失败！", Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	@Override
	public void onStart() {
		Message msg=new Message();
		msg.arg1=200;
		handler.sendMessage(msg);
		super.onStart();
		
	
	}

}
