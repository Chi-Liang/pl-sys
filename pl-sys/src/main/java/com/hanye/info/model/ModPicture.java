package com.hanye.info.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class ModPicture {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 1000)
	private String group1FileName;
	
	@Column(length = 1000)
	private String group2FileName;
	
	@Lob
	private byte[] group1Picture;
	
	@Lob
	private byte[] group2Picture;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
