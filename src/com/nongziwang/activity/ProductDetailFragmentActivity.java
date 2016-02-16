package com.nongziwang.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.nongziwang.fragment.ProductDetailFragment;
import com.nongziwang.fragment.ProductDetailMoreFragment;
import com.nongziwang.main.R;

@SuppressLint("HandlerLeak")
public class ProductDetailFragmentActivity extends BaseActivity implements OnClickListener {
	private ImageView image_back,image_fenxiang;
	private  String params;
	public static final int LOADING=0X01;
	public static final int LOADINGBACK=0X10;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_product_detail_main);
		ShareSDK.initSDK(this);
		params=getIntent().getStringExtra("params");
		initViews();
		addFragment(Style.PRUDUCTDETAI, params);
		initEvent();
	}
	public  Handler handler=new Handler(){
		 public void handleMessage(android.os.Message msg) {
			 if(msg.arg1==LOADING){
				 addFragment(Style.MOREPRUDUCTDETAI,params);
			 }
			 if(msg.arg1==LOADINGBACK){
				 addFragment(Style.PRUDUCTDETAI,params);
			 }
		 };
	 };

	
	private void initViews() {
		image_back=(ImageView) findViewById(R.id.image_back);
		image_fenxiang=(ImageView) findViewById(R.id.image_fenxiang);
	}
	
	private void initEvent() {
		image_back.setOnClickListener(this);
		image_fenxiang.setOnClickListener(this);

	}

	public enum Style{
		PRUDUCTDETAI,MOREPRUDUCTDETAI
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
			ShowToast("点击了分享");
			showShare(ProductDetailFragmentActivity.this, null, true);
			break;
		}
		
	}
	public static void showShare(Context context, String platformToShare, boolean showContentEdit) {
		OnekeyShare oks = new OnekeyShare();
		oks.setSilent(!showContentEdit);
		if (platformToShare != null) {
			oks.setPlatform(platformToShare);
		}
		//ShareSDK快捷分享提供两个界面第一个是九宫格 CLASSIC  第二个是SKYBLUE
		oks.setTheme(OnekeyShareTheme.CLASSIC);
		// 令编辑页面显示为Dialog模式
		oks.setDialogMode();
		// 在自动授权时可以禁用SSO方式
		oks.disableSSOWhenAuthorize(); 
		 
		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle("分享");
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("我是分享文本");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://sharesdk.cn");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite("农资网");
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://sharesdk.cn");
		// 启动分享GUI
		 oks.show(context);
	}
}
