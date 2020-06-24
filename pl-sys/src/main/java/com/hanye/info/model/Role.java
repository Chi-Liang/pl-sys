package com.hanye.info.model;

import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Nationalized;

@Entity
public class Role {

	@Id
    @Column(length = 100)
    private String rid;

    @Basic
    @Nationalized
    @Column(length = 50)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    
    public Role() {
    	
    }

	public Role(String rid) {
		super();
		this.rid = rid;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}


}
