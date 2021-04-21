package com.hanye.info.vo;

import org.springframework.web.multipart.MultipartFile;

public class ModPictureVO {
	
	private Long id;
	private String group1Title;
	private String group2Title;
	private MultipartFile group1File;
	private MultipartFile group2File;
	private String group1FileName;
	private String group2FileName;
	private byte[] group1Picture;
	private byte[] group2Picture;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public MultipartFile getGroup1File() {
		return group1File;
	}
	public void setGroup1File(MultipartFile group1File) {
		this.group1File = group1File;
	}
	public MultipartFile getGroup2File() {
		return group2File;
	}
	public void setGroup2File(MultipartFile group2File) {
		this.group2File = group2File;
	}
	public String getGroup1FileName() {
		return group1FileName;
	}
	public void setGroup1FileName(String group1FileName) {
		this.group1FileName = group1FileName;
	}
	public String getGroup2FileName() {
		return group2FileName;
	}
	public void setGroup2FileName(String group2FileName) {
		this.group2FileName = group2FileName;
	}
	public byte[] getGroup1Picture() {
		return group1Picture;
	}
	public void setGroup1Picture(byte[] group1Picture) {
		this.group1Picture = group1Picture;
	}
	public byte[] getGroup2Picture() {
		return group2Picture;
	}
	public void setGroup2Picture(byte[] group2Picture) {
		this.group2Picture = group2Picture;
	}
	public String getGroup1Title() {
		return group1Title;
	}
	public void setGroup1Title(String group1Title) {
		this.group1Title = group1Title;
	}
	public String getGroup2Title() {
		return group2Title;
	}
	public void setGroup2Title(String group2Title) {
		this.group2Title = group2Title;
	}
}
