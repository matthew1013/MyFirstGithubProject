package com.anyonehelps.model;

import java.io.Serializable;

/**
 * 权限url 实体类
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-09-24
 * 
 */
public class Authority_url implements Serializable {

	private static final long serialVersionUID = -8031699207761782766L;
	private String authority_id;
	public String name;
	public String menu_id;
	
	
	public Authority_url() {
    }
	
	public Authority_url( String authority_id,String name,String meau_id) {
		this.authority_id = authority_id;
		this.name = name;
		this.menu_id=meau_id;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getAuthoriy_id() {
		return authority_id;
	}

	public void setAuthoriy_id(String authoriyId) {
		authority_id = authoriyId;
	}

	public String getMeau_id() {
		return menu_id;
	}
	public void setMeau_id(String meauId) {
		menu_id = meauId;
	}
	
	 @Override
	    public String toString() {
	        return "Authority_url [authoriy_id=" + authority_id + ", name=" + name + ", meau_id=" + menu_id + "]";
	    }

}
