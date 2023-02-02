package com.hanye.info.vo;

import org.springframework.web.multipart.MultipartFile;

public class ContractGroupVO {

	private Long groupId;

	private String groupName;
	
	private MultipartFile file;
	
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

}
