package com.nongziwang.entity;

public class ProductBean {
	private String chanpinid;
	private String title;
	private String jiage;
	private String province;
	private String cityname;
	private String chanpinimg;
	
	public ProductBean(){
		
	}
	
	
	@Override
	public String toString() {
		return "ProductBean [chanpinid=" + chanpinid + ", title=" + title
				+ ", jiage=" + jiage + ", province=" + province + ", cityname="
				+ cityname + ", chanpinimg=" + chanpinimg + "]";
	}
	
	public String getChanpinid() {
		return chanpinid;
	}
	public void setChanpinid(String chanpinid) {
		this.chanpinid = chanpinid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getJiage() {
		return jiage;
	}
	public void setJiage(String jiage) {
		this.jiage = jiage;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCityname() {
		return cityname;
	}
	public void setCityname(String cityname) {
		this.cityname = cityname;
	}
	public String getChanpinimg() {
		return chanpinimg;
	}
	public void setChanpinimg(String chanpinimg) {
		this.chanpinimg = chanpinimg;
	}
	
	
	

}
