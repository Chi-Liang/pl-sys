package com.hanye.info.vo;

import javax.persistence.Column;
import javax.persistence.Id;

public class LectureVO {
	private Long id;
	private String formName;
	private String formLink;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getFormLink() {
		return formLink;
	}
	public void setFormLink(String formLink) {
		this.formLink = formLink;
	}
	
}
