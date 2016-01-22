package com.nongziwang.entity;

public class UsesBean {
	private String usesid;
	private String usesname;
	@Override
	public String toString() {
		return "UsesBean [usesid=" + usesid + ", usesname=" + usesname + "]";
	}
	public  UsesBean(){
		
	}
	public String getUsesid() {
		return usesid;
	}
	public void setUsesid(String usesid) {
		this.usesid = usesid;
	}
	public String getUsesname() {
		return usesname;
	}
	public void setUsesname(String usesname) {
		this.usesname = usesname;
	}

}
