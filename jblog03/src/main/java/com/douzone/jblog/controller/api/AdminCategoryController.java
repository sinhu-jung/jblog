package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.vo.CategoryVo;

@Controller
@RequestMapping("/category")
public class AdminCategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ResponseBody
	@RequestMapping("/list/{id}")
	public JsonResult adminCategory(
			@PathVariable("id") String id) {
		List<CategoryVo> categoryList = categoryService.findById(id);
		return JsonResult.success(categoryList);
	}
	
	@ResponseBody
	@RequestMapping("/addlist/{id}")
	public JsonResult addCategory(
			@PathVariable("id") String id,
			@RequestBody CategoryVo categoryVo){
		categoryVo.setBlogID(id);
		categoryService.addcategory(categoryVo);
		return JsonResult.success(categoryVo);
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public JsonResult deleteCategory(
			@RequestParam(value="no", required=true, defaultValue="") Long categoryNo ) {
		System.out.println(categoryNo);
		categoryService.delCategory(categoryNo);
		return JsonResult.success(categoryNo);
	}
}
