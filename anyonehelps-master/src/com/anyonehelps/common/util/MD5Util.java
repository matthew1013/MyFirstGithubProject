package com.anyonehelps.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.log4j.Logger;

public class MD5Util {
	private static Logger logger = Logger.getLogger(MD5Util.class);
	public static void main(String[] args) {
	    //System.out.println(encode("username=zhangjq&email=985951143@qq.com&stime=1422254169272&key=usitaoPsF9527"));
		int j=1;
		int n=0;
		while(n<10000){
			n=n+1;
			
			String tmp=String.valueOf(n);
			if(tmp.length()==2){
				int total = Integer.parseInt(String.valueOf(tmp.charAt(0))) +Integer.parseInt(String.valueOf(tmp.charAt(1))) ;
				
				if(total>12&&total<26){
					//System.out.println("00"+tmp);
					//System.out.println("00"+tmp.charAt(0)+tmp.charAt(1));
					if((tmp.contains("1")||tmp.contains("4")||tmp.contains("7"))&&(tmp.contains("3")||tmp.contains("6")||tmp.contains("9"))){
						if(j%6==0){
							System.out.println("00"+tmp);
						}else{
							System.out.print("00"+tmp+"      ");
						}
						j=j+1;
					}
					
				}
				continue;
			}
			if(tmp.length()==3){
				int total = Integer.parseInt(String.valueOf(tmp.charAt(0))) +Integer.parseInt(String.valueOf(tmp.charAt(1)))+Integer.parseInt(String.valueOf(tmp.charAt(2))) ;
				if(total>12&&total<26){
					
					//System.out.println("0"+tmp.charAt(0)+tmp.charAt(1)+tmp.charAt(2));
					if((tmp.contains("1")||tmp.contains("4")||tmp.contains("7"))&&(tmp.contains("3")||tmp.contains("6")||tmp.contains("9"))){
						if(j%6==0){
							System.out.println("0"+tmp);
						}else{
							System.out.print("0"+tmp+"      ");
						}
						j=j+1;
					}
				}
				continue;
			}
			
			if(tmp.length()==4){
				int total = Integer.parseInt(String.valueOf(tmp.charAt(0))) +Integer.parseInt(String.valueOf(tmp.charAt(1)))+Integer.parseInt(String.valueOf(tmp.charAt(2)))+Integer.parseInt(String.valueOf(tmp.charAt(3)))  ;
				
				if(total>12&&total<26){
					//System.out.println(tmp.charAt(0)+tmp.charAt(1)+tmp.charAt(2)+tmp.charAt(3));
					if((tmp.contains("0")||tmp.contains("2")||tmp.contains("5")||tmp.contains("8"))&&(tmp.contains("1")||tmp.contains("4")||tmp.contains("7"))&&(tmp.contains("3")||tmp.contains("6")||tmp.contains("9"))){
						if(j%6==0){
							System.out.println(tmp);
						}else{
							System.out.print(tmp+"      ");
						}
						j=j+1;
					}
					
				}
				continue;
			}
			//System.out.println();
			
		}
    }

	/**
	 * 对string对象进行MD5 进行编码
	 * 
	 * @param string
	 * @return
	 */
	public static String encode(String string) {
		if (string == null || "".equals(string.trim())) {
			return null;
		}
		try {
			return generateMD5(string.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
			throw new RuntimeException(e);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 产生一个随机数(36位)
	 * 
	 * @return
	 */
	public static String random() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 对source进行MD5进行加密
	 * 
	 * @param source
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	private static String generateMD5(byte[] source)
			throws NoSuchAlgorithmException {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(source);
		byte tmp[] = md.digest();
		char str[] = new char[16 * 2];
		int k = 0;
		for (int i = 0; i < 16; i++) {
			byte byte0 = tmp[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}
		s = new String(str);
		return s;
	}
}
