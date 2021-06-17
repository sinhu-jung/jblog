package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	public List<CategoryVo> findById(String id) {
		return categoryRepository.findById(id) ;
	}

	public void addcategory(CategoryVo categoryVo) {
		categoryRepository.insertCategory(categoryVo);
	}

	public int findCount() {
		return 0;
	}

	public void delCategory(Long categoryNo) {
		postRepository.deletePost(categoryNo);
		categoryRepository.deleteCategory(categoryNo);
	}

	public Long findminCategory() {
		return categoryRepository.findminCategory();
	}

}
