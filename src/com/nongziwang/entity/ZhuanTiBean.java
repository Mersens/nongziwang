package com.nongziwang.entity;

public class ZhuanTiBean {
	private String zhuantiid;
	private String zhuantiname;
	private String  imgsrc;
	private String keywords;
	private String center;
	private String addtime;
	private String zhuantiurl;


	public ZhuanTiBean(){
		
	}

	public String getZhuantiurl() {
		return zhuantiurl;
	}

	public void setZhuantiurl(String zhuantiurl) {
		this.zhuantiurl = zhuantiurl;
	}
	public String getZhuantiid() {
		return zhuantiid;
	}
	public void setZhuantiid(String zhuantiid) {
		this.zhuantiid = zhuantiid;
	}
	public String getZhuantiname() {
		return zhuantiname;
	}
	public void setZhuantiname(String zhuantiname) {
		this.zhuantiname = zhuantiname;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
	
	@Override
	public String toString() {
		return "ZhuanTiBean [zhuantiid=" + zhuantiid + ", zhuantiname="
				+ zhuantiname + ", imgsrc=" + imgsrc + ", keywords=" + keywords
				+ ", center=" + center + ", addtime=" + addtime + "]";
	}
}
