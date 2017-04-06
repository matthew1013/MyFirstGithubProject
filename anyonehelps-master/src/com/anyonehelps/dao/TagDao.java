package com.anyonehelps.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.anyonehelps.model.Education;
import com.anyonehelps.model.Tag;

public interface TagDao {  
	public int insertTag(Tag tag ) throws Exception;
	public Education getById(@Param("id") String id) throws Exception;
	public List<Tag> getAll() throws Exception;
	public int deleteById(@Param("id") String id) throws Exception;
}
