package com.anyonehelps.model;

import java.io.Serializable;

/**
 * 权限控制实体类（绑定权限与可操作url）
 * create chenkanghua 
 * phone +8615818853022
 * email 846645133@qq.com
 * at 2015-09-24
 * 
 */
public class Authority  implements Serializable{
	private static final long serialVersionUID = 3037774521572346372L;
	private String authority_id;
	private String users_id;
	//private List<Authority> authoritys;
	
	public Authority( String authorityId,String users_id) {
		this.authority_id = authorityId;
		this.users_id = users_id;
	}
	
	public String getAuthority_id() {
		return authority_id;
	}

	public void setAuthority_id(String authoriyId) {
		authority_id = authoriyId;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	@Override
    public String toString() {
        return "Authority [authoriy_id=" + authority_id + ", users_id=" + users_id + "]";
    }
	
}
