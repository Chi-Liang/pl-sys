package com.hanye.info.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class LatestInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long lid;

	@Column(length = 100)
	private String title;
	
	@Column(length = 1000)
	private String detail;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
	private Date createDate;
	
	@Column(length = 1000)
	private String bigFileName;
	
	@Column(length = 1000)
	private String smallFileName;
	
	@Lob
	private byte[] bigPicture;
	
	@Lob
	private byte[] smallPicture;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	
}
