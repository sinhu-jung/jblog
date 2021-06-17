package com.douzone.jblog.vo;

public class CategoryVo {
	private Long no;
	private Long count;
	private String name;
	private String description;
	private String regDate;
	private String blogID;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getBlogID() {
		return blogID;
	}
	public void setBlogID(String blogID) {
		this.blogID = blogID;
	}
	@Override
	public String toString() {
		return "CategoryVo [no=" + no + ", count=" + count + ", name=" + name + ", description=" + description
				+ ", regDate=" + regDate + ", blogID=" + blogID + "]";
	}
	
	
}
