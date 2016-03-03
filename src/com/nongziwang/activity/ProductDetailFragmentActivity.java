package com.nongziwang.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

import com.nongziwang.adapter.ShareAdapter;
import com.nongziwang.entity.ChanPinBean;
import com.nongziwang.entity.ShareBean;
import com.nongziwang.fragment.ProductDetailFragment;
import com.nongziwang.fragment.ProductDetailMoreFragment;
import com.nongziwang.main.R;
import com.nongziwang.utils.ShareDatas;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

@SuppressLint("HandlerLeak")
public class ProductDetailFragmentActivity extends BaseActivity implements
		OnClickListener {
	private ImageView image_back, image_fenxiang;
	private String params;
	public static final int LOADING = 0X01;
	public static final int LOADINGBACK = 0X10;
	public static ChanPinBean bean = null;
	private PopupWindow popupwindow;
	private LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_product_detail_main);
		mInflater = LayoutInflater.from(this);
		params = getIntent().getStringExtra("params");
		initViews();
		addFragment(Style.PRUDUCTDETAI, params);
		initEvent();
	}

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.arg1 == LOADING) {
				addFragment(Style.MOREPRUDUCTDETAI, params);
			}
			if (msg.arg1 == LOADINGBACK) {
				addFragment(Style.PRUDUCTDETAI, params);
			}
		};
	};

	private void initViews() {
		image_back = (ImageView) findViewById(R.id.image_back);
		image_fenxiang = (ImageView) findViewById(R.id.image_fenxiang);
	}

	private void initEvent() {
		image_back.setOnClickListener(this);
		image_fenxiang.setOnClickListener(this);

	}

	public enum Style {
		PRUDUCTDETAI, MOREPRUDUCTDETAI
	}

	public Fragment creatFragment(Style style, String params) {
		Fragment fragment = null;

		switch (style) {
		case PRUDUCTDETAI:
			fragment = ProductDetailFragment.getInstance(params);
			break;
		case MOREPRUDUCTDETAI:
			fragment = ProductDetailMoreFragment.getInstance(params);
			break;
		}
		return fragment;

	}

	public void addFragment(Style style, String params) {
		FragmentManager fm = getSupportFragmentManager();
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
		if (fragment != null) {
			fm.beginTransaction().remove(fragment).commit();
		}
		fragment = creatFragment(style, params);
		fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.image_back:
			finishActivity();
			break;
		case R.id.image_fenxiang:
			showSharePanel();
			break;
		}
	}

	public void showSharePanel() {
		View pop_v = mInflater.inflate(R.layout.layout_share, null);
		GridView gridView = (GridView) pop_v.findViewById(R.id.gridView);
		final List<ShareBean> mList = ShareDatas.getInstance(
				ProductDetailFragmentActivity.this).getDatas();
		gridView.setAdapter(new ShareAdapter(mList,
				ProductDetailFragmentActivity.this));
		ImageButton btn_cancel = (ImageButton) pop_v
				.findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dimissPop();
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				dimissPop();
				doOnItemClick(mList, position);
			}
		});
		popupwindow = new PopupWindow(pop_v);
		popupwindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		popupwindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		popupwindow.setTouchable(true);
		popupwindow.setFocusable(true);
		popupwindow.setOutsideTouchable(true);
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
		popupwindow.setAnimationStyle(R.style.GrowFromBottom);
		popupwindow.showAtLocation(findViewById(R.id.main), Gravity.BOTTOM, 0,
				0);
		popupwindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				popupwindow.dismiss();
				popupwindow = null;
			}
		});

	}

	private void doOnItemClick(List<ShareBean> mList, int position) {
		String strtitle = null;
		String imgsrc = null;
		String center = null;
		String targeturl = null;
		if (bean != null) {
			strtitle = bean.getDianpuname();
			imgsrc = bean.getChanpinimg();
			center = bean.getTitle();
			targeturl = bean.getPcurl();
		}
		ShareBean bean = mList.get(position);
		switch (bean.getType()) {
		case QQ:
			setDatas(SHARE_MEDIA.QQ, strtitle, imgsrc, center, targeturl);
			break;
		case QZONE:
			setDatas(SHARE_MEDIA.QZONE, strtitle, imgsrc, center, targeturl);
			break;
		case WEIXIN:
			setDatas(SHARE_MEDIA.WEIXIN, strtitle, imgsrc, center, targeturl);
			break;
		case WXCIRCLE:
			setDatas(SHARE_MEDIA.WEIXIN_CIRCLE, strtitle, imgsrc, center,
					targeturl);
			break;
		case SINA:
			setDatas(SHARE_MEDIA.SINA, strtitle, imgsrc, center, targeturl);
			break;
		case ALIPAY:
			setDatas(SHARE_MEDIA.ALIPAY, strtitle, imgsrc, center, targeturl);
			break;
		}
	}

	public void setDatas(SHARE_MEDIA type, String strtitle, String imgsrc,
			String center, String targeturl) {
		if (TextUtils.isEmpty(strtitle) || TextUtils.isEmpty(center)) {
			ShowToast("分享内容为空！");
			return;
		}
		UMImage image = new UMImage(ProductDetailFragmentActivity.this, imgsrc);
		new ShareAction(this).setPlatform(type).setCallback(umShareListener)
				.withTitle(strtitle).withText(center).withTargetUrl(targeturl)
				.withMedia(image).share();
	}

	public void dimissPop() {
		if (popupwindow != null && popupwindow.isShowing()) {
			popupwindow.dismiss();
			popupwindow = null;
		}
	}

	private UMShareListener umShareListener = new UMShareListener() {
		@Override
		public void onResult(SHARE_MEDIA platform) {
			ShowToast("分享成功!");
		}

		@Override
		public void onError(SHARE_MEDIA platform, Throwable t) {
			ShowToast("分享失败!");
		}

		@Override
		public void onCancel(SHARE_MEDIA platform) {

		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		bean = null;
	}
}
