package com.hanye.info.vo;

import java.util.List;

public class ReturnLatestInfoVO {
	
	private String result;
	private String message;
	private List<LatestInfoVO> latestInfoVOList;
	public ReturnLatestInfoVO(String result, String message, List<LatestInfoVO> latestInfoVOList) {
		super();
		this.result = result;
		this.message = message;
		this.latestInfoVOList = latestInfoVOList;
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
	public List<LatestInfoVO> getLatestInfoVOList() {
		return latestInfoVOList;
	}
	public void setLatestInfoVOList(List<LatestInfoVO> latestInfoVOList) {
		this.latestInfoVOList = latestInfoVOList;
	}
	
}
