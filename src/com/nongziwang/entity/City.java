package com.nongziwang.entity;
/**
 * 
 * @author Mersens
 * ����
 */
public class City {
	private String cityid;//����id
	private String name;//��������
	private String provinceid;//ʡ��id(���Լ��)

	public City(){
		
	}
	public City(String cityid, String name, String provinceid) {
		super();
		this.cityid = cityid;
		this.name = name;
		this.provinceid = provinceid;
	}
	
	/** setter,getter���� */
	 
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
