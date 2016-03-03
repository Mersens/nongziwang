package com.nongziwang.entity;

public class NewsDatialBean {
	private String newsid;
	private String title;
	private String center;
	private String newsimg;
	private String addtime;
	private String newsurl;

	@Override
	public String toString() {
		return "NewsDatialBean [newsid=" + newsid + ", title=" + title
				+ ", center=" + center + ", newsimg=" + newsimg + ", addtime="
				+ addtime + "]";
	}

	public String getNewsurl() {
		return newsurl;
	}
	public void setNewsurl(String newsurl) {
		this.newsurl = newsurl;
	}
	public NewsDatialBean(){
		
	}
	public String getNewsid() {
		return newsid;
	}

	public void setNewsid(String newsid) {
		this.newsid = newsid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCenter() {
		return center;
	}

	public void setCenter(String center) {
		this.center = center;
	}

	public String getNewsimg() {
		return newsimg;
	}

	public void setNewsimg(String newsimg) {
		this.newsimg = newsimg;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}


}
