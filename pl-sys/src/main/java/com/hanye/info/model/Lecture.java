package com.hanye.info.model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Lecture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	@Column(length = 50)
	private String startTime;
	
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	@Column(length = 50)
	private String endTime;
	
	
	@Column(length = 50)
	private String session;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
	
}
