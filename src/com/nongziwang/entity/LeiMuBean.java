package com.nongziwang.entity;

public class LeiMuBean {
	private String leimuid;
	private String name;
	private String parentid;

	public LeiMuBean() {

	}

	public LeiMuBean(String leimuid, String name, String parentid) {
		super();
		this.leimuid = leimuid;
		this.name = name;
		this.parentid = parentid;
	}

	public String getLeimuid() {
		return leimuid;
	}

	public void setLeimuid(String leimuid) {
		this.leimuid = leimuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Override
	public String toString() {
		return "LeiMuBean [leimuid=" + leimuid + ", name=" + name
				+ ", parentid=" + parentid + "]";
	}

}
