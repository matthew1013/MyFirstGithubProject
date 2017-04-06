package com.anyonehelps.common.util;

import java.util.List;

import com.anyonehelps.model.Seo;


public class SeoUtil {
	public static List<Seo> seos;
	
	public static Seo getSeoByWebFlag(String Webflag){
		Seo seo = null;
		if(seos!=null){
			for(Seo s:seos){
				if(Webflag.equals(s.getWebFlag())){
					seo = s;
				}
			}
		}
		
		return seo;
		
	}
}
