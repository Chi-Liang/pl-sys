package com.hanye.info.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class KnowledgeArticle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lid;
	
	@Column(length = 100)
	private String title;
	
	@Column(length = 1000)
	private String detail;

	public Long getLid() {
		return lid;
	}

	public void setLid(Long lid) {
		this.lid = lid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
