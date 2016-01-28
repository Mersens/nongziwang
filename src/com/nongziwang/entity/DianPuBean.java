package com.nongziwang.entity;

import java.util.List;

public class DianPuBean {
	private String dianpuid;
	private String dianpuname;
	private String dianpulogo;
	private List<ChanPinBean> chanpinlist;
	
	public DianPuBean(){
		
	}
	
	
	public String getDianpuid() {
		return dianpuid;
	}
	public void setDianpuid(String dianpuid) {
		this.dianpuid = dianpuid;
	}
	public String getDianpuname() {
		return dianpuname;
	}
	public void setDianpuname(String dianpuname) {
		this.dianpuname = dianpuname;
	}
	public String getDianpulogo() {
		return dianpulogo;
	}
	public void setDianpulogo(String dianpulogo) {
		this.dianpulogo = dianpulogo;
	}
	public List<ChanPinBean> getChanpinlist() {
		return chanpinlist;
	}
	public void setChanpinlist(List<ChanPinBean> chanpinlist) {
		this.chanpinlist = chanpinlist;
	}
	
	



}
