package com.hanye.info.vo;

import java.util.List;

public class ReturnLectureVO {
	private String result;
	private String message;
	private List<LectureVO> lectureVOList;
	
	public ReturnLectureVO(String result, String message, List<LectureVO> lectureVOList) {
		super();
		this.result = result;
		this.message = message;
		this.lectureVOList = lectureVOList;
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
	public List<LectureVO> getLectureVOList() {
		return lectureVOList;
	}
	public void setLectureVOList(List<LectureVO> lectureVOList) {
		this.lectureVOList = lectureVOList;
	}
	
}
