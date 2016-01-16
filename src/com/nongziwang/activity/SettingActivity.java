package com.nongziwang.activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.main.R;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.view.DialogTips;
import com.nongziwang.view.HeadView.OnLeftClickListener;

public class SettingActivity extends BaseActivity {
	private RelativeLayout layout_setting;
	private NongziDao dao;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_setting);
		dao=new NongziDaoImpl(getApplicationContext());
		initViews();
		initEvent();
	}

	private void initViews() {
		layout_setting=(RelativeLayout) findViewById(R.id.layout_setting);

	}

	private void initEvent() {
		
		setLeftWithTitleViewMethod(R.drawable.ic_menu_back, "����", new OnLeftClickListener() {
			@Override
			public void onClick() {
				finishActivity();
			}
		});
		layout_setting.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				confirmExit();
			}
		});
	}
	
	public void confirmExit() {
		DialogTips dialog = new DialogTips(SettingActivity.this, "�˳�", "�Ƿ��˳���¼��",
				"ȷ��", true, true);
		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int userId) {
				String id=SharePreferenceUtil.getInstance(getApplicationContext()).getUserId();
				dao.delUserInfoById(id);
				SharePreferenceUtil.getInstance(getApplicationContext()).clearData();
				finishActivity();
			}
		});
		dialog.show();
		dialog = null;
	}
}
