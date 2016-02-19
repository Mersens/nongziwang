package com.nongziwang.activity;

import org.apache.http.Header;
import org.json.JSONException;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.OnekeyShareTheme;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.NewsDatialBean;
import com.nongziwang.entity.ZhuanTiBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.view.FlowTextView;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.HeadView.OnRightClickListener;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class IndustryInfoDetialActivity extends BaseActivity {
	private boolean isNews = false;
	private String newsid = null;
	private static final String NEWS_URL = AppConstants.SERVICE_ADDRESS
			+ "news/gotoNewsXiangqing";
	private static final String ZHUANTI_URL = AppConstants.SERVICE_ADDRESS
			+ "news/gotoZhuantiXiangqing";
	private TextView title, tv_time,tv_from;
	private FlowTextView ftv;
	private NewsDatialBean newsdatialbean = null;
	private ZhuanTiBean zhuantibean = null;
	private RelativeLayout layout_loading;
	private static final String TAG = "IndustryInfoDetialActivity";

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_industry_info_detial);
		ShareSDK.initSDK(this);
		isNews = getIntent().getBooleanExtra("isNews", false);
		newsid = getIntent().getStringExtra("newsid");
		initViews();
		initEvent();

	}

	private void initViews() {
		tv_from=(TextView) findViewById(R.id.tv_from);
		layout_loading = (RelativeLayout) findViewById(R.id.layout_loading);
		title = (TextView) findViewById(R.id.title);
		tv_time = (TextView) findViewById(R.id.tv_time);
		ftv = (FlowTextView) findViewById(R.id.ftv);
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "��ҵ��Ѷ", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		
		/*setDefaultViewMethod(R.drawable.ic_menu_back, "��ҵ��Ѷ",
				R.drawable.icon_share_light, new OnLeftClickListener() {
					@Override
					public void onClick() {
						
					}
				}, new OnRightClickListener() {

					@Override
					public void onClick() {
						showShare(IndustryInfoDetialActivity.this, null, true);
					}
				});*/
		setHeadViewBg(R.color.white_color);

	}

	private void initEvent() {
		if (isNews)
			doSearchNewsDetial();

		else
			doSearchZhuanTiDetial();

	}

	public void doSearchNewsDetial() {
		RequestParams params = new RequestParams();
		params.put("newsid", newsid);
		HttpUtils.doPost(NEWS_URL, params, new TextHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						Toast.makeText(IndustryInfoDetialActivity.this,
								"newsidΪ��!", Toast.LENGTH_LONG).show();
					} else if ("1".equals(code)) {
						try {
							newsdatialbean = JsonUtils.getNewsDatialInfo(arg2);
							initDatas();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else if ("2".equals(code)) {
						Toast.makeText(IndustryInfoDetialActivity.this,
								"�����Ų�����!", Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ShowToast("��������ʧ��!");

			}

			@Override
			public void onFinish() {
				layout_loading.setVisibility(View.GONE);
				super.onFinish();
			}
		});

	}

	public void doSearchZhuanTiDetial() {
		RequestParams params = new RequestParams();
		params.put("zhuantiid", newsid);
		HttpUtils.doPost(ZHUANTI_URL, params, new TextHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code = JsonUtils.getCode(arg2);
				if (!TextUtils.isEmpty(code)) {
					if ("0".equals(code)) {
						Toast.makeText(IndustryInfoDetialActivity.this,
								"zhuantiidΪ��!", Toast.LENGTH_LONG).show();
					} else if ("1".equals(code)) {
						try {
							zhuantibean = JsonUtils.getZhuanTiDatialInfo(arg2);
							initDatas();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else if ("2".equals(code)) {
						Toast.makeText(IndustryInfoDetialActivity.this,
								"û�в�ѯ�� ��ר����Ϣ!", Toast.LENGTH_LONG).show();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ShowToast("��������ʧ��!");

			}

			@Override
			public void onFinish() {
				layout_loading.setVisibility(View.GONE);
				super.onFinish();
			}
		});

	}
	public void initDatas() {
		if (isNews) {
			if (newsdatialbean != null) {
				title.setText(newsdatialbean.getTitle());
				tv_time.setText(newsdatialbean.getAddtime());
				ftv.setText(newsdatialbean.getCenter());
				tv_from.setText("101ũ����");
			}
		} else {
			if (zhuantibean != null) {
				title.setText(zhuantibean.getZhuantiname());
				tv_time.setText(zhuantibean.getAddtime());
				ftv.setText(zhuantibean.getCenter());
				tv_from.setText("101ũ����");
			}
		}
	}
	public  void showShare(Context context, String platformToShare,
			boolean showContentEdit) {
		OnekeyShare oks = new OnekeyShare();
		oks.setSilent(!showContentEdit);
		if (platformToShare != null) {
			oks.setPlatform(platformToShare);
		}

		oks.setTheme(OnekeyShareTheme.CLASSIC);
		oks.setDialogMode();
		oks.disableSSOWhenAuthorize();
		if(isNews){
			if (newsdatialbean != null) {
				setShareData(context, oks, newsdatialbean.getTitle(), newsdatialbean.getCenter(), newsdatialbean.getNewsimg());
			}
		}else{
			if (zhuantibean != null) {
				setShareData(context, oks, zhuantibean.getZhuantiname(), zhuantibean.getCenter(), zhuantibean.getImgsrc());
			}
		}
	}
	
	public void setShareData(Context context,OnekeyShare oks,String title,String text,String imgsrc){
		oks.setTitle(title);
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl("http://www.nz101.com/");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText(text);
		// imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
		//oks.setImagePath(imgsrc);// ȷ��SDcard������ڴ���ͼƬ
		oks.setImageUrl(imgsrc);
		// url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		oks.setUrl("http://www.nz101.com/");
		// comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
		//oks.setComment("���ǲ��������ı�");
		// site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
		oks.setSite("101ũ����");
		
		// siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
		oks.setSiteUrl("http://www.nz101.com/");
		oks.show(context);
	}
}
