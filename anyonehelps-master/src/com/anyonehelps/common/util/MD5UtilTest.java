package com.anyonehelps.common.util;
import java.security.MessageDigest;

public class MD5UtilTest {
	
	 public static void main(String[] args) {
	    	try{
		    	System.out.println(
		    		toMD5("username=zhangjq&stime=1422192384514&key=yuejiaPsF1257".getBytes("UTF8"))
		    	); 
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	    }
	    public static String toMD5(byte[] source) {    	
	    	try{
	    		MessageDigest md = MessageDigest.getInstance("MD5");
	    	    md.update( source );    	    
	    	    StringBuffer buf=new StringBuffer();    	    
	    	    for(byte b:md.digest())
	    	    	buf.append(String.format("%02x", b&0xff) );    	     
	    	    return buf.toString();
	    	}catch( Exception e ){
	    		e.printStackTrace(); return null;
	    	}  
	    }
	}
