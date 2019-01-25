package com.viverselftest.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * PasswordUtil
 */
public class PasswordUtil {

	/**
	 * 随机生成number位密码
	 * @return
	 */
	public static Map randomGeneratePasword(int number) {
		String randomStr = "0123456789abcdefghijklmnopqrstuvwxyz#$&";
		String pwdStr = "";
		for (int i = 0; i < number; i++) {
			pwdStr += randomStr.charAt(getRandom(38));
		}

		return generatePassword(MD5Util.MD5(pwdStr));

	}

	/**
	 * 密码生成
	 * @param inputPassword
	 * @return
	 */
	public static Map generatePassword(String inputPassword) {
		return generatePasswordWidthMd5pwd(MD5Util.MD5(inputPassword));
	}

	/**
	 * 密码生成
	 * @param md5Password
	 * @return
	 */
	public static Map generatePasswordWidthMd5pwd(String md5Password) {
		Map<String, String> pwdMap = new HashMap<>();
		char[] saltCharArray = new char[8];
		for (int i = 0; i < 8; i++) {
			int tempRandom = getRandom(31);
			saltCharArray[i] = md5Password.charAt(tempRandom);
		}
		String salt = String.valueOf(saltCharArray);
		String pwd = MD5Util.MD5(md5Password + salt);

		pwdMap.put("password", pwd);
		pwdMap.put("salt", salt);
		return pwdMap;
	}

	/**
	 * 获取0-number随机数
	 * @param number
	 * @return
	 */
	public static int getRandom(int number) {
		Random random = new Random();
		int result = random.nextInt(number);
		return result + 1;
	}

	/**
	 * 判断用户输入的密码是否正确
	 * @param inputPassword 用户输入的密码
	 * @param salt 盐
	 * @param password 数据库中存的密码
	 * @return
	 */
	public static boolean isEqualPassword(String inputPassword, String salt, String password) {
		return isEqualMd5Password(MD5Util.MD5(inputPassword), salt, password);
	}

	/**
	 * 判断用户密码是否正确
	 * @param md5Password
	 * @param salt
	 * @param password
	 * @return
	 */
	public static boolean isEqualMd5Password(String md5Password, String salt, String password) {
		String md5 = MD5Util.MD5(md5Password + salt);
		if (md5.equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] args) {
		Map map = generatePassword("111111");
		System.out.println("pwd:" + map.get("password") + " salt:" + map.get("salt"));
	}

}
