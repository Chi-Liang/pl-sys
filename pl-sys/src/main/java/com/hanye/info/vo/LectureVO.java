package com.hanye.info.vo;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class LectureVO {
	private Long id;
	private String startTime;
	private String endTime;
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
