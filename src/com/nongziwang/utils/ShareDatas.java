package com.nongziwang.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.nongziwang.entity.ShareBean;
import com.nongziwang.entity.ShareBean.Type;
import com.nongziwang.main.R;

public class ShareDatas {
	private static ShareDatas mShareDatas;
	private List<ShareBean> mlist;
	private ShareDatas(Context cnt){
		mlist=new ArrayList<ShareBean>();
		mlist.add(new ShareBean("����΢��", cnt.getResources().getDrawable(R.drawable.umeng_socialize_sina_on),Type.SINA));
		mlist.add(new ShareBean("QQ", cnt.getResources().getDrawable(R.drawable.umeng_socialize_qq_on),Type.QQ));
		mlist.add(new ShareBean("QQ�ռ�", cnt.getResources().getDrawable(R.drawable.umeng_socialize_qzone_on),Type.QZONE));
		mlist.add(new ShareBean("΢��", cnt.getResources().getDrawable(R.drawable.umeng_socialize_wechat),Type.WEIXIN));
		mlist.add(new ShareBean("����Ȧ", cnt.getResources().getDrawable(R.drawable.umeng_socialize_wxcircle),Type.WXCIRCLE));
		mlist.add(new ShareBean("֧����", cnt.getResources().getDrawable(R.drawable.umeng_socialize_alipay),Type.ALIPAY));
	}
	
	public static ShareDatas getInstance(Context cnt){
		if(mShareDatas==null){
			mShareDatas=new ShareDatas(cnt);
		}
		return mShareDatas;
	}

	public List<ShareBean> getDatas(){
		return new ArrayList<ShareBean>(mlist); 
	}
}
