package com.nongziwang.entity;
/**
 * 
 * @author Mersens
 * ����
 */
public class Area {
	private String areaid;//����id
	private String name;//��������
	private String cityid;//����id(���Լ��)

	public Area(){
		
	}
	public Area(String areaid, String name, String cityid) {
		super();
		this.areaid = areaid;
		this.name = name;
		this.cityid = cityid;
	}
	
	/** setter,getter���� */
	
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
