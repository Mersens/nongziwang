package com.nongziwang.entity;
/**
 * 
 * @author Mersens
 * 城市
 */
public class City {
	private String cityid;//城市id
	private String name;//城市名称
	private String provinceid;//省份id(外键约束)

	public City(){
		
	}
	public City(String cityid, String name, String provinceid) {
		super();
		this.cityid = cityid;
		this.name = name;
		this.provinceid = provinceid;
	}
	
	/** setter,getter方法 */
	 
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	@Override
	public String toString() {
		return "City [cityid=" + cityid + ", name=" + name + ", provinceid="
				+ provinceid + "]";
	}

}
