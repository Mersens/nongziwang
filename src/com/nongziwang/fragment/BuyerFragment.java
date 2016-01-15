package com.nongziwang.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nongziwang.activity.CartActivity;
import com.nongziwang.activity.CommonOrderFragmentActivity;
import com.nongziwang.activity.FbxjdActivity;
import com.nongziwang.activity.LoginActivity;
import com.nongziwang.activity.MyAccountBuyerFragmentActivity;
import com.nongziwang.activity.MyAddressFragmentActivity;
import com.nongziwang.activity.MyCollectionFragmentActivity;
import com.nongziwang.activity.MyFootprintActivity;
import com.nongziwang.activity.SettingActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.UserBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.PhotoUtil;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.view.CircleImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

/**
 * 
 * @author Mersens 买家
 */
public class BuyerFragment extends BaseFragment implements OnClickListener {
	private View view;
	private TextView tv_buyer_switch;
	private TextView tv_login, tv_name;
	private CircleImageView buyer_user_head;
	private RelativeLayout layout_ymdcp;
	private RelativeLayout layout_dqr;
	private RelativeLayout layout_dfk;
	private RelativeLayout layout_dsh;
	private RelativeLayout layout_jycg;
	private RelativeLayout layout_cart;
	private RelativeLayout layout_fbxjd;
	private RelativeLayout layout_mycollection;
	private RelativeLayout layout_myfootprint;
	private RelativeLayout layout_myaddress;
	private RelativeLayout layout_setting;
	private RelativeLayout layout_myaccount;
	public static final int FROM_XC = 0X00;
	public static final int FROM_CJ = 0X01;
	public static final String TAG = "BuyerFragment";
	public String path;
	private NongziDao dao;
	private String userid = null;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_buyer, null);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		buyer_user_head = (CircleImageView) view
				.findViewById(R.id.buyer_user_head);
		layout_myaccount = (RelativeLayout) view
				.findViewById(R.id.layout_myaccount);
		layout_setting = (RelativeLayout) view
				.findViewById(R.id.layout_setting);
		layout_myaddress = (RelativeLayout) view
				.findViewById(R.id.layout_myaddress);
		layout_myfootprint = (RelativeLayout) view
				.findViewById(R.id.layout_myfootprint);
		layout_mycollection = (RelativeLayout) view
				.findViewById(R.id.layout_mycollection);
		tv_buyer_switch = (TextView) view.findViewById(R.id.tv_buyer_switch);
		tv_login = (TextView) view.findViewById(R.id.tv_login);
		layout_ymdcp = (RelativeLayout) view.findViewById(R.id.layout_ymdcp);
		layout_dqr = (RelativeLayout) view.findViewById(R.id.layout_dqr);
		layout_dfk = (RelativeLayout) view.findViewById(R.id.layout_dfk);
		layout_dsh = (RelativeLayout) view.findViewById(R.id.layout_dsh);
		layout_jycg = (RelativeLayout) view.findViewById(R.id.layout_jycg);
		layout_cart = (RelativeLayout) view.findViewById(R.id.layout_cart);
		layout_fbxjd = (RelativeLayout) view.findViewById(R.id.layout_fbxjd);
	}

	public void initDatas() {
		dao = new NongziDaoImpl(getActivity().getApplicationContext());
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
		layout_myaddress.setOnClickListener(this);
		layout_myfootprint.setOnClickListener(this);
		layout_mycollection.setOnClickListener(this);
		layout_fbxjd.setOnClickListener(this);
		tv_buyer_switch.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		layout_ymdcp.setOnClickListener(this);
		layout_dqr.setOnClickListener(this);
		layout_dfk.setOnClickListener(this);
		layout_dsh.setOnClickListener(this);
		layout_jycg.setOnClickListener(this);
		layout_cart.setOnClickListener(this);
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
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.tv_buyer_switch:
			Intent intent = new Intent();
			intent.setAction(UserCenterFragment.BROADCAST_ACTION_SELL);
			getActivity().sendBroadcast(intent);
			break;
		case R.id.buyer_user_head:
			showAvatarPop();
			break;
		case R.id.tv_login:
			intentAction(getActivity(), LoginActivity.class);
			break;
		case R.id.layout_ymdcp:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.YMDCP);
			break;
		case R.id.layout_dqr:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.DQR);
			break;
		case R.id.layout_dfk:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.DFK);
			break;
		case R.id.layout_dsh:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.DSH);
			break;
		case R.id.layout_jycg:
			intentAction(getActivity(), CommonOrderFragmentActivity.class,
					CommonOrderFragmentActivity.JYCG);
			break;
		case R.id.layout_cart:
			intentAction(getActivity(), CartActivity.class);
			break;
		case R.id.layout_fbxjd:
			intentAction(getActivity(), FbxjdActivity.class);
			break;
		case R.id.layout_mycollection:
			intentAction(getActivity(), MyCollectionFragmentActivity.class);
			break;
		case R.id.layout_myfootprint:
			intentAction(getActivity(), MyFootprintActivity.class);
			break;
		case R.id.layout_myaddress:
			intentAction(getActivity(), MyAddressFragmentActivity.class);
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
		// 上传头像
	}

	@Override
	public void onStart() {
		initDatas();
		super.onStart();
	}
}
