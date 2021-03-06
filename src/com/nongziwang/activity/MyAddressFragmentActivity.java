package com.nongziwang.activity;
import com.nongziwang.fragment.MyAddressAddFragment;
import com.nongziwang.fragment.MyAddressEditorFragment;
import com.nongziwang.fragment.MyAddressListFragment;
import com.nongziwang.main.R;
import com.nongziwang.view.HeadView.OnLeftClickListener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

public class MyAddressFragmentActivity extends BaseActivity{
	public static final String ACTION_LIST="action_list";
	public static final String ACTION_ADD="action_add";
	public static final String ACTION_EDITOR="action_editor";
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_myaddress);
		initViews();
		initEvent();
		addFragment(Style.LIST, null,false);
	}

	private void initViews() {
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "�ջ���ַ", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
	}
	
	public enum Style{
		LIST,EDITOR,ADD;
	}
	
	public Fragment creatFragment(Style style, String params) {
		Fragment fragment = null;
		switch (style) {
		case EDITOR:
			fragment=MyAddressEditorFragment.getInstance(params);
			break;
		case ADD:
			fragment=MyAddressAddFragment.getInstance(params);
			break;
		case LIST:
			fragment=MyAddressListFragment.getInstance(params);
			break;
		}
		return fragment;
	}
	public void addFragment(Style style, String params, boolean isAnim) {

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		Fragment fragment = fm
				.findFragmentById(R.id.fragment_addresscontainer);
		if (fragment != null) {
			ft.remove(fragment);
		}
		fragment = creatFragment(style, params);
		if (isAnim) {
			ft.setCustomAnimations(R.anim.left_in, R.anim.left_out);
		}
		ft.addToBackStack(null);
		ft.replace(R.id.fragment_addresscontainer, fragment);
		ft.commit();

	}
	private void initEvent() {
		registerBoradcastReceiver();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK)) {
			finishActivity();
			return true;
		} 
		return false;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(mBroadcastReceiver);
	}
	
	public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction(ACTION_LIST);  
        myIntentFilter.addAction(ACTION_ADD);  
        myIntentFilter.addAction(ACTION_EDITOR);  
        //ע��㲥        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    } 
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if(action.equals(ACTION_LIST)){
            	addFragment(Style.LIST, null,true);
            }else if(action.equals(ACTION_ADD)){
            	addFragment(Style.ADD, null,true);
            }else if(action.equals(ACTION_EDITOR)){
            	addFragment(Style.EDITOR, null,true);
            }
        }  
    }; 
}
