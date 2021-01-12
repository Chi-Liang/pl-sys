package com.hanye.info.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class LatestInfoVO {
	private Long lid;
	private String title;
	private String detail;
	private String createDate;
	private MultipartFile bigFile;
	private MultipartFile smallFile;
	private String bigFileName;
	private String smallFileName;
	private byte[] bigPicture;
	private byte[] smallPicture;
	private String bigPictureUrl;
	private String smallPictureUrl;
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
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public MultipartFile getBigFile() {
		return bigFile;
	}
	public void setBigFile(MultipartFile bigFile) {
		this.bigFile = bigFile;
	}
	public MultipartFile getSmallFile() {
		return smallFile;
	}
	public void setSmallFile(MultipartFile smallFile) {
		this.smallFile = smallFile;
	}
	public String getBigFileName() {
		return bigFileName;
	}
	public void setBigFileName(String bigFileName) {
		this.bigFileName = bigFileName;
	}
	public String getSmallFileName() {
		return smallFileName;
	}
	public void setSmallFileName(String smallFileName) {
		this.smallFileName = smallFileName;
	}
	public byte[] getBigPicture() {
		return bigPicture;
	}
	public void setBigPicture(byte[] bigPicture) {
		this.bigPicture = bigPicture;
	}
	public byte[] getSmallPicture() {
		return smallPicture;
	}
	public void setSmallPicture(byte[] smallPicture) {
		this.smallPicture = smallPicture;
	}
	public String getBigPictureUrl() {
		return bigPictureUrl;
	}
	public void setBigPictureUrl(String bigPictureUrl) {
		this.bigPictureUrl = bigPictureUrl;
	}
	public String getSmallPictureUrl() {
		return smallPictureUrl;
	}
	public void setSmallPictureUrl(String smallPictureUrl) {
		this.smallPictureUrl = smallPictureUrl;
	}
	
}
