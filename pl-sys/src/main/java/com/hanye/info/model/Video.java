package com.hanye.info.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Nationalized;

@Entity
public class Video {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vid;

	@Basic
	@Nationalized
	@Column(length = 100)
	private String title;

	@Basic
	@Nationalized
	@Column(length = 100)
	private String subtitle;

	@Basic
	@Nationalized
	@Column(length = 300)
	private String description;

	@Column(length = 200)
	private String videoUrl;

//	@Column(length = 200)
//	private String fileUrl;
//	
//	@Column(length = 1000)
//	private String fileName;
//	
//	@Lob
//	private byte[] picture;

	@ManyToOne
	private Category category;

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

}
