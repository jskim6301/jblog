package com.douzone.jblog.vo;

import org.springframework.web.multipart.MultipartFile;

public class BlogVO {
	private String id;
	private String title;
	private String logo;
	private MultipartFile logoFile;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	public MultipartFile getLogoFile() {
		return logoFile;
	}
	public void setLogoFile(MultipartFile logoFile) {
		this.logoFile = logoFile;
	}
	@Override
	public String toString() {
		return "BlogVO [id=" + id + ", title=" + title + ", logo=" + logo + ", logoFile=" + logoFile + "]";
	}

	
}
