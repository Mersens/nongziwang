package com.nongziwang.view;


import com.nongziwang.main.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

public class DialogWaiting extends AlertDialog {
	public DialogWaiting(Context context) {
		super(context,R.style.alert);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		setCanceledOnTouchOutside(false);
	}
}
