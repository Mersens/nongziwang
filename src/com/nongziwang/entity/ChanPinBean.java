package com.nongziwang.entity;

import java.io.Serializable;

public class ChanPinBean  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String chanpinid ;//产品id
	private String pinpai;// 产品品牌
	private String title ;//产品名称
	private String keyword ;//关键字
	private String miaoshu ;//描述
	private String detail ;//详细介绍
	private String jiage ;//价格
	private String ishot ;//是否热门
	private String unit ;//单位
	private String dianpuid ;//店铺id
	private String gongsiid ;//公司id
	private String userid ;//用户id
	private String xinxiststic ;//产品状态
	private String htmlid ;//产品htmlid
	private String addtime ;//产品添加时间
	private String chanpinimg ;//产品图片
	private boolean canRemove = true;//标示是否可以删除
	private String province;// 省份名称
	private String cityname;// 城市名称
	private String dianpuname;// 店铺名称
	private String yongtuname;// 产品用途
	private String chengfenname;// 产品成分
	private String yunfei;//运费
	private String gslogo;

	public String getGslogo() {
		return gslogo;
	}

	public void setGslogo(String gslogo) {
		this.gslogo = gslogo;
	}

	public String getYunfei() {
		return yunfei;
	}

	public void setYunfei(String yunfei) {
		this.yunfei = yunfei;
	}

	public ChanPinBean(){
		
	}
	
	public String getYongtuname() {
		return yongtuname;
	}

	public void setYongtuname(String yongtuname) {
		this.yongtuname = yongtuname;
	}

	public String getChengfenname() {
		return chengfenname;
	}

	public void setChengfenname(String chengfenname) {
		this.chengfenname = chengfenname;
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

	public String getDianpuname() {
		return dianpuname;
	}

	public void setDianpuname(String dianpuname) {
		this.dianpuname = dianpuname;
	}

	public String getChanpinid() {
		return chanpinid;
	}
	public void setChanpinid(String chanpinid) {
		this.chanpinid = chanpinid;
	}
	public String getPinpai() {
		return pinpai;
	}
	public void setPinpai(String pinpai) {
		this.pinpai = pinpai;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getMiaoshu() {
		return miaoshu;
	}
	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getJiage() {
		return jiage;
	}
	public void setJiage(String jiage) {
		this.jiage = jiage;
	}
	public String getIshot() {
		return ishot;
	}
	public void setIshot(String ishot) {
		this.ishot = ishot;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDianpuid() {
		return dianpuid;
	}
	public void setDianpuid(String dianpuid) {
		this.dianpuid = dianpuid;
	}
	public String getGongsiid() {
		return gongsiid;
	}
	public void setGongsiid(String gongsiid) {
		this.gongsiid = gongsiid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getXinxiststic() {
		return xinxiststic;
	}
	public void setXinxiststic(String xinxiststic) {
		this.xinxiststic = xinxiststic;
	}
	public String getHtmlid() {
		return htmlid;
	}
	public void setHtmlid(String htmlid) {
		this.htmlid = htmlid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getChanpinimg() {
		return chanpinimg;
	}
	public void setChanpinimg(String chanpinimg) {
		this.chanpinimg = chanpinimg;
	}
	public boolean isCanRemove() {
		return canRemove;
	}
	public void setCanRemove(boolean canRemove) {
		this.canRemove = canRemove;
	}
	
	@Override
	public String toString() {
		return title;
	}
}
