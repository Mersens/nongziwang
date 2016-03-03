package com.nongziwang.activity;

import java.util.List;

import org.apache.http.Header;
import org.json.JSONException;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.adapter.ShareAdapter;
import com.nongziwang.application.AppConstants;
import com.nongziwang.entity.NewsDatialBean;
import com.nongziwang.entity.ShareBean;
import com.nongziwang.entity.ZhuanTiBean;
import com.nongziwang.main.R;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.ShareDatas;
import com.nongziwang.view.FlowTextView;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.HeadView.OnRightClickListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

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
	private PopupWindow popupwindow;
	private LayoutInflater mInflater;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.layout_industry_info_detial);
		mInflater=LayoutInflater.from(this);
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

		setDefaultViewMethod(R.drawable.ic_menu_back, "行业资讯", R.drawable.icon_share_light, new OnLeftClickListener() {
			
			@Override
			public void onClick() {
				finishActivity();				
			}
		}, new OnRightClickListener() {
			@Override
			public void onClick() {
				showSharePanel();
			}
		});
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
						ShowToast("newsid为空!");
					} else if ("1".equals(code)) {
						try {
							newsdatialbean = JsonUtils.getNewsDatialInfo(arg2);
							initDatas();
						} catch (JSONException e) {
							e.printStackTrace();
						}

					} else if ("2".equals(code)) {
						ShowToast("该新闻不存在!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ShowToast("请求数据失败!");
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
						ShowToast("zhuantiid为空!");
					} else if ("1".equals(code)) {
						try {
							zhuantibean = JsonUtils.getZhuanTiDatialInfo(arg2);
							initDatas();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else if ("2".equals(code)) {
						ShowToast("没有查询到 该专题信息!");
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2,
					Throwable arg3) {
				Log.e(TAG, arg2 == null ? "" : arg2);
				ShowToast("请求数据失败!");
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
				tv_from.setText("101农资网");
			}
		} else {
			if (zhuantibean != null) {
				title.setText(zhuantibean.getZhuantiname());
				tv_time.setText(zhuantibean.getAddtime());
				ftv.setText(zhuantibean.getCenter());
				tv_from.setText("101农资网");
			}
		}
	}
	
	public void showSharePanel(){
		View pop_v=mInflater.inflate(R.layout.layout_share, null);
		GridView gridView=(GridView) pop_v.findViewById(R.id.gridView);
		final List<ShareBean> mList=ShareDatas.getInstance(IndustryInfoDetialActivity.this).getDatas();
		gridView.setAdapter(new ShareAdapter(mList, IndustryInfoDetialActivity.this));
		ImageButton btn_cancel=(ImageButton) pop_v.findViewById(R.id.btn_cancel);
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
				doOnItemClick(mList,position);
			}
		});
		popupwindow=new PopupWindow(pop_v);
		popupwindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		popupwindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		popupwindow.setTouchable(true);
		popupwindow.setFocusable(true);
		popupwindow.setOutsideTouchable(true);
		popupwindow.setBackgroundDrawable(new BitmapDrawable());
		popupwindow.setAnimationStyle(R.style.GrowFromBottom);
		popupwindow.showAtLocation(findViewById(R.id.detail_main),
				Gravity.BOTTOM , 0, 0);
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
		String targeturl=null;
		if(isNews){
			if (newsdatialbean != null) {
				strtitle=newsdatialbean.getTitle();
				imgsrc=newsdatialbean.getNewsimg();
				center=newsdatialbean.getCenter();
				targeturl=newsdatialbean.getNewsurl();
			}
		}else{
			if (zhuantibean != null) {
				strtitle=zhuantibean.getZhuantiname();
				imgsrc=zhuantibean.getImgsrc();
				center=zhuantibean.getCenter();
				targeturl=zhuantibean.getZhuantiurl();
			}
		}
		ShareBean bean=mList.get(position);
		switch (bean.getType()) {
		case QQ:
			setDatas(SHARE_MEDIA.QQ,strtitle,imgsrc,center,targeturl);
			break;
		case QZONE:
			setDatas(SHARE_MEDIA.QZONE,strtitle,imgsrc,center,targeturl);
			break;
		case WEIXIN:
			setDatas(SHARE_MEDIA.WEIXIN,strtitle,imgsrc,center,targeturl);
			break;
		case WXCIRCLE:
			setDatas(SHARE_MEDIA.WEIXIN_CIRCLE,strtitle,imgsrc,center,targeturl);
			break;
		case SINA:
			setDatas(SHARE_MEDIA.SINA,strtitle,imgsrc,center,targeturl);
			break;
		case ALIPAY:
			setDatas(SHARE_MEDIA.ALIPAY,strtitle,imgsrc,center,targeturl);
			break;
		}
	}
	public void setDatas(SHARE_MEDIA type,String strtitle,String imgsrc,String center,String targeturl){
		if(TextUtils.isEmpty(strtitle) ||  TextUtils.isEmpty(center)){
			ShowToast("分享内容为空！");
			return;
		}
		 UMImage image = new UMImage(IndustryInfoDetialActivity.this, imgsrc);
				new ShareAction(this)
				.setPlatform(type)
				.setCallback(umShareListener)
				.withTitle(strtitle)
				.withText(center)
				.withTargetUrl(targeturl)
				.withMedia(image)
				.share();
	}
	
	public void dimissPop(){
		if(popupwindow !=null && popupwindow.isShowing()){
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
        UMShareAPI.get( this ).onActivityResult( requestCode, resultCode, data);
    }
}
