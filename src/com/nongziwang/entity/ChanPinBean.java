package com.nongziwang.entity;

public class ChanPinBean {
	private String chanpinid ;//��Ʒid
	private String pinpai;// ��ƷƷ��
	private String title ;//��Ʒ����
	private String keyword ;//�ؼ���
	private String miaoshu ;//����
	private String detail ;//��ϸ����
	private String jiage ;//�۸�
	private String ishot ;//�Ƿ�����
	private String unit ;//��λ
	private String dianpuid ;//����id
	private String gongsiid ;//��˾id
	private String userid ;//�û�id
	private String xinxiststic ;//��Ʒ״̬
	private String htmlid ;//��Ʒhtmlid
	private String addtime ;//��Ʒ����ʱ��
	private String chanpinimg ;//��ƷͼƬ
	private boolean canRemove = true;//��ʾ�Ƿ����ɾ��


	public ChanPinBean(){
		
	}
	
	public String getChanpinid() {
		return chanpinid;
	}
	public void setChanpinid(String chanpinid) {
		this.chanpinid = chanpinid;
	}
	public String getPinpai() {
		return pinpai;
	}
	public void setPinpai(String pinpai) {
		this.pinpai = pinpai;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getMiaoshu() {
		return miaoshu;
	}
	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getJiage() {
		return jiage;
	}
	public void setJiage(String jiage) {
		this.jiage = jiage;
	}
	public String getIshot() {
		return ishot;
	}
	public void setIshot(String ishot) {
		this.ishot = ishot;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getDianpuid() {
		return dianpuid;
	}
	public void setDianpuid(String dianpuid) {
		this.dianpuid = dianpuid;
	}
	public String getGongsiid() {
		return gongsiid;
	}
	public void setGongsiid(String gongsiid) {
		this.gongsiid = gongsiid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getXinxiststic() {
		return xinxiststic;
	}
	public void setXinxiststic(String xinxiststic) {
		this.xinxiststic = xinxiststic;
	}
	public String getHtmlid() {
		return htmlid;
	}
	public void setHtmlid(String htmlid) {
		this.htmlid = htmlid;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getChanpinimg() {
		return chanpinimg;
	}
	public void setChanpinimg(String chanpinimg) {
		this.chanpinimg = chanpinimg;
	}
	public boolean isCanRemove() {
		return canRemove;
	}
	public void setCanRemove(boolean canRemove) {
		this.canRemove = canRemove;
	}
	
	@Override
	public String toString() {
		return title;
	}
}