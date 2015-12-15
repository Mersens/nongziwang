package com.nongziwang.view;

import com.nongziwang.main.R;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class SpotsDialog extends AlertDialog {

	private static final int DELAY = 150;
	private static final int DURATION = 1500;
	private View view;
	private LayoutInflater mInflater;
	private ProgressLayout progress;
	private int size;
	private AnimatedView[] spots;
	private AnimatorPlayer animator;
	private String title;
	private String msg;
	private boolean isShowSport;
	private boolean isCanceled;
	private TextView tv_title, tv_msg;

	public SpotsDialog(Context context, String title, String msg,
			boolean isShowSport, boolean isCanceled) {
		this(context, R.style.SpotsDialogDefault);
		this.title = title;
		this.msg = msg;
		this.isShowSport = isShowSport;
		this.isCanceled = isCanceled;
	}

	public SpotsDialog(Context context, int theme) {
		super(context, theme);
	}

	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(getContext());
		view = mInflater.inflate(R.layout.dialog, null);
		setContentView(view);
		initViews();

	}

	private void initViews() {
		tv_title = (TextView) view.findViewById(R.id.tv_title);
		tv_title.setText(title);
		tv_msg = (TextView) findViewById(R.id.tv_msg);
		progress = (ProgressLayout) view.findViewById(R.id.progress);
		if (isShowSport) {
			progress.setVisibility(View.VISIBLE);
			tv_msg.setVisibility(View.GONE);
		} else {
			progress.setVisibility(View.GONE);
			tv_msg.setText(msg);
			tv_msg.setVisibility(View.VISIBLE);
		}
		if (isCanceled) {
			setCanceledOnTouchOutside(true);
		} else {
			setCanceledOnTouchOutside(false);
		}
		initProgress();
	}

	@Override
	protected void onStart() {
		super.onStart();
		animator = new AnimatorPlayer(createAnimations());
		animator.play();
	}

	@Override
	protected void onStop() {
		super.onStop();
		animator.stop();
	}

	private void initProgress() {
		size = progress.getSpotsCount();

		spots = new AnimatedView[size];
		int size = getContext().getResources().getDimensionPixelSize(
				R.dimen.spot_size);
		int progressWidth = getContext().getResources().getDimensionPixelSize(
				R.dimen.progress_width);
		for (int i = 0; i < spots.length; i++) {
			AnimatedView v = new AnimatedView(getContext());
			v.setBackgroundResource(R.drawable.spot);
			v.setTarget(progressWidth);
			v.setXFactor(-1f);
			progress.addView(v, size, size);
			spots[i] = v;
		}
	}

	private Animator[] createAnimations() {
		Animator[] animators = new Animator[size];
		for (int i = 0; i < spots.length; i++) {
			Animator move = ObjectAnimator.ofFloat(spots[i], "xFactor", 0, 1);
			move.setDuration(DURATION);
			move.setInterpolator(new HesitateInterpolator());
			move.setStartDelay(DELAY * i);
			animators[i] = move;
		}
		return animators;
	}
}
