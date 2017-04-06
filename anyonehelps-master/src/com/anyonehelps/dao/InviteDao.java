package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Invite;

public interface InviteDao {  
	public int insertInvites(@Param("invites") List<Invite> invites ) throws Exception;
	public int getCountByUserId(@Param("userId") String userId, @Param("demandId") String demandId ) throws Exception;
}
