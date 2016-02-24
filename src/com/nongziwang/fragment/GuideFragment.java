package com.nongziwang.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.nongziwang.main.MainActivity;
import com.nongziwang.main.R;
import com.nongziwang.utils.GuidedUtil;
/**
 * 
 * @title GuideFragment
 * @description:GuideFragment主要实现ViewPager导航
 * @author Mersens
 * @time 2016年2月14日
 */
public class GuideFragment extends BaseFragment {
	private View view;
	private ImageView imageView;
	public static int[] imags = { R.drawable.img_guide_1, R.drawable.img_guide_2,
			R.drawable.img_guide_3 };//引导页的图片资源数组
	private int index = 0;//引导页的图片标识
	private TextView tvInNew;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_guide, null);
		index = getArguments().getInt("params");
		initViews();
		return view;
	}

	private void initViews() {
		imageView = (ImageView) view.findViewById(R.id.imageView);
		tvInNew = (TextView) view.findViewById(R.id.tvInNew);
		imageView.setImageResource(imags[index]);
		tvInNew.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				GuidedUtil.getInstance(getActivity().getApplicationContext())
						.setIsFirst(false);
				intentAction(getActivity(), MainActivity.class);
				getActivity().finish();
			}
		});
		if (index == imags.length - 1) {
			tvInNew.setVisibility(View.VISIBLE);
		}
	}

	@Override
	protected void lazyLoad() {

	}

	public static Fragment getInstance(int params) {
		GuideFragment fragment = new GuideFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("params", params);
		fragment.setArguments(bundle);
		return fragment;

	}

}
