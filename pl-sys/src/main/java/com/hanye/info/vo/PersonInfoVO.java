package com.hanye.info.vo;

import javax.persistence.Column;

public class PersonInfoVO {
	//名稱
	private String mid;
	//姓名
	private String name;
	//生日
	private String birthday;
	//身份證字號
	private String taxId;
	//通訊電話
	private String phone;
	//信箱
	private String email;
	//戶籍地址
	private String residenceAddress;
	//通訊地址
	private String communicationAddress;
	//服務公司
	private String company;
	//職位
	private String position;
	//年資
	private String seniority;
	//工作地點
	private String workspace;
	//月收入
	private String monthlySalary;
	//扣繳憑單       有1,無0
	private String withholdVoucher;
	//薪資轉帳       有1,無0
	private String payrollTransfer;
	//勞保           有1,無0
	private String laborProtection;
	//名下不動產     有1,無0
	private String realEstate;
	//基金投資
	private String fundInvestment;
	//股票投資
	private String stockInvestment;
	//保險
	private String insurance;
	//其他收入
	private String otherIncome;
	//存摺平均餘額
	private String averageBalance;
	//其他貸款   有1,無0 
	//學貸 
	private String studentLoan;
	//車貸 
	private String carLoan;
	//房貸 
	private String housingLoan;
	//信貸
	private String creditLoan;
	//其他  
	private String otherLoans;
	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getResidenceAddress() {
		return residenceAddress;
	}

	public void setResidenceAddress(String residenceAddress) {
		this.residenceAddress = residenceAddress;
	}

	public String getCommunicationAddress() {
		return communicationAddress;
	}

	public void setCommunicationAddress(String communicationAddress) {
		this.communicationAddress = communicationAddress;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSeniority() {
		return seniority;
	}

	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public String getMonthlySalary() {
		return monthlySalary;
	}

	public void setMonthlySalary(String monthlySalary) {
		this.monthlySalary = monthlySalary;
	}

	public String getWithholdVoucher() {
		return withholdVoucher;
	}

	public void setWithholdVoucher(String withholdVoucher) {
		this.withholdVoucher = withholdVoucher;
	}

	public String getPayrollTransfer() {
		return payrollTransfer;
	}

	public void setPayrollTransfer(String payrollTransfer) {
		this.payrollTransfer = payrollTransfer;
	}

	public String getLaborProtection() {
		return laborProtection;
	}

	public void setLaborProtection(String laborProtection) {
		this.laborProtection = laborProtection;
	}

	public String getRealEstate() {
		return realEstate;
	}

	public void setRealEstate(String realEstate) {
		this.realEstate = realEstate;
	}

	public String getFundInvestment() {
		return fundInvestment;
	}

	public void setFundInvestment(String fundInvestment) {
		this.fundInvestment = fundInvestment;
	}

	public String getStockInvestment() {
		return stockInvestment;
	}

	public void setStockInvestment(String stockInvestment) {
		this.stockInvestment = stockInvestment;
	}

	public String getInsurance() {
		return insurance;
	}

	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}

	public String getOtherIncome() {
		return otherIncome;
	}

	public void setOtherIncome(String otherIncome) {
		this.otherIncome = otherIncome;
	}

	public String getAverageBalance() {
		return averageBalance;
	}

	public void setAverageBalance(String averageBalance) {
		this.averageBalance = averageBalance;
	}

	public String getOtherLoans() {
		return otherLoans;
	}

	public void setOtherLoans(String otherLoans) {
		this.otherLoans = otherLoans;
	}

	public String getStudentLoan() {
		return studentLoan;
	}

	public void setStudentLoan(String studentLoan) {
		this.studentLoan = studentLoan;
	}

	public String getCarLoan() {
		return carLoan;
	}

	public void setCarLoan(String carLoan) {
		this.carLoan = carLoan;
	}

	public String getHousingLoan() {
		return housingLoan;
	}

	public void setHousingLoan(String housingLoan) {
		this.housingLoan = housingLoan;
	}

	public String getCreditLoan() {
		return creditLoan;
	}

	public void setCreditLoan(String creditLoan) {
		this.creditLoan = creditLoan;
	}
	
}
