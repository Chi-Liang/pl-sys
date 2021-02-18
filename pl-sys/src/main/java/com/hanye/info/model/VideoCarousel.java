package com.hanye.info.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class VideoCarousel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vid;

	@Column(length = 200)
	private String videoUrl1;
	
	@Column(length = 200)
	private String videoUrl2;
	
	@Column(length = 200)
	private String videoUrl3;

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public String getVideoUrl1() {
		return videoUrl1;
	}

	public void setVideoUrl1(String videoUrl1) {
		this.videoUrl1 = videoUrl1;
	}

	public String getVideoUrl2() {
		return videoUrl2;
	}

	public void setVideoUrl2(String videoUrl2) {
		this.videoUrl2 = videoUrl2;
	}

	public String getVideoUrl3() {
		return videoUrl3;
	}

	public void setVideoUrl3(String videoUrl3) {
		this.videoUrl3 = videoUrl3;
	}
	
}
