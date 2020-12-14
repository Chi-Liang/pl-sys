package com.hanye.info.vo;

import java.util.List;

public class ReturnKnowledgeArticleVO {
	
	private String result;
	private String message;
	private List<KnowledgeArticleVO> knowledgeArticleVOList;
	public ReturnKnowledgeArticleVO(String result, String message, List<KnowledgeArticleVO> knowledgeArticleVOList) {
		super();
		this.result = result;
		this.message = message;
		this.knowledgeArticleVOList = knowledgeArticleVOList;
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
	public List<KnowledgeArticleVO> getKnowledgeArticleVOList() {
		return knowledgeArticleVOList;
	}
	public void setKnowledgeArticleVOList(List<KnowledgeArticleVO> knowledgeArticleVOList) {
		this.knowledgeArticleVOList = knowledgeArticleVOList;
	}
	
}
