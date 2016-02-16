package com.nongziwang.entity;

public class IndexBean {
	private String name;
	private String id;
	private String imgsrc;


	public IndexBean(){
		
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgsrc() {
		return imgsrc;
	}
	public void setImgsrc(String imgsrc) {
		this.imgsrc = imgsrc;
	}
	
	@Override
	public String toString() {
		return "IndexBean [name=" + name + ", id=" + id + ", imgsrc=" + imgsrc
				+ "]";
	}

}
