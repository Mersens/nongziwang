package com.nongziwang.entity;

public class UserBean {
	private String userid;
	private String username;
	private String userpwd;
	private String userphone;
	private String qq;
	private String xingming;
	private String addtime;
	private String companyid;
	private String htmlid;
	private String touxiang;
	private String dianpuid;


	public UserBean(){
		
	}

	public String getDianpuid() {
		return dianpuid;
	}

	public void setDianpuid(String dianpuid) {
		this.dianpuid = dianpuid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public String getUserphone() {
		return userphone;
	}
	public void setUserphone(String userphone) {
		this.userphone = userphone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getHtmlid() {
		return htmlid;
	}
	public void setHtmlid(String htmlid) {
		this.htmlid = htmlid;
	}
	public String getTouxiang() {
		return touxiang;
	}
	public void setTouxiang(String touxiang) {
		this.touxiang = touxiang;
	}
	
	@Override
	public String toString() {
		return "UserBean [userid=" + userid + ", username=" + username
				+ ", userpwd=" + userpwd + ", userphone=" + userphone + ", qq="
				+ qq + ", xingming=" + xingming + ", addtime=" + addtime
				+ ", companyid=" + companyid + ", htmlid=" + htmlid
				+ ", touxiang=" + touxiang + "]";
	}

}
