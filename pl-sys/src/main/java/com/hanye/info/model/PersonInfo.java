package com.hanye.info.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Nationalized;

@Entity
public class PersonInfo {

	@Id
	@Column(length = 15)
	private String mid;
	
	@Basic
	@Nationalized
	@Column(length = 100)
	private String name;
	
	@Column(length = 100)
	private String birthday;

	@Column(length = 300)
	private String taxId;

	@Column(length = 200)
	private String phone;

	@Column(length = 200)
	private String email;
	
	@Column(length = 200)
	private String residenceAddress;
	
	@Column(length = 200)
	private String communicationAddress;
	
	@Column(length = 200)
	private String company;
	
	@Column(length = 200)
	private String position;
	
	@Column(length = 200)
	private String seniority;
	
	@Column(length = 200)
	private String workspace;
	
	@Column(length = 200)
	private String monthlySalary;
	
	@Column(length = 1)
	private String withholdVoucher;
	
	@Column(length = 1)
	private String payrollTransfer;
	
	@Column(length = 1)
	private String laborProtection;
	
	@Column(length = 1)
	private String realEstate;
	
	@Column(length = 200)
	private String fundInvestment;

	@Column(length = 200)
	private String stockInvestment;
	
	@Column(length = 200)
	private String insurance;

	@Column(length = 200)
	private String otherIncome;

	@Column(length = 200)
	private String averageBalance;

	@Column(length = 1)
	private String studentLoan;
	
	@Column(length = 1)
	private String carLoan;
	
	@Column(length = 1)
	private String housingLoan;
	
	@Column(length = 1)
	private String creditLoan;
	
	@Column(length = 1)
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
