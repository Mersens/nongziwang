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

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutCompat.LayoutParams;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.entity.ImgBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.BitmapUtils;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.ImageLoadOptions;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.view.DialogWaiting;
import com.nongziwang.view.FlowLayout;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.ProcessImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProductUpdateActivity extends BaseActivity {
	private Button btn_next;
	private EditText edt_title, edt_gjz, edt_yt, edt_cf, edt_pp, edt_ms,
			edt_xq, edt_jg, edt_dw;
	private FlowLayout flowlayout;
	private DialogWaiting dialog;
	private String id;
	private int index = 0;
	private int addindex = 0;
	private boolean isAdd=false;
	private MarginLayoutParams lp;
	private List<ChanPinBean> chanpinbeans;
	private List<ImgBean> maplist;
	private static final String GETINFOURL = AppConstants.SERVICE_ADDRESS
			+ "chanpin/getChanpinById";
	private static final String UPDATEURL = AppConstants.SERVICE_ADDRESS
			+ "chanpin/updateChanpinData";
	private static final String UPDATEIMGURL = AppConstants.SERVICE_ADDRESS
			+ "chanpin/gotoUpChanpinImg";
	private static final String TAG = "ProductUpdateActivity";
	private List<ProcessImageView> imageviewlist = new ArrayList<ProcessImageView>();
	private Map<String, String> map = new HashMap<String, String>();
	private ImageView img_add;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_product_update);
		id = getIntent().getStringExtra("params");
		initViews();
		initEvent();
		initDatas();
	}

	private void initViews() {
		img_add = (ImageView) findViewById(R.id.img_add);
		edt_dw = (EditText) findViewById(R.id.edt_dw);
		edt_jg = (EditText) findViewById(R.id.edt_jg);
		edt_xq = (EditText) findViewById(R.id.edt_xq);
		flowlayout = (FlowLayout) findViewById(R.id.flowlayout);
		edt_title = (EditText) findViewById(R.id.edt_title);
		edt_gjz = (EditText) findViewById(R.id.edt_gjz);
		edt_yt = (EditText) findViewById(R.id.edt_yt);
		edt_cf = (EditText) findViewById(R.id.edt_cf);
		edt_pp = (EditText) findViewById(R.id.edt_pp);
		edt_ms = (EditText) findViewById(R.id.edt_ms);
		btn_next = (Button) findViewById(R.id.btn_next);
		lp = new MarginLayoutParams(new LayoutParams(160, 160));
		lp.bottomMargin = 5;
		lp.leftMargin = 10;
		lp.rightMargin = 10;
		lp.topMargin = 5;
	}

	private void setDatas() {
		ChanPinBean bean = chanpinbeans.get(0);
		edt_title.setText(bean.getTitle());
		edt_gjz.setText(bean.getKeyword());
		edt_yt.setText(bean.getYongtuname());
		edt_cf.setText(bean.getChengfenname());
		edt_pp.setText(bean.getPinpai());
		edt_ms.setText(bean.getMiaoshu());
		edt_xq.setText(bean.getDetail());
		edt_jg.setText(bean.getJiage());
		if (maplist != null && maplist.size() > 0) {
			for (int i = 0; i < maplist.size(); i++) {
				ProcessImageView imageview = new ProcessImageView(
						ProductUpdateActivity.this);
				imageview.isProgress(false);
				imageview.setId(i);
				imageview.setOnClickListener(toolsItemListener);
				imageviewlist.add(imageview);
				ImageLoader.getInstance().displayImage(
						maplist.get(i).getImgsrc(), imageview,
						ImageLoadOptions.getOptions());
				flowlayout.addView(imageview, lp);
			}
		}
	}

	private View.OnClickListener toolsItemListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			index = v.getId();
			Intent intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, 1);
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1 && data != null) {
			Cursor cursor = getContentResolver().query(data.getData(), null,
					null, null, null);
			cursor.moveToFirst();
			int dex = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			String path = cursor.getString(dex);
			Bitmap bitmap = BitmapUtils.getBitMap(path);
			cursor.close();
			setImage(bitmap);
			upLoadeImag(path, index);
		}
		if (requestCode == 2 && data != null) {
			Cursor cursor = getContentResolver().query(data.getData(), null,
					null, null, null);
			cursor.moveToFirst();
			int dex = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			String path = cursor.getString(dex);
			Bitmap bitmap = BitmapUtils.getBitMap(path);
			cursor.close();
			setAddImage(bitmap); 
			upLoadeImag(path,index);
			
		}

	}

	private void upLoadeImag(final String path, final int id) {
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

		HttpUtils.doPost(UPDATEIMGURL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("1".equals(code)) {
					try {
						JSONObject jsonObject = new JSONObject(arg2);
						String url = jsonObject.getString("chanpinimg");
						if(isAdd){
							map.put(url, url);
						}else{
							map.put(maplist.get(id).getImgid(), url);	
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if ("2".equals(code)) {
					ShowToast("资源太大!");

				} else if ("3".equals(code)) {
					ShowToast("文件为空！");
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				ShowToast("上传失败!");
				Log.e(TAG, arg2 == null ? "" : arg2);
			}

			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				// TODO Auto-generated method stub
				super.onProgress(bytesWritten, totalSize);
				int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
				if(isAdd){
					imageviewlist.get(addindex + maplist.size()-1).setProgress(count);
				}else{
				   imageviewlist.get(index).setProgress(count);
				}
				
			}
			@Override
			public void onFinish() {
				super.onFinish();
				if(isAdd){
				isAdd=false;
				}
			}
		});
	}

	private void setImage(Bitmap bitmap) {
		if (bitmap != null) {
			imageviewlist.get(index).setImageBitmap(bitmap);
		}
	}
	
	public void setAddImage(Bitmap bitmap){
		if (bitmap != null) {
		ProcessImageView imageview = new ProcessImageView(
				ProductUpdateActivity.this);
		imageview.isProgress(true);
		imageview.setImageBitmap(bitmap);
		imageviewlist.add(imageview);
		addindex++;
		}
	}

	private void initEvent() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "产品修改",
				new OnLeftClickListener() {
					@Override
					public void onClick() {
						finishActivity();
					}
				});
		btn_next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doFilter();
			}
		});
		
		img_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (addindex + maplist.size() > 8) {
					return;
				}
				isAdd=true;
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intent, 2);
			}
		});
	}

	private void initDatas() {
		RequestParams params = new RequestParams();
		params.put("chanpinid", id);
		HttpUtils.doPost(GETINFOURL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("0".equals(code)) {
					ShowToast("该用户没有店铺!");
				} else if ("1".equals(code)) {
					try {
						chanpinbeans = JsonUtils.getChanPinInfoByid(arg2);
						maplist = JsonUtils.getChanpinImgList(arg2);
						if (chanpinbeans != null && chanpinbeans.size() > 0) {
							setDatas();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				ShowToast("获取信息失败！");
				Log.e(TAG, arg2 == null ? "" : arg2);
			}
		});
	}

	private void doFilter() {
		String dw = edt_dw.getText().toString().replaceAll(" ", "");
		String xq = edt_xq.getText().toString().replaceAll(" ", "");
		String title = edt_title.getText().toString().replaceAll(" ", "");
		String gjz = edt_gjz.getText().toString().replaceAll(" ", "");
		String yt = edt_yt.getText().toString().replaceAll(" ", "");
		String cf = edt_cf.getText().toString().replaceAll(" ", "");
		String pp = edt_pp.getText().toString().replaceAll(" ", "");
		String ms = edt_ms.getText().toString().replaceAll(" ", "");
		String jg = edt_jg.getText().toString().replaceAll(" ", "");
		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(gjz)
				|| TextUtils.isEmpty(jg) || TextUtils.isEmpty(yt)
				|| TextUtils.isEmpty(cf) || TextUtils.isEmpty(dw)
				|| TextUtils.isEmpty(pp) || TextUtils.isEmpty(ms)
				|| TextUtils.isEmpty(xq)) {
			ShowToast("请完善信息!");
			return;
		}

		if (!isSuitLen(title, 60)) {
			ShowToast("商品标题内容过长!");
			return;
		}
		if (!isSuitLen(gjz, 30)) {
			ShowToast("关键字内容过长!");
			return;
		}
		if (!isSuitLen(yt, 30)) {
			ShowToast("主要用途内容过长!");
			return;
		}
		if (!isSuitLen(cf, 30)) {
			ShowToast("主要成分内容过长!");
			return;
		}
		if (!isSuitLen(pp, 30)) {
			ShowToast("品牌内容过长!");
			return;
		}
		if (!isSuitLen(ms, 200)) {
			ShowToast("商品描述内容过长!");
			return;
		}
		if (!isSuitLen(xq, 200)) {
			ShowToast("商品详情内容过长!");
			return;
		}
		if (map.size() == 0) {
			for (int i = 0; i < maplist.size(); i++) {
				map.put(maplist.get(i).getImgid(), maplist.get(i).getImgsrc());
			}
		}
		RequestParams params = new RequestParams();
		params.put("chanpinid", id);
		params.put("title", title);
		params.put("keyword", gjz);
		params.put("yongtuname", yt);
		params.put("chengfenname", cf);
		params.put("pinpainame", pp);
		params.put("miaoshu", ms);
		params.put("offerDetail", xq);
		params.put("jiage", jg);
		params.put("unit", dw);
		Gson gson = new Gson();
		String chanpinImgJsonString = gson.toJson(map);
		params.put("chanpinImgJsonString", chanpinImgJsonString);
		doUpdate(params);
	}

	private void doUpdate(RequestParams params) {
		HttpUtils.doPost(UPDATEURL, params, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				dialog = new DialogWaiting(ProductUpdateActivity.this);
				dialog.show();
				super.onStart();
			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if ("1".equals(code)) {
					ShowToast("修改成功！");
					finishActivity();
				} else if ("0".equals(code)) {
					ShowToast("该产品信息不存在!");
				} else if ("2".equals(code)) {
					ShowToast("信息填写不完整!");
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				ShowToast("修改失败！");
				Log.e(TAG, arg2 == null ? "" : arg2);

			}

			@Override
			public void onFinish() {
				super.onFinish();
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
				}
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
}
