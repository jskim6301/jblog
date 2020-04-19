package com.douzone.jblog.vo;

public class CategoryVO {
	private Long no;
	private String name;
	private String description;
	private String regDate;
	private String id;
	
	private String categoryCnt; 
	private Integer totalCategoryCnt;
	

	


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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCategoryCnt() {
		return categoryCnt;
	}
	public void setCategoryCnt(String categoryCnt) {
		this.categoryCnt = categoryCnt;
	}
	
	public Integer getTotalCategoryCnt() {
		return totalCategoryCnt;
	}
	public void setTotalCategoryCnt(Integer totalCategoryCnt) {
		this.totalCategoryCnt = totalCategoryCnt;
	}
	
	
	@Override
	public String toString() {
		return "CategoryVO [no=" + no + ", name=" + name + ", description=" + description + ", regDate=" + regDate
				+ ", id=" + id + ", categoryCnt=" + categoryCnt + ", totalCategoryCnt=" + totalCategoryCnt + "]";
	}
	
	

	

	

	

	
}
