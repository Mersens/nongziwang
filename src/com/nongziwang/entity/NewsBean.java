package com.nongziwang.entity;

public class NewsBean {
	private String newsid;
	private String title;
	private String center;
	private String imgsrc;
	private String addtime;

	public NewsBean(){
		
	}
	
	public NewsBean(String newsid, String title, String center, String imgsrc,
			String addtime) {
		super();
		this.newsid = newsid;
		this.title = title;
		this.center = center;
		this.imgsrc = imgsrc;
		this.addtime = addtime;
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
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	

	@Override
	public String toString() {
		return "NewsBean [newsid=" + newsid + ", title=" + title + ", center="
				+ center + ", imgsrc=" + imgsrc + ", addtime=" + addtime + "]";
	}
}
