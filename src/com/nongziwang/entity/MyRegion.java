package com.nongziwang.entity;

public class MyRegion {
	private String id;
	private String name;
	private String parentid;
	
	public MyRegion(String id, String name, String parentid) {
		this.id = id;
		this.name = name;
		this.parentid = parentid;
	}
	public MyRegion(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
		return name;
	}
}
