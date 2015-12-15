package com.nongziwang.fragment;

import com.nongziwang.activity.ProductDetailFragmentActivity;
import com.nongziwang.main.R;
import com.nongziwang.view.MyScrollView;
import com.nongziwang.view.MyScrollView.OnScrollToBottomListener;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

@SuppressLint("ClickableViewAccessibility")
public class ProductDetailFragment extends BaseFragment {
	private View view;
	private MyScrollView scrollView;
	private boolean isBottom = false;
	private float y1 = 0;
	private float y2 = 0;
	private ImageView image_up;
	private TextView tv_tips;
	private ProgressBar progressBar;
	private Handler handler;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_product_detail, container,
				false);
		//handler=ProductDetailFragmentActivity.handler;
		ProductDetailFragmentActivity activity=(ProductDetailFragmentActivity) getActivity();
		handler=activity.handler;
		initViews();
		initEvent();
		return view;
	}

	private void initViews() {
		scrollView = (MyScrollView) view.findViewById(R.id.scrollView);
		image_up = (ImageView) view.findViewById(R.id.image_up);
		tv_tips = (TextView) view.findViewById(R.id.tv_tips);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
	}

	private void initEvent() {
		scrollView.setOnScrollToBottomLintener(new OnScrollToBottomListener() {
			@Override
			public void onScrollBottomListener(boolean isBtom) {
				isBottom = isBtom;
			}
		});
		scrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					// 当手指按下的时候
					y1 = event.getY();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					// 当手指离开的时候
					y2 = event.getY();
					if (y1 - y2 > 45 && isBottom) {
						image_up.setVisibility(View.GONE);
						tv_tips.setText("正在加载");
						progressBar.setVisibility(View.VISIBLE);
						Message msg=new Message();
						msg.arg1=ProductDetailFragmentActivity.LOADING;
						handler.sendMessageDelayed(msg, 1000);

					} else if (y2 - y1 > 45 && !isBottom) {
						image_up.setVisibility(View.VISIBLE);
						tv_tips.setText("上拉查看图文详情");
						progressBar.setVisibility(View.GONE);
					}
				}
				return false;
			}
		});

	}

	@Override
	protected void lazyLoad() {
		// TODO Auto-generated method stub

	}

	public static Fragment getInstance(String params) {
		ProductDetailFragment fragment = new ProductDetailFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}

}
