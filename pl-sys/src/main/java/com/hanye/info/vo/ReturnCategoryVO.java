package com.hanye.info.vo;

import java.util.List;

public class ReturnCategoryVO {

	private String result;
	private String message;
	private List<CategoryVO> categoryVOList;
	
	public ReturnCategoryVO(String result, String message, List<CategoryVO> categoryVOList) {
		super();
		this.result = result;
		this.message = message;
		this.categoryVOList = categoryVOList;
	}

	public List<CategoryVO> getCategoryVOList() {
		return categoryVOList;
	}

	public void setCategoryVOList(List<CategoryVO> categoryVOList) {
		this.categoryVOList = categoryVOList;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
