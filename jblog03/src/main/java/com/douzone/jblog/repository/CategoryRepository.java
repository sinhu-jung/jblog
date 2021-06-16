package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	
	@Autowired
	private SqlSession sqlSession;

	public boolean insertCategory(String category, String title, String id) {
		String desc= title + "에 오신 것을 환영 합니다.";
		Map<String, String> map = new HashMap<>();
		map.put("c", category);
		map.put("d", desc);
		map.put("i", id);
		
		int count = sqlSession.insert("category.insert", map);
		return count == 1;
	}

	public List<CategoryVo> findById(String id) {
		return sqlSession.selectList("category.findById", id);
	}

}
