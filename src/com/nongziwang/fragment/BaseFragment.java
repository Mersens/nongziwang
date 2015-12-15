package com.nongziwang.fragment;

import com.nongziwang.main.R;
import com.nongziwang.view.HeadView;
import com.nongziwang.view.HeadView.HeaderStyle;
import com.nongziwang.view.HeadView.OnLeftClickListener;
import com.nongziwang.view.HeadView.OnRightClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
	private  Toast mToast;
	private Activity activity;
	protected boolean isVisible;
	private HeadView mHeadView;
	/**
	 * Fragment懒加载，当该Fragment显示时再去加载
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (getUserVisibleHint()) {
			isVisible = true;
			onVisible();
		} else {
			isVisible = false;
			onInvisible();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		activity=getActivity();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	/**
	 * @author Mersens
	 * setDefaultViewMethod--默认显示左侧按钮，标题和右侧按钮
	 * @param leftsrcid
	 * @param title
	 * @param rightsrcid
	 * @param onleftclicklistener
	 * @param onrightclicklistener
	 */
	public void setDefaultViewMethod(View v,int leftsrcid,String title,int rightsrcid, OnLeftClickListener onleftclicklistener,OnRightClickListener onrightclicklistener) {
		mHeadView=(HeadView) v.findViewById(R.id.common_actionbar);
		mHeadView.init(HeaderStyle.DEFAULT);
		mHeadView.setDefaultViewMethod(leftsrcid,title,rightsrcid,onleftclicklistener,onrightclicklistener);
	}
	
	public  void setHeadViewBg(int resid){
		mHeadView.setHeadViewBackground(resid);
	}
	
	public  void setHeadViewTitleColor(int resid){
		mHeadView.setHeadViewTitleColor(resid);
	}
	
	/**
	 * @author Mersens
	 * setRightAndTitleMethod--显示右侧按钮和标题
	 * @param title
	 * @param rightsrcid
	 * @param onRightClickListener
	 */
	public void setRightAndTitleMethod(View v,String title,int rightsrcid, OnRightClickListener onRightClickListener){
		mHeadView=(HeadView) v.findViewById(R.id.common_actionbar);
		mHeadView.init(HeaderStyle.RIGHTANDTITLE);
		mHeadView.setRightAndTitleMethod(title, rightsrcid, onRightClickListener);
	}
	
	
	/**
	 * @author Mersens
	 * setLeftWithTitleViewMethod--显示左侧按钮和标题
	 * @param leftsrcid
	 * @param title
	 * @param onleftclicklistener
	 */
	public void setLeftWithTitleViewMethod(View v,int leftsrcid,String title, OnLeftClickListener onleftclicklistener){
		mHeadView=(HeadView) v.findViewById(R.id.common_actionbar);
		mHeadView.init(HeaderStyle.LEFTANDTITLE);
		mHeadView.setLeftWithTitleViewMethod( leftsrcid, title,  onleftclicklistener);
	}
	
	/**
	 * @author Mersens
	 * setOnlyTileViewMethod--只显示标题
	 * @param title
	 */
	public void setOnlyTileViewMethod(View v,String title) {
		mHeadView=(HeadView) v.findViewById(R.id.common_actionbar);
		mHeadView.setBackgroundResource(R.color.actionbar_blue_color);
		mHeadView.init(HeaderStyle.ONLYTITLE);
		mHeadView.setOnlyTileViewMethod(title);
	}

	/**
	 * @author Mersens
	 * setLeftViewMethod--只显示左侧按钮
	 * @param leftsrcid
	 * @param onleftclicklistener
	 */
	public void setLeftViewMethod(View v,int leftsrcid,OnLeftClickListener onleftclicklistener) {
		mHeadView=(HeadView) v.findViewById(R.id.common_actionbar);
		mHeadView.init(HeaderStyle.LEFT);
		mHeadView.setLeftViewMethod( leftsrcid, onleftclicklistener);
	}
	
	
	/**
	 * @author Mersens
	 * Toast显示以字符串作为显示内容
	 * @param text
	 */
	public void ShowToast(String text) {
		if (mToast == null) {
			mToast = Toast.makeText(activity, text, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(text);
		}
		mToast.show();
	}
	
	/**
	 * @author Mersens
	 * Toast显示参数为资源id作为显示内容
	 * @param srcid
	 */
	public void ShowToast(int srcid) {
		if (mToast == null) {
			mToast = Toast.makeText(activity, srcid, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(srcid);
		}
		mToast.show();
	}
	
	
	/**
	 * @author Mersens
	 * ͨ判断是否有网络连接
	 * @return 
	 */
	public boolean isNetworkAvailable() {
		NetworkInfo info = getNetworkInfo(activity);
		if (info != null) {
			return info.isAvailable();
		}
		return false;
	}
	
	private static NetworkInfo getNetworkInfo(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo();
	}
	
	/**
	 * @author Mersens
	 * @param context
	 * @param cls
	 */
	public <T> void intentAction(Activity context, Class<T> cls) {
		Intent intent = new Intent(context, cls);
		startActivity(intent);
		context.overridePendingTransition(R.anim.left_in,
				R.anim.left_out);
	}
	public <T> void intentAction(Activity context, Class<T> cls,String params) {
		Intent intent = new Intent(context, cls);
		intent.putExtra("params", params);
		startActivity(intent);
		context.overridePendingTransition(R.anim.left_in,
				R.anim.left_out);
	}
	/**
	 * @author Mersens
	 * finishActivity()
	 */
	public void finishActivity(){
		activity.finish();
		activity.overridePendingTransition(R.anim.right_in,
				R.anim.right_out);
	}
	
	protected void onVisible() {
		lazyLoad();
	}

	//提供抽象的加载方法，其子类必须实现
	protected abstract void lazyLoad();
	
	protected void onInvisible() {
		
	}

}
