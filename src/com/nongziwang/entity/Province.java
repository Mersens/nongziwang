package com.nongziwang.entity;
/**
 * @author Mersens
 * ʡ��
 */
public class Province {
	private  String provinceid;//ʡ��id
	private String name;//ʡ������
	
	public Province(){
		
	}

	public Province(String provinceid, String name) {
		super();
		this.provinceid = provinceid;
		this.name = name;
	}
	
	/** setter,getter���� */
	
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
