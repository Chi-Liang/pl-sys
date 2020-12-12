package com.hanye.info.vo;

public class ReturnLoginVO {
	private String result;
	private String message;
	private MemberVO memberVO;
	public ReturnLoginVO(String result, String message, MemberVO memberVO) {
		super();
		this.result = result;
		this.message = message;
		this.memberVO = memberVO;
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
	public MemberVO getMemberVO() {
		return memberVO;
	}
	public void setMemberVO(MemberVO memberVO) {
		this.memberVO = memberVO;
	}
	
}
