package com.hanye.info.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Nationalized;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cid;

	@Basic
	@Nationalized
	@Column(length = 50)
	private String name;

	@OneToMany(mappedBy = "category")
	private Set<Video> videos = new HashSet<Video>();
	
	@ManyToMany(mappedBy = "categories")
	private Collection<Member> membes;

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Video> getVideos() {
		return videos;
	}

	public void setVideos(Set<Video> videos) {
		this.videos = videos;
	}

	public Collection<Member> getMembes() {
		return membes;
	}

	public void setMembes(Collection<Member> membes) {
		this.membes = membes;
	}


}
