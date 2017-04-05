package com.abadou.wx.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import com.abadou.wx.model.WXJSConfig;
import com.alibaba.fastjson.JSONObject;

/**
 * ΢�Ź�����
 * 
 * @author abadou-hepeng
 *
 */
public class WXUtils {

	/**
	 * ��ȡ����ƾ֤
	 */
	private static final String access_token = "access_token";
	/**
	 * ƾ֤��Чʱ�䣬��λ����
	 */
	private static final String expires_in = "expires_in";

	private static final String appid = "";
	private static final String appSecret = "";

	
	/**
	 * js��url
	 */
	private static final String js_url = "";

	private static WXJSConfig wxjsConfig = new WXJSConfig();
	
	static{
		wxjsConfig.setAppId(WXUtils.appid);
	}

	/**
	 * ��ȡ΢��access_token
	 * 
	 * @param appid
	 * @param appSecret
	 * @return
	 */
	public static String getTokenAccess(String appid, String appSecret) {
		String tokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid" + "=" + appid
				+ "&secret=" + appSecret + "";
		String access_token_json = HttpUtils.doGet(tokenUrl);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(access_token_json);
		String access_tokenStr = (String) jsonObject.get(WXUtils.access_token);
		return access_tokenStr;
	}

	/**
	 * ��ȡʱ���
	 * 
	 * @return
	 */
	public static String createTimestamp() {
		String timestamp = Long.toString(System.currentTimeMillis() / 1000);
		wxjsConfig.setTimestamp(timestamp);
		return timestamp;
	}

	/**
	 * ��ȡ����ַ���
	 */
	@SuppressWarnings("unused")
	private static String createNonceStr() {
		String nonceStr = UUID.randomUUID().toString();
		wxjsConfig.setNonceStr(nonceStr);
		return nonceStr;
	}

	/**
	 * ͨ��accessToken��ȡjsapi_ticket
	 * 
	 * @param accessToken
	 * @return
	 */
	public static String getWxJsTicket(String accessToken) {
		String jsapi_ticket_str = "";
		try {
			String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + accessToken
					+ "&type=jsapi";
			jsapi_ticket_str = HttpUtils.doGet(jsapiTicketUrl);
		} catch (Exception e) {
			jsapi_ticket_str = "";
		}
		return jsapi_ticket_str;

	}

	/**
	 * ��jsapi_ticket,noncestr,timestamp,jsUrlƴ��,ʹ��SHA1�����㷨,��������ǩ��
	 * 
	 * @param jsapi_ticket
	 * @param noncestr
	 * @param timestamp
	 * @param jsUrl
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sha1Signature(String jsapi_ticket, String noncestr, String timestamp, String jsUrl)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		// ע���������������ȫ��Сд���ұ�������
		String str = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url="
				+ jsUrl;
		MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		crypt.reset();
		crypt.update(str.getBytes("UTF-8"));
		String signature = byteToHexString(crypt.digest());
		return signature;

	}

	/**
	 * ��ָ��byte����ת����16�����ַ���
	 * 
	 * @param b
	 * @return
	 */
	public static String byteToHexString(byte[] b) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hexString.append(hex.toUpperCase());
		}
		return hexString.toString();
	}

	/**
	 * ����jsConfigȨ����Ϣ
	 * 
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static WXJSConfig getConfig() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String signature = sha1Signature(getWxJsTicket(getTokenAccess(WXUtils.appid, WXUtils.appSecret)),
				wxjsConfig.getNonceStr(), wxjsConfig.getTimestamp(), WXUtils.js_url);
		wxjsConfig.setSigature(signature);
		
		//wxjsConfig.setJsPaiList(jsPaiList);
		return wxjsConfig;
	}
}
