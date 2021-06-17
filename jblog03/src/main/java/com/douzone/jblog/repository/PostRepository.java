package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public void insertWrite(PostVo postvo) {
		sqlSession.insert("post.insertWrite", postvo);
	}

	public void deletePost(Long categoryNo) {
		sqlSession.delete("post.delete", categoryNo);
	}

	public PostVo getPost(Long postNo, Long categoryNo) {
		Map<String, Long> map = new HashMap<>();
		map.put("postNo", postNo);
		map.put("categoryNo", categoryNo);
		return sqlSession.selectOne("post.getpost", map);
	}

	public List<PostVo> getPostList(Long categoryNo) {
		return sqlSession.selectList("post.getPostList", categoryNo);
	}

}
