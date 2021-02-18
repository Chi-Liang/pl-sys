package com.hanye.info.vo;

public class ReturnVideoCarouselVO {
	private String result;
	private String message;
	private VideoCarouselVO videoCarouselVO;
	
	public ReturnVideoCarouselVO(String result, String message, VideoCarouselVO videoCarouselVO) {
		super();
		this.result = result;
		this.message = message;
		this.videoCarouselVO = videoCarouselVO;
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
	public VideoCarouselVO getVideoCarouselVO() {
		return videoCarouselVO;
	}
	public void setVideoCarouselVO(VideoCarouselVO videoCarouselVO) {
		this.videoCarouselVO = videoCarouselVO;
	}
	
}
