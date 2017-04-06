package com.anyonehelps.dao;
import java.util.List;

import com.anyonehelps.model.Authority;



public interface AuthorityuserDao {
	
	//插入员工权限信息
	public int insertAuthority(List<Authority> authoritys) throws Exception;
	//根据员工ID删除对应的权限
	public int dellAuthorityUserById( String id)throws Exception;
	//修改员工权限
	public int modifyAuthority( List<Authority> authority,String id) throws Exception;
	
}
