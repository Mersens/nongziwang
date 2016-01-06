package com.nongziwang.activity;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.HeadView.OnRightClickListener;

import android.content.Context;
import android.os.Bundle;

public class IndustryInfoDetialActivity extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_industry_info_detial);
		// ��ʼ��ShareSDK
		ShareSDK.initSDK(this);
		initViews();
		initEvent();

	}

	private void initViews() {
		setDefaultViewMethod(R.drawable.ic_menu_back, "��ҵ��Ѷ", R.drawable.icon_share_light, new OnLeftClickListener() {
			
			@Override
			public void onClick() {
				finishActivity();
				
			}
		}, new OnRightClickListener() {
			
			@Override
			public void onClick() {
				showShare(IndustryInfoDetialActivity.this, null, true);
			}
		});
		setHeadViewBg(R.color.white_color);
		
	}

	private void initEvent() {
		
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
