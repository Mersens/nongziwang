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
			ShowToast("����˷���");
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
		//ShareSDK��ݷ����ṩ���������һ���ǾŹ��� CLASSIC  �ڶ�����SKYBLUE
		oks.setTheme(OnekeyShareTheme.CLASSIC);
		// ��༭ҳ����ʾΪDialogģʽ
		oks.setDialogMode();
		// ���Զ���Ȩʱ���Խ���SSO��ʽ
		oks.disableSSOWhenAuthorize(); 
		 
		// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		 oks.setTitle("����");
		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		 oks.setText("���Ƿ����ı�");
		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		 oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		 oks.setUrl("http://sharesdk.cn");
		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		 oks.setComment("���ǲ��������ı�");
		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		 oks.setSite("ũ����");
		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		 oks.setSiteUrl("http://sharesdk.cn");
		// ��������GUI
		 oks.show(context);
	}
}
