package com.nongziwang.entity;

public class FootprintBean {
	private String chanpinid;// 产品id
	 private String title ;//产品名称
	 private String jiage;// 产品价格
	 private String chanpinimg;// 产品图片
	 private String province;// 所在省份
	 private String cityname ;//所在城市
	 
	 @Override
	public String toString() {
		return "FootprintBean [chanpinid=" + chanpinid + ", title=" + title
				+ ", jiage=" + jiage + ", chanpinimg=" + chanpinimg
				+ ", province=" + province + ", cityname=" + cityname + "]";
	}

	public FootprintBean(){
		 
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
	public String getChanpinimg() {
		return chanpinimg;
	}
	public void setChanpinimg(String chanpinimg) {
		this.chanpinimg = chanpinimg;
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

}
