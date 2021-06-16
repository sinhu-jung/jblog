package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public boolean insertNew(String title, String logo, String id) {
		Map<String, String> map = new HashMap<>();
		map.put("t", title);
		map.put("l", logo);
		map.put("i", id);
		
		int count = sqlSession.insert("blog.insert", map);
		return count == 1;
	}
	
}
