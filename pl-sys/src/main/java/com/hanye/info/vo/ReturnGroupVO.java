package com.hanye.info.vo;

import java.util.List;

public class ReturnGroupVO {

	private String result;
	private String message;
	private ModPictureVO modPictureVO;
	
	public ReturnGroupVO(String result, String message, ModPictureVO modPictureVO) {
		super();
		this.result = result;
		this.message = message;
		this.modPictureVO = modPictureVO;
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
	public ModPictureVO getModPictureVO() {
		return modPictureVO;
	}
	public void setModPictureVO(ModPictureVO modPictureVO) {
		this.modPictureVO = modPictureVO;
	}
	
}
