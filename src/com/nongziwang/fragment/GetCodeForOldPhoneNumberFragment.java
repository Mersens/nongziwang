package com.nongziwang.fragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nongziwang.activity.LoginActivity;
import com.nongziwang.activity.RegisterActivity;
import com.nongziwang.activity.ResetPhoneNumberFragmentActivity;
import com.nongziwang.application.AppConstants;
import com.nongziwang.db.NongziDao;
import com.nongziwang.db.NongziDaoImpl;
import com.nongziwang.entity.UserBean;
import com.nongziwang.main.R;
import com.nongziwang.service.RegisterCodeTimerService;
import com.nongziwang.utils.HttpUtils;
import com.nongziwang.utils.JsonUtils;
import com.nongziwang.utils.RegisterCodeTimer;
import com.nongziwang.utils.SharePreferenceUtil;
import com.nongziwang.utils.StringUtils;
import com.nongziwang.view.DialogWaiting;

public class GetCodeForOldPhoneNumberFragment extends BaseFragment {
	private View view;
	private Button btn_ok, btn_getcode;
	private EditText edt_getcode;
	private String userid=null;
	private NongziDao dao;
	private TextView tv_tel;
	private String telnum=null;
	private Intent mIntent;
	private static final String TAG="GetCodeForOldPhoneNumberFragment";
	private static final String URL=AppConstants.SERVICE_ADDRESS+"send/sendrandByPhone";
	private String yzm=null;
	private DialogWaiting dialog;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.layout_old_phone_number, container,
				false);
		initViews();
		initEvent();
		return view;
	}
	@SuppressLint("HandlerLeak")
	Handler mCodeHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
				btn_getcode.setText(msg.obj.toString());
				btn_getcode.setEnabled(false);
			} else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
				btn_getcode.setEnabled(true);
				btn_getcode.setText(msg.obj.toString());
			}
		};
	};
	private void initViews() {
		tv_tel=(TextView) view.findViewById(R.id.tv_tel);
		btn_getcode = (Button) view.findViewById(R.id.btn_getcode);
		edt_getcode = (EditText) view.findViewById(R.id.edt_getcode);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);

	}

	private void initEvent() {
		mIntent = new Intent(getActivity(),
				RegisterCodeTimerService.class);
		RegisterCodeTimerService.setHandler(mCodeHandler);
		userid=SharePreferenceUtil.getInstance(getActivity().getApplicationContext()).getUserId();
		dao=new NongziDaoImpl(getActivity().getApplicationContext());
		UserBean user=dao.findUserInfoById(userid);
		if(null != user){
			telnum=user.getUserphone();
			tv_tel.setText(StringUtils.getFormatTelNum(telnum));
		}
		btn_getcode.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doGetCode();
			}
		});

		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doNext();
			}
		});
	}

	public void doGetCode() {
		if(TextUtils.isEmpty(telnum)){
			Toast.makeText(getActivity(), "电话号码为空！", Toast.LENGTH_LONG).show();
			return;
		}
		getActivity().startService(mIntent);
		RequestParams params=new RequestParams();
		params.put("userphone", telnum);
		params.put("msgflag", "3");
		HttpUtils.doPost(URL, params, new TextHttpResponseHandler() {
			@Override
			public void onStart() {
				dialog =new DialogWaiting(getActivity());
				dialog.show();
				super.onStart();
			}
			@Override
			public void onSuccess(int arg0, Header[] arg1, String arg2) {
				String code= JsonUtils.getCode(arg2);
				if(!TextUtils.isEmpty(code)){
					if("0".equals(code)){
						Toast.makeText(getActivity(), "手机号码格式不正确!", Toast.LENGTH_LONG).show();
					} else if("1".equals(code)){
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(arg2);
							yzm = jsonObject.getString("yanzhengma");
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}else if("2".equals(code)){
						Toast.makeText(getActivity(), "该手机号码已被注册!", Toast.LENGTH_LONG).show();
					}
				}
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
				Log.e(TAG, arg2==null?"":arg2);
				Toast.makeText(getActivity(), "获取验证码失败!", Toast.LENGTH_LONG).show();
			}
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				dialog.dismiss();
			}
		});

	}

	public void doNext() {
		String code=edt_getcode.getText().toString().trim();
		if(TextUtils.isEmpty(code) ){
			Toast.makeText(getActivity(), "验证码为空!", Toast.LENGTH_LONG).show();
		    return;
		}
		if(!StringUtils.isQQNum(code)){
			Toast.makeText(getActivity(), "验证码格式错误!", Toast.LENGTH_LONG).show();
			return;
		}
		if(TextUtils.isEmpty(yzm) ){
			Toast.makeText(getActivity(), "验证码错误!", Toast.LENGTH_LONG).show();
			return;
		}
		if(!yzm.equals(code)){
			Toast.makeText(getActivity(), "验证码错误!", Toast.LENGTH_LONG).show();
			return;
		}
		Intent mIntent = new Intent(
				ResetPhoneNumberFragmentActivity.ACTION_GETNEWCODE);
		getActivity().sendBroadcast(mIntent);
	}

	public static Fragment getInstance(String params) {
		GetCodeForOldPhoneNumberFragment fragment = new GetCodeForOldPhoneNumberFragment();
		Bundle bundle = new Bundle();
		bundle.putString("params", params);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	protected void lazyLoad() {

	}
@Override
public void onDestroy() {
	super.onDestroy();
	if(mIntent!=null){
		getActivity().stopService(mIntent);	
	}
}
}
