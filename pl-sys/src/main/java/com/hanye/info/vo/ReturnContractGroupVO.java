package com.hanye.info.vo;

import java.util.List;

public class ReturnContractGroupVO {

	private String result;
	private String message;
	private List<ContractGroupVO> contractGroupVOList;
	
	public ReturnContractGroupVO(String result, String message, List<ContractGroupVO> contractGroupVOList) {
		super();
		this.result = result;
		this.message = message;
		this.contractGroupVOList = contractGroupVOList;
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
	public List<ContractGroupVO> getContractGroupVOList() {
		return contractGroupVOList;
	}
	public void setContractGroupVOList(List<ContractGroupVO> contractGroupVOList) {
		this.contractGroupVOList = contractGroupVOList;
	}
	
}
