package com.nongziwang.entity;

public class MyRegion {
	private String id;//地区id
	private String name;//地区名字
	private String parentid;//地区的父类id
	
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
