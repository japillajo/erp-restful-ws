package com.japc.rest.ws.erp.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the PASSWORD database table.
 * 
 */
@Entity
@Table(name = "PASSWORD")
public class Password implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PASSWORD_ID", nullable = false)
	private Long passwordId;

	@Column(name = "PASSWORD_STATE", nullable = false)
	private boolean passwordState;

	@Column(name = "PASSWORD_TEXT", nullable = false, length = 64)
	private String passwordText;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	public Password() {
	}

	public Long getPasswordId() {
		return this.passwordId;
	}

	public void setPasswordId(Long passwordId) {
		this.passwordId = passwordId;
	}

	public boolean getPasswordState() {
		return this.passwordState;
	}

	public void setPasswordState(boolean passwordState) {
		this.passwordState = passwordState;
	}

	public String getPasswordText() {
		return this.passwordText;
	}

	public void setPasswordText(String passwordText) {
		this.passwordText = passwordText;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passwordId == null) ? 0 : passwordId.hashCode());
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
		Password other = (Password) obj;
		if (passwordId == null) {
			if (other.passwordId != null)
				return false;
		} else if (!passwordId.equals(other.passwordId))
			return false;
		return true;
	}

}