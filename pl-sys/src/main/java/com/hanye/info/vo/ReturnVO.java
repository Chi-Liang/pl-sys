package com.hanye.info.vo;

public class ReturnVO {
	private String result;
	private String message;
	
	public ReturnVO(String result, String message) {
		super();
		this.result = result;
		this.message = message;
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
