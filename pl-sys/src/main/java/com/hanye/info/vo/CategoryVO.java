package com.hanye.info.vo;

import javax.persistence.Column;

import org.springframework.web.multipart.MultipartFile;

public class CategoryVO {

	private Long cid;
	private String name;
	private MultipartFile file;
	private String fileName;
	private byte[] picture;
	private String pictureUrl;
	private String whichWebSite = "1";

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
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

	public String getWhichWebSite() {
		return whichWebSite;
	}

	public void setWhichWebSite(String whichWebSite) {
		this.whichWebSite = whichWebSite;
	}
	
}
