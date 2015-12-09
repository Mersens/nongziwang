package com.nongziwang.entity;
/**
 * 
 * @author Mersens
 * 地区
 */
public class Area {
	private String areaid;//地区id
	private String name;//地区名称
	private String cityid;//城市id(外键约束)

	public Area(){
		
	}
	public Area(String areaid, String name, String cityid) {
		super();
		this.areaid = areaid;
		this.name = name;
		this.cityid = cityid;
	}
	
	/** setter,getter方法 */
	
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	
	@Override
	public String toString() {
		return "Area [areaid=" + areaid + ", name=" + name + ", cityid="
				+ cityid + "]";
	}
	
	
}
