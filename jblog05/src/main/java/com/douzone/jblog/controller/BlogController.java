package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.service.PostService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private PostService postService;
	
	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String index(
			@PathVariable("id") String id,
			@PathVariable("pathNo1") Optional<Long> pathNo1,
			@PathVariable("pathNo2") Optional<Long> pathNo2,
			Model model) {
		Long categoryNo = 0L;
		Long postNo = 0L;
		
		if(pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		} else if (pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
		} else {
			categoryNo = categoryService.findminCategory(id);
		}
		
		BlogVo blogVo = blogService.findById(id);
		PostVo postVo = postService.getPost(postNo, categoryNo, id);
		List<CategoryVo> categoryList = categoryService.findById(id);
		List<PostVo> postList = postService.getPostList(categoryNo, id);
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("postVo", postVo);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("postList", postList);
		return "blog/index";
	}
	
	@RequestMapping("/admin/basic")
	public String adminBasic(
			@PathVariable("id") String id, Model model) {
		BlogVo blogVo = blogService.findById(id);
		model.addAttribute("blogVo", blogVo);
		return "blog/admin/basic";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST)
	public String updateBasic(
			@PathVariable("id") String id,
			BlogVo vo,
			@RequestParam("logo-file") MultipartFile file) {
		String url = fileUploadService.restore(file);
		vo.setId(id);
		vo.setLogo(url);
		blogService.update(vo);
		return "redirect:/{id}/admin/basic";
	}
	
	@RequestMapping("/admin/category")
	public String adminCategory(
			@PathVariable("id") String id, Model model) {
		BlogVo blogVo = blogService.findById(id);
		List<CategoryVo> categoryList = categoryService.findById(id);
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		return "blog/admin/category";
	}
	
	@RequestMapping(value="/admin/category", method=RequestMethod.POST)
	public String addCategory(
			@PathVariable("id") String id,
			CategoryVo categoryVo ) {
		categoryVo.setBlogID(id);
		categoryService.addcategory(categoryVo);
		return "redirect:/{id}/admin/category";
	}
	
	@RequestMapping(value="/admin/category/del", method=RequestMethod.GET)
	public String deleteCategory(
			@RequestParam(value="categoriNo", required=true, defaultValue="") Long categoryNo ) {
		categoryService.delCategory(categoryNo);
		return "redirect:/{id}/admin/category";
	}
	
	@RequestMapping("/admin/write")
	public String adminWrite(
			@PathVariable("id") String id, Model model) {
		BlogVo blogVo = blogService.findById(id);
		List<CategoryVo> categoryList = categoryService.findById(id);
		
		model.addAttribute("blogVo", blogVo);
		model.addAttribute("categoryList", categoryList);
		return "blog/admin/write";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String addWrite(
			@PathVariable("id") String id,
			@RequestParam(value="category", required=true, defaultValue="") Long categoryNo,
			PostVo postvo
			) {
		
		postvo.setCategoriNo(categoryNo);
		postService.addwrite(postvo);
		return "redirect:/{id}";
	}
}
