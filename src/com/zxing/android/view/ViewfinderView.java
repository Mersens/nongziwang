/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zxing.android.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.google.zxing.ResultPoint;
import com.nongziwang.main.R;
import com.zxing.android.camera.CameraManager;

/**
 * This view is overlaid on top of the camera preview. It adds the viewfinder
 * rectangle and partial transparency outside it, as well as the laser scanner
 * animation and result points.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class ViewfinderView extends View {

	private static final long ANIMATION_DELAY = 30L;
	private static final int OPAQUE = 0xFF;
	private static final int MAX_RESULT_POINTS = 5;

	private int ScreenRate;

	private static final int CORNER_WIDTH = 10;

	private static final int MIDDLE_LINE_WIDTH = 6;


	private static final int MIDDLE_LINE_PADDING = 5;

	private static final int SPEEN_DISTANCE = 5;

	private static float density;

	private static final int TEXT_SIZE = 16;

	private static final int TEXT_PADDING_TOP = 30;

	private Paint paint;

	private int slideTop;

	private int slideBottom;

	private Bitmap resultBitmap;
	private final int maskColor;
	private final int resultColor;

	private final int resultPointColor;
	private Collection<ResultPoint> possibleResultPoints;
	private Collection<ResultPoint> lastPossibleResultPoints;
	private CameraManager cameraManager;
	boolean isFirst;

	public ViewfinderView(Context context) {
		this(context, null);
	}

	// This constructor is used when the class is built from an XML resource.
	public ViewfinderView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// Initialize these once for performance rather than calling them every
		// time in onDraw().
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		maskColor = 0x60000000;
		resultColor = 0xb0000000;
		resultPointColor = 0xc0ffff00;
		possibleResultPoints = new ArrayList<ResultPoint>(5);
		lastPossibleResultPoints = null;

		density = context.getResources().getDisplayMetrics().density;

		ScreenRate = (int) (20 * density);
	}

	public void setCameraManager(CameraManager cameraManager) {
		this.cameraManager = cameraManager;
	}

	@Override
	public void onDraw(Canvas canvas) {
		Rect frame = cameraManager.getFramingRect();
		if (frame == null) {
			return;
		}

		if (!isFirst) {
			isFirst = true;
			slideTop = frame.top;
			slideBottom = frame.bottom;
		}

		int width = canvas.getWidth();
		int height = canvas.getHeight();

		paint.setColor(resultBitmap != null ? resultColor : maskColor);

		canvas.drawRect(0, 0, width, frame.top, paint);
		canvas.drawRect(0, frame.top, frame.left, frame.bottom + 1, paint);
		canvas.drawRect(frame.right + 1, frame.top, width, frame.bottom + 1, paint);
		canvas.drawRect(0, frame.bottom + 1, width, height, paint);

		if (resultBitmap != null) {
			// Draw the opaque result bitmap over the scanning rectangle
			paint.setAlpha(OPAQUE);
			canvas.drawBitmap(resultBitmap, frame.left, frame.top, paint);
		} else {

			// Draw a two pixel solid black border inside the framing rect
			paint.setColor(0xffffffff);
			canvas.drawRect(frame.left, frame.top, frame.right + 1, frame.top + 2, paint);
			canvas.drawRect(frame.left, frame.top + 2, frame.left + 2, frame.bottom - 1, paint);
			canvas.drawRect(frame.right - 1, frame.top, frame.right + 1, frame.bottom - 1, paint);
			canvas.drawRect(frame.left, frame.bottom - 1, frame.right + 1, frame.bottom + 1, paint);

			paint.setColor(0xff0ec3ff);
			paint.setAntiAlias(true);
			canvas.drawRect(frame.left - CORNER_WIDTH + 2, frame.top - CORNER_WIDTH + 2, frame.left + ScreenRate
					- CORNER_WIDTH + 2, frame.top + 2, paint);
			canvas.drawRect(frame.left - CORNER_WIDTH + 2, frame.top - CORNER_WIDTH + 2, frame.left + 2, frame.top
					+ ScreenRate - CORNER_WIDTH + 2, paint);
			canvas.drawRect(frame.right - ScreenRate + CORNER_WIDTH - 2, frame.top - CORNER_WIDTH + 2, frame.right
					+ CORNER_WIDTH - 2, frame.top + 2, paint);
			canvas.drawRect(frame.right - 2, frame.top - CORNER_WIDTH + 2, frame.right + CORNER_WIDTH - 2, frame.top
					+ ScreenRate - CORNER_WIDTH + 2, paint);

			canvas.drawRect(frame.left - CORNER_WIDTH + 2, frame.bottom - 2,
					frame.left + ScreenRate - CORNER_WIDTH + 2, frame.bottom + CORNER_WIDTH - 2, paint);
			canvas.drawRect(frame.left - CORNER_WIDTH + 2, frame.bottom - ScreenRate + CORNER_WIDTH - 2,
					frame.left + 2, frame.bottom + CORNER_WIDTH - 2, paint);
			canvas.drawRect(frame.right - ScreenRate + CORNER_WIDTH - 2, frame.bottom - 2, frame.right + CORNER_WIDTH
					- 2, frame.bottom + CORNER_WIDTH - 2, paint);
			canvas.drawRect(frame.right - 2, frame.bottom - ScreenRate + CORNER_WIDTH - 2, frame.right + CORNER_WIDTH
					- 2, frame.bottom + CORNER_WIDTH - 2, paint);

			slideTop += SPEEN_DISTANCE;
			if (slideTop >= frame.bottom) {
				slideTop = frame.top;
			}

			Rect lineRect = new Rect();
			lineRect.left = frame.left;
			lineRect.right = frame.right;
			lineRect.top = slideTop;
			lineRect.bottom = slideTop + 18;
			canvas.drawBitmap(((BitmapDrawable) (getResources().getDrawable(R.drawable.qr_scan_line))).getBitmap(),
					null, lineRect, paint);

			
			paint.setColor(Color.WHITE);    
			paint.setTextSize(TEXT_SIZE * density);    
			paint.setAlpha(0x40);    
			paint.setTypeface(Typeface.DEFAULT_BOLD);   
			String text = getResources().getString(R.string.scan_text);  
			float textWidth = paint.measureText(text);  
			canvas.drawText(text, (width - textWidth)/2, (float) (frame.bottom + (float)TEXT_PADDING_TOP *density), paint) ;
            postInvalidateDelayed(ANIMATION_DELAY, frame.left, frame.top, frame.right, frame.bottom);

		}
	}
	
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	public void drawViewfinder() {
		Bitmap resultBitmap = this.resultBitmap;
		this.resultBitmap = null;
		if (resultBitmap != null) {
			resultBitmap.recycle();
		}
		invalidate();
	}

	/**
	 * Draw a bitmap with the result points highlighted instead of the live
	 * scanning display.
	 * 
	 * @param barcode
	 *            An image of the decoded barcode.
	 */
	public void drawResultBitmap(Bitmap barcode) {
		resultBitmap = barcode;
		invalidate();
	}

	public void addPossibleResultPoint(ResultPoint point) {
		possibleResultPoints.add(point);
	}
}
