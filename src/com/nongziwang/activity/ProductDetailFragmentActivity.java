package com.nongziwang.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import com.nongziwang.fragment.ProductDetailFragment;
import com.nongziwang.fragment.ProductDetailMoreFragment;
import com.nongziwang.main.R;

@SuppressLint("HandlerLeak")
public class ProductDetailFragmentActivity extends BaseActivity implements OnClickListener {
	private ImageView image_back,image_shoucang,image_fenxiang;
	private Button btn_buy;
	private  String params;
	public static final int LOADING=0X01;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.layout_product_detail_main);
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
		 };
	 };

	
	private void initViews() {
		image_back=(ImageView) findViewById(R.id.image_back);
		image_shoucang=(ImageView) findViewById(R.id.image_shoucang);
		image_fenxiang=(ImageView) findViewById(R.id.image_fenxiang);
		btn_buy=(Button) findViewById(R.id.btn_buy);
	}
	
	private void initEvent() {
		image_back.setOnClickListener(this);
		image_shoucang.setOnClickListener(this);
		image_fenxiang.setOnClickListener(this);
		btn_buy.setOnClickListener(this);

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
		case R.id.image_shoucang:
			ShowToast("点击了收藏");
			break;
		case R.id.image_fenxiang:
			ShowToast("点击了分享");
			break;
		case R.id.btn_buy:
			ShowToast("点击了购买");
			break;


		}
		
	}

}
