package com.nongziwang.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.nongziwang.activity.ResetPhoneNumberFragmentActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.utils.PhotoUtil;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountVipFragment extends BaseFragment{
	private View view;
	private ImageView img_user_head;
	private EditText edt_name,edt_qq;
	private TextView tv_update;
	private Button btn_ok;
	private String path;
	public static final int FROM_XC=0X00;
	public static final int FROM_CJ=0X01;
	public static final String TAG="AccountVipFragment";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.layout_account_vip, container,false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		img_user_head=(ImageView) view.findViewById(R.id.img_user_head);
		edt_name=(EditText) view.findViewById(R.id.edt_name);
		edt_qq=(EditText) view.findViewById(R.id.edt_qq);
		tv_update=(TextView) view.findViewById(R.id.tv_update);
		btn_ok=(Button) view.findViewById(R.id.btn_ok);
	}


	private void initEvent() {
		img_user_head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showAvatarPop();
			}
		});
		tv_update.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intentAction(getActivity(),ResetPhoneNumberFragmentActivity.class);
				
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				doSave();
			}
		});
		
	}

	public void doSave(){

	}
	
	public static Fragment getInstance(String params) {
		AccountVipFragment fragment = new AccountVipFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

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
				img_user_head.setImageBitmap(bitmap);
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
	
	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub
		
	}

}
