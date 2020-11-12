package com.hanye.info.vo;

public class ReturnVO {
	private String result;
	private String msg;
	
	public ReturnVO(String result, String msg) {
		super();
		this.result = result;
		this.msg = msg;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
