package com.nongziwang.entity;

public class GongsiDetailBean {
	
	private String gongsiname;// ��˾����
	private String dianputype;// ��Ӫģʽ
	private String diqu;// ���ڵ���
	private String zhuyingchanpin;// ��Ӫ��Ʒ
	private String lianxiren;// ��ϵ��
	private String lianxirendianhua;// ��ϵ�˵绰
	private String lianxidizhi ;//��ϵ��ַ
	private String manyidu;// ����̶�ͼƬurl


	public GongsiDetailBean(){
		
	}
	
	
	public String getGongsiname() {
		return gongsiname;
	}
	public void setGongsiname(String gongsiname) {
		this.gongsiname = gongsiname;
	}
	public String getDianputype() {
		return dianputype;
	}
	public void setDianputype(String dianputype) {
		this.dianputype = dianputype;
	}
	public String getDiqu() {
		return diqu;
	}
	public void setDiqu(String diqu) {
		this.diqu = diqu;
	}
	public String getZhuyingchanpin() {
		return zhuyingchanpin;
	}
	public void setZhuyingchanpin(String zhuyingchanpin) {
		this.zhuyingchanpin = zhuyingchanpin;
	}
	public String getLianxiren() {
		return lianxiren;
	}
	public void setLianxiren(String lianxiren) {
		this.lianxiren = lianxiren;
	}
	public String getLianxirendianhua() {
		return lianxirendianhua;
	}
	public void setLianxirendianhua(String lianxirendianhua) {
		this.lianxirendianhua = lianxirendianhua;
	}
	public String getLianxidizhi() {
		return lianxidizhi;
	}
	public void setLianxidizhi(String lianxidizhi) {
		this.lianxidizhi = lianxidizhi;
	}
	public String getManyidu() {
		return manyidu;
	}
	public void setManyidu(String manyidu) {
		this.manyidu = manyidu;
	}
	
	
	@Override
	public String toString() {
		return "GongsiDetailBean [gongsiname=" + gongsiname + ", dianputype="
				+ dianputype + ", diqu=" + diqu + ", zhuyingchanpin="
				+ zhuyingchanpin + ", lianxiren=" + lianxiren
				+ ", lianxirendianhua=" + lianxirendianhua + ", lianxidizhi="
				+ lianxidizhi + ", manyidu=" + manyidu + "]";
	}

}
