package com.nongziwang.entity;

import android.graphics.drawable.Drawable;

public class ShareBean {
	private String mName;
	private Drawable mDrawable;
	private Type type;
	
	public ShareBean(String mName, Drawable mDrawable,Type type) {
		super();
		this.mName = mName;
		this.mDrawable = mDrawable;
		this.type=type;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}

	public String getmName() {
		return mName;
	}
	public void setmName(String mName) {
		this.mName = mName;
	}
	public Drawable getmDrawable() {
		return mDrawable;
	}
	public void setmDrawable(Drawable mDrawable) {
		this.mDrawable = mDrawable;
	}

	public enum Type{
		QQ,QZONE,WEIXIN,SINA,ALIPAY,WXCIRCLE;
	}
	
}
