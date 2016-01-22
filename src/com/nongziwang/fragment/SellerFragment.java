package com.nongziwang.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nongziwang.activity.CommonOrderFragmentActivity;
import com.nongziwang.activity.JyzwActivity;
import com.nongziwang.activity.LoginActivity;
import com.nongziwang.activity.MyAccountSellerFragmentActivity;
import com.nongziwang.activity.MyShopsFragmentActivity;
import com.nongziwang.activity.ProductManagementFragmentActivity;
import com.nongziwang.activity.ReleaseProductFragmentActivity;
import com.nongziwang.activity.SettingActivity;
import com.nongziwang.activity.TqhkActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.utils.PhotoUtil;
import com.nongziwang.view.CircleImageView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 
 * @author Mersens 卖家
 */
public class SellerFragment extends BaseFragment implements OnClickListener{
	private View view;
	private TextView tv_seller_switch;
	private TextView tv_my_dianpu;
	private CircleImageView seller_user_head;
	private TextView tv_login;
	
	private RelativeLayout layout_ymdcp;
	private RelativeLayout layout_dqr;
	private RelativeLayout layout_dfk;
	private RelativeLayout layout_yfh;
	private RelativeLayout layout_jycg;
	private RelativeLayout layout_jyzw;
	private RelativeLayout layout_tqck;
	private RelativeLayout layout_myaccount;
	private RelativeLayout layout_setting;
	private RelativeLayout layout_pro;
	private RelativeLayout layout_rele_pro;
	private RelativeLayout layout_ckdp;
	public static final int FROM_XC=0X00;
	public static final int FROM_CJ=0X01;
	private String path;
	public static final String TAG="SellerFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.activity_seller, null);
		initViews();
		initEvent();
		return view;
	}
	private void initViews() {
		layout_ckdp=(RelativeLayout) view.findViewById(R.id.layout_ckdp);
		layout_rele_pro=(RelativeLayout) view.findViewById(R.id.layout_rele_pro);
		layout_pro=(RelativeLayout) view.findViewById(R.id.layout_pro);
		layout_myaccount=(RelativeLayout) view.findViewById(R.id.layout_myaccount);
		layout_setting=(RelativeLayout) view.findViewById(R.id.layout_setting);
		layout_tqck=(RelativeLayout) view.findViewById(R.id.layout_tqck);
		layout_jyzw=(RelativeLayout) view.findViewById(R.id.layout_jyzw);
		tv_seller_switch=(TextView) view.findViewById(R.id.tv_seller_switch);
		tv_my_dianpu=(TextView) view.findViewById(R.id.tv_my_dianpu);
		seller_user_head=(CircleImageView) view.findViewById(R.id.seller_user_head);
		tv_login=(TextView) view.findViewById(R.id.tv_login);
		layout_ymdcp = (RelativeLayout) view.findViewById(R.id.layout_ymdcp);
		layout_dqr = (RelativeLayout) view.findViewById(R.id.layout_dqr);
		layout_dfk = (RelativeLayout) view.findViewById(R.id.layout_dfk);
		layout_yfh = (RelativeLayout) view.findViewById(R.id.layout_yfh);
		layout_jycg = (RelativeLayout) view.findViewById(R.id.layout_jycg);
	}
	private void initEvent() {
		layout_ckdp.setOnClickListener(this);
		layout_rele_pro.setOnClickListener(this);
		layout_pro.setOnClickListener(this);
		layout_myaccount.setOnClickListener(this);
		layout_setting.setOnClickListener(this);
		layout_tqck.setOnClickListener(this);
		layout_jyzw.setOnClickListener(this);
		tv_seller_switch.setOnClickListener(this);
		tv_my_dianpu.setOnClickListener(this);
		seller_user_head.setOnClickListener(this);
		tv_login.setOnClickListener(this);
		layout_ymdcp.setOnClickListener(this);
		layout_dqr.setOnClickListener(this);
		layout_dfk.setOnClickListener(this);
		layout_yfh.setOnClickListener(this);
		layout_jycg.setOnClickListener(this);
	}

	public static Fragment getInstance(String params) {
		SellerFragment fragment = new SellerFragment();
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
		switch (v.getId()) {
		case R.id.tv_seller_switch:
			Intent intent = new Intent();
			intent.setAction(UserCenterFragment.BROADCAST_ACTION_BUY);
			getActivity().sendBroadcast(intent);	
			break;
		case R.id.tv_my_dianpu:
			intentAction(getActivity(), MyShopsFragmentActivity.class);
			break;
		case R.id.seller_user_head:
			showAvatarPop();
			break;
		case R.id.layout_ckdp:
			intentAction(getActivity(), MyShopsFragmentActivity.class);
			break;
		case R.id.tv_login:
			intentAction(getActivity(), LoginActivity.class);
			break;

		case R.id.layout_tqck:
			intentAction(getActivity(),TqhkActivity.class);
			break;
		case R.id.layout_jyzw:
			intentAction(getActivity(),JyzwActivity.class);
			break;
		case R.id.layout_myaccount:
			intentAction(getActivity(),MyAccountSellerFragmentActivity.class);
			break;
		case R.id.layout_setting:
			intentAction(getActivity(),SettingActivity.class);
			break;
		case R.id.layout_pro:
			intentAction(getActivity(),ProductManagementFragmentActivity.class);
			break;
		case R.id.layout_rele_pro:
			intentAction(getActivity(),ReleaseProductFragmentActivity.class);
			break;
		}
		
	}
	public void showAvatarPop(){
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent,FROM_XC);
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
				startImageAction(uri, 200, 200,
						FROM_CJ, true);
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
				seller_user_head.setImageBitmap(bitmap);
				// 保存图片
				String filename = new SimpleDateFormat("yyMMddHHmmss")
						.format(new Date())+".png";
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
		//上传头像
	}
}
