package com.abadou.wx.model;

import java.io.Serializable;

/**
 * 获取微信config接口注入权限验证配置
 * 
 * @author abadou-hepeng
 *
 */
public class WXJSConfig implements Serializable{

	private static final long serialVersionUID = -7856812773352643750L;

	/**
	 * 开启调试模式,调用的所有api的返回值会在客户端alert出来， 若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，
	 * 仅在pc端时才会打印。
	 */
	private boolean debug;

	private String appId;// 必填，公众号的唯一标识
	private String timestamp;// 必填，生成签名的时间戳
	private String nonceStr;// 必填，生成签名的随机串
	private String sigature;// 必填，签名，见附录1
	private String[] jsPaiList; // 必填，需要使用的JS接口列表
	
	public boolean isDebug() {
		return debug;
	}
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSigature() {
		return sigature;
	}
	public void setSigature(String sigature) {
		this.sigature = sigature;
	}
	public String[] getJsPaiList() {
		return jsPaiList;
	}
	public void setJsPaiList(String[] jsPaiList) {
		this.jsPaiList = jsPaiList;
	}
	
	

}
