package com.nongziwang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtils {
	/**
	 * @author Mersens ��֤�Ƿ�Ϊ�ֻ��Ÿ�ʽ
	 * @param num
	 * @return
	 */
	public static boolean isMobileNum(String num) {
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(num);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Mersens ��֤�Ƿ�ΪQQ��ʽ
	 * @param num
	 * @return
	 */
	public static boolean isQQNum(String num) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(num);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @author Mersens ��֤�Ƿ�Ϊ�����ʽ
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
		Pattern p = Pattern.compile(strPattern);
		Matcher m = p.matcher(strEmail);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @author Mersens ��֤�Ƿ�����ո�
	 * @param str
	 * @return
	 */
	public static boolean isContainsSpace(String str) {
		return str.contains(" ");
	}

	/**
	 * @author Mersens ��֤�ܴa�L���Ƿ���6
	 * @param str
	 * @return
	 */
	public static boolean isLeanth(String str) {
		if (str.trim().length() < 6) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @author Mersens �������6λ��֤��
	 * @return
	 */
	public static String getRandom() {
		return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

	}
	
	public static String getFormatTelNum(String num){
		StringBuffer sbf=new StringBuffer();
		if(num==null || "".equals(num)){
			return null;
		}
		if(num.length()!=11){
			return null;
		}
		sbf.append(num.substring(0,3)).append("****").append(num.substring(num.length()-4,num.length()));
		return sbf.toString();
		
	}
}
