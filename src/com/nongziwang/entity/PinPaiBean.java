package com.nongziwang.entity;

public class PinPaiBean {
	private String pinpaiid;
	private String pinpainame;
	
	@Override
	public String toString() {
		return "PinPaiBean [pinpaiid=" + pinpaiid + ", pinpainame="
				+ pinpainame + "]";
	}


	public PinPaiBean(){
		
	}
	
	
	public String getPinpaiid() {
		return pinpaiid;
	}
	public void setPinpaiid(String pinpaiid) {
		this.pinpaiid = pinpaiid;
	}
	public String getPinpainame() {
		return pinpainame;
	}
	public void setPinpainame(String pinpainame) {
		this.pinpainame = pinpainame;
	}
}
