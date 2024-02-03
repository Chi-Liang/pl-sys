package com.hanye.info.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class MemberPoint {
	
	@Id
	@Column(length = 15)
	private String mid;
	
	@Column(length = 10)
	private String points;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}
	
}
