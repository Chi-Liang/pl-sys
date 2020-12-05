package com.hanye.info.vo;

public class ReturnPersonInfoVO {
	private String result;
	private String message;
	private PersonInfoVO personInfoVO;
	
	public ReturnPersonInfoVO(String result, String message, PersonInfoVO personInfoVO) {
		super();
		this.result = result;
		this.message = message;
		this.personInfoVO = personInfoVO;
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
	public PersonInfoVO getPersonInfoVO() {
		return personInfoVO;
	}
	public void setPersonInfoVO(PersonInfoVO personInfoVO) {
		this.personInfoVO = personInfoVO;
	}
	
}
