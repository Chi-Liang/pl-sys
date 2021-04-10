package com.hanye.info.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Nationalized;

@Entity
public class Member {

	@Id
	@Column(length = 15)
	private String mid;

	@Column(length = 100)
	private String pwd;

	@Basic
	@Nationalized
	@Column(length = 50)
	private String name;

	@Column(length = 10)
	private String tel;

	@Column(length = 200)
	private String email;

	@Basic
	@Nationalized
	@Column(length = 200)
	private String address;

	private Date createDate;
	private Date updateDate;
	
	@ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "MemberCategroy",
            joinColumns = @JoinColumn(
                    name = "member_id", referencedColumnName = "mid"),
            inverseJoinColumns = @JoinColumn(
                    name = "category_id", referencedColumnName = "cid"))
	private Set<Category> categories = new HashSet<Category>();
	
	@Column(length = 1)
	private String freeOrPaid;
	@Column(length = 10)
	private String points;
	@Column(length = 1)
	private String whichGroup;
	
	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public String getFreeOrPaid() {
		return freeOrPaid;
	}

	public void setFreeOrPaid(String freeOrPaid) {
		this.freeOrPaid = freeOrPaid;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}

	public String getWhichGroup() {
		return whichGroup;
	}

	public void setWhichGroup(String whichGroup) {
		this.whichGroup = whichGroup;
	}
	
}
