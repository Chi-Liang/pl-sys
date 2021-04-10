package com.hanye.info.vo;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.web.multipart.MultipartFile;

public class LectureVO {
	private Long id;
	private String startTime;
	private String endTime;
	private String session;
	private MultipartFile file;
	private String fileName;
	private byte[] picture;
	private String pictureUrl;
	private String whichGroup;
	private String session2;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getWhichGroup() {
		return whichGroup;
	}
	public void setWhichGroup(String whichGroup) {
		this.whichGroup = whichGroup;
	}
	public String getSession2() {
		return session2;
	}
	public void setSession2(String session2) {
		this.session2 = session2;
	}
	
}
