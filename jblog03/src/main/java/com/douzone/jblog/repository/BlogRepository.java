package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

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

	public BlogVo findById(String id) {
		return sqlSession.selectOne("blog.findById", id);
	}

	public void update(BlogVo vo) {
		sqlSession.update("blog.update", vo);
	}
	
}
