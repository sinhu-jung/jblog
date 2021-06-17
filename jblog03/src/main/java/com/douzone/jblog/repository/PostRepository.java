package com.douzone.jblog.repository;

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

}
