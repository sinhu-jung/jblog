package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.PostVo;

@Service
public class PostService {
	
	@Autowired
	private PostRepository postRepository;

	public void addwrite(PostVo postvo) {
		postRepository.insertWrite(postvo);
	}

	public PostVo getPost(Long postNo, Long categoryNo, String id) {
		return postRepository.getPost(postNo, categoryNo, id);
	}

	public List<PostVo> getPostList(Long categoryNo, String id) {
		return postRepository.getPostList(categoryNo, id);
	}
	
}
