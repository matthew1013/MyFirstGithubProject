package com.anyonehelps.dao;

import java.util.List;

import com.anyonehelps.model.Seo;


public interface SeoDao {  
	public List<Seo> getAll() throws Exception;
}
