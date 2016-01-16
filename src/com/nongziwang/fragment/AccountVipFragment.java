package com.nongziwang.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.Header;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.ResetPhoneNumberFragmentActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.PhotoUtil;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.utils.StringUtils;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AccountVipFragment extends BaseFragment {
	private View view;
	private ImageView img_user_head;
	private EditText edt_name, edt_qq;
	private TextView tv_update;
	private Button btn_ok;
	private String path;
	public static final int FROM_XC = 0X00;
	public static final int FROM_CJ = 0X01;
	public static final String TAG = "AccountVipFragment";
	private static final String URL = AppConstants.SERVICE_ADDRESS
			+ "userinfo/gotoUpdateUserInfo";
	private static final String HEADURL = AppConstants.SERVICE_ADDRESS
			+ "userinfo/gotoUpHeadImg";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_account_vip, container, false);
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		img_user_head = (ImageView) view.findViewById(R.id.img_user_head);
		edt_name = (EditText) view.findViewById(R.id.edt_name);
		edt_qq = (EditText) view.findViewById(R.id.edt_qq);
		tv_update = (TextView) view.findViewById(R.id.tv_update);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
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
				intentAction(getActivity(),
						ResetPhoneNumberFragmentActivity.class);

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
		String name=edt_name.getText().toString().trim();
		String qq =edt_qq.getText().toString().trim();
		if(TextUtils.isEmpty(name)){
			Toast.makeText(getActivity(), "姓名为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if(TextUtils.isEmpty(qq)){
			Toast.makeText(getActivity(), "QQ为空！", Toast.LENGTH_LONG).show();
			return;
		}
		if(!StringUtils.isQQNum(qq)){
			Toast.makeText(getActivity(), "QQ格式错误！", Toast.LENGTH_LONG).show();
			return;
		}
		final String id= SharePreferenceUtil.getInstance(getActivity().getApplicationContext()).getUserId();
		RequestParams params = new RequestParams();
		params.put("userid", id);
		params.put("xingming", name);
		params.put("qq", qq);
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code =JsonUtils.getCode(arg2);
				if("0".equals(code)){
					Toast.makeText(getActivity(), "用户id,姓名,或qq号码为空！", Toast.LENGTH_LONG).show();
				}else if("1".equals(code)){
					Toast.makeText(getActivity(), "用户信息修改成功！", Toast.LENGTH_LONG).show();
				}else if("2".equals(code)){
					Toast.makeText(getActivity(), "用户信息不存在！", Toast.LENGTH_LONG).show();
				}else if("3".equals(code)){
					Toast.makeText(getActivity(), "姓名格式不正确！", Toast.LENGTH_LONG).show();
				}else if("4".equals(code)){
					Toast.makeText(getActivity(), "qq号码格式不正确！", Toast.LENGTH_LONG).show();
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
		});

	}

	public static Fragment getInstance(String params) {
		AccountVipFragment fragment = new AccountVipFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

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
				img_user_head.setImageBitmap(bitmap);
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
/*		File file=new File(path);
		String id= SharePreferenceUtil.getInstance(getActivity().getApplicationContext()).getUserId();
		RequestParams params = new RequestParams();
		params.put("userid", id);
		params.put("xingming", name);
		HttpUtils.doPost(url, params res);*/
		// 上传头像
	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

}
