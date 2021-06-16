package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.UserRepository;
import com.douzone.jblog.vo.UserVo;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private CategoryRepository categoriRepository;

	public void join(UserVo vo) {
		String title = vo.getId() + "의 블로그";
		String logo = "/assets/images/profile.jpg";
		String category = "myblog";
		
		userRepository.insertUser(vo);
		blogRepository.insertNew(title, logo, vo.getId());
		categoriRepository.insertCategory(category, title, vo.getId());
	}

	public UserVo getUser(String id, String password) {
		UserVo vo = userRepository.findAll(id, password);
		return vo;
	}
	
}
