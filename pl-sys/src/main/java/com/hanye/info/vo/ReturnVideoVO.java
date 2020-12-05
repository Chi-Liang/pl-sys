package com.hanye.info.vo;

public class ReturnVideoVO {
	private String result;
	private String message;
	private VideoVO videoVO;
	
	public ReturnVideoVO(String result, String message, VideoVO videoVO) {
		super();
		this.result = result;
		this.message = message;
		this.videoVO = videoVO;
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
	public VideoVO getVideoVO() {
		return videoVO;
	}
	public void setVideoVO(VideoVO videoVO) {
		this.videoVO = videoVO;
	}
	
}
