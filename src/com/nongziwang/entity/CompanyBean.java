package com.nongziwang.entity;

public class CompanyBean {
	private String dianpuid;
	private String gongsiid;
	private String gongsiname;
	private String dianputype;
	private String chanpinsum;
	private String zhuyingchanpin;
	
	@Override
	public String toString() {
		return "CompanyBean [dianpuid=" + dianpuid + ", gongsiid=" + gongsiid
				+ ", gongsiname=" + gongsiname + ", dianputype=" + dianputype
				+ ", chanpinsum=" + chanpinsum + ", zhuyingchanpin="
				+ zhuyingchanpin + "]";
	}

	public CompanyBean(){
		
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
	public String getGongsiname() {
		return gongsiname;
	}
	public void setGongsiname(String gongsiname) {
		this.gongsiname = gongsiname;
	}
	public String getDianputype() {
		return dianputype;
	}
	public void setDianputype(String dianputype) {
		this.dianputype = dianputype;
	}
	public String getChanpinsum() {
		return chanpinsum;
	}
	public void setChanpinsum(String chanpinsum) {
		this.chanpinsum = chanpinsum;
	}
	public String getZhuyingchanpin() {
		return zhuyingchanpin;
	}
	public void setZhuyingchanpin(String zhuyingchanpin) {
		this.zhuyingchanpin = zhuyingchanpin;
	}

}
