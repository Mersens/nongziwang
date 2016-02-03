package com.nongziwang.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ProcessImageView extends ImageView {
	private Paint mPaint;// ����
	int width = 0;
	int height = 0;
	Context context = null;
	int progress = 0;
	boolean isProgress = true;

	public ProcessImageView(Context context) {
		super(context);
		super.setScaleType(ScaleType.FIT_XY);
		this.context = context;
		mPaint = new Paint();
	}

	public ProcessImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		super.setScaleType(ScaleType.FIT_XY);
		this.context = context;
		mPaint = new Paint();
	}

	public ProcessImageView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		super.setScaleType(ScaleType.FIT_XY);
		this.context = context;
		mPaint = new Paint();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint.setAntiAlias(true); // �������
		mPaint.setStyle(Paint.Style.FILL);
		if (isProgress) {
			mPaint.setColor(Color.parseColor("#70000000"));// ��͸��
			canvas.drawRect(0, 0, getWidth(), getHeight() - getHeight()
					* progress / 100, mPaint);
		}

		mPaint.setColor(Color.parseColor("#00000000"));// ȫ͸��
		canvas.drawRect(0, getHeight() - getHeight() * progress / 100,
				getWidth(), getHeight(), mPaint);

		mPaint.setTextSize(30);
		mPaint.setColor(Color.parseColor("#FFFFFF"));
		mPaint.setStrokeWidth(2);
		if (progress != 100 && isProgress) {
			Rect rect = new Rect();
			mPaint.getTextBounds("100%", 0, "100%".length(), rect);// ȷ�����ֵĿ��
			canvas.drawText(progress + "%", getWidth() / 2 - rect.width() / 2,
					getHeight() / 2, mPaint);
		}

	}

	public void setProgress(int progress) {
		this.progress = progress;
		postInvalidate();
	}

	public void isProgress(boolean isProgress) {
		this.isProgress = isProgress;

	}

}
