package com.japc.rest.ws.erp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the [USER] database table.
 * 
 */
@Entity
@Table(name = "[USER]")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID", nullable = false, length = 50)
	private String userId;

	@Column(name = "USER_EMAIL", nullable = false, length = 100)
	private String userEmail;

	@Column(name = "USER_NAME", nullable = false, length = 50)
	private String userName;

	@Column(name = "USER_STATE", nullable = false, length = 1)
	private String userState;

	@Column(name = "USER_LASTNAME", nullable = false, length = 50)
	private String userLastname;
	
	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private Role role;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private List<Password> passwords;

	public User() {
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserState() {
		return this.userState;
	}

	public void setUserState(String userState) {
		this.userState = userState;
	}

	public String getUserLastname() {
		return userLastname;
	}

	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}

	@JsonIgnore
	public List<Password> getPasswords() {
		return passwords;
	}

	public void setPasswords(List<Password> passwords) {
		this.passwords = passwords;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

}