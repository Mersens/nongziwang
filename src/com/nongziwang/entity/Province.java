package com.nongziwang.entity;
/**
 * @author Mersens
 * 省份
 */
public class Province {
	private  String provinceid;//省份id
	private String name;//省份名称
	
	public Province(){
		
	}

	public Province(String provinceid, String name) {
		super();
		this.provinceid = provinceid;
		this.name = name;
	}
	
	/** setter,getter方法 */
	
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "Province [provinceid=" + provinceid + ", name=" + name + "]";
	}
}
