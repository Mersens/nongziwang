package com.nongziwang.entity;

public class GongsiBean {
	private String gongsiid; //- 公司id
	private String gongsiname; //公司名称
	private String jiancheng; //公司简称
	private String miaoshu; //公司描述
	private String province; //所属省份id
	private String city; //所属城市id
	private String area; //所属区域id
	private String dizhi; //详细地址
	private String dianhua; //公司电话
	private String chuanzhen; //公司传真
	private String lianxiren; //联系人
	private String lianxidianhua; //联系人电话
	private String yingyezhizhao; //营业执照图片路径
	private String shuiwudengjizheng; //税务登记证图片路径
	private String zuzhijigoudaimazheng; //组织机构代码图片路径
	private String gsstatic;//公司状态

	@Override
	public String toString() {
		return "GongsiBean [gongsiid=" + gongsiid + ", gongsiname="
				+ gongsiname + ", jiancheng=" + jiancheng + ", miaoshu="
				+ miaoshu + ", provinceid=" + province + ", cityid=" + city
				+ ", areaid=" + area + ", dizhi=" + dizhi + ", dianhua="
				+ dianhua + ", chuanzhen=" + chuanzhen + ", lianxiren="
				+ lianxiren + ", lianxidianhua=" + lianxidianhua
				+ ", yingyezhizhao=" + yingyezhizhao + ", shuiwudengjizheng="
				+ shuiwudengjizheng + ", zuzhijigoudaimazheng="
				+ zuzhijigoudaimazheng + "]";
	}
	public GongsiBean(){
		
	}
	public String getGsstatic() {
		return gsstatic;
	}
	public void setGsstatic(String gsstatic) {
		this.gsstatic = gsstatic;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getGongsiid() {
		return gongsiid;
	}
	public void setGongsiid(String gongsiid) {
		this.gongsiid = gongsiid;
	}
	public String getGongsiname() {
		return gongsiname;
	}
	public void setGongsiname(String gongsiname) {
		this.gongsiname = gongsiname;
	}
	public String getJiancheng() {
		return jiancheng;
	}
	public void setJiancheng(String jiancheng) {
		this.jiancheng = jiancheng;
	}
	public String getMiaoshu() {
		return miaoshu;
	}
	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setAreaid(String area) {
		this.area = area;
	}
	public String getDizhi() {
		return dizhi;
	}
	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}
	public String getDianhua() {
		return dianhua;
	}
	public void setDianhua(String dianhua) {
		this.dianhua = dianhua;
	}
	public String getChuanzhen() {
		return chuanzhen;
	}
	public void setChuanzhen(String chuanzhen) {
		this.chuanzhen = chuanzhen;
	}
	public String getLianxiren() {
		return lianxiren;
	}
	public void setLianxiren(String lianxiren) {
		this.lianxiren = lianxiren;
	}
	public String getLianxidianhua() {
		return lianxidianhua;
	}
	public void setLianxidianhua(String lianxidianhua) {
		this.lianxidianhua = lianxidianhua;
	}
	public String getYingyezhizhao() {
		return yingyezhizhao;
	}
	public void setYingyezhizhao(String yingyezhizhao) {
		this.yingyezhizhao = yingyezhizhao;
	}
	public String getShuiwudengjizheng() {
		return shuiwudengjizheng;
	}
	public void setShuiwudengjizheng(String shuiwudengjizheng) {
		this.shuiwudengjizheng = shuiwudengjizheng;
	}
	public String getZuzhijigoudaimazheng() {
		return zuzhijigoudaimazheng;
	}
	public void setZuzhijigoudaimazheng(String zuzhijigoudaimazheng) {
		this.zuzhijigoudaimazheng = zuzhijigoudaimazheng;
	}
	
	
	

}
