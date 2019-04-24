package com.japc.rest.ws.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * The persistent class for the USER_MENU_DISABLED database table.
 * 
 */
@Entity
@Table(name="USER_MENU")
public class UserMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserMenuPK id;
	
	@Column(name = "USER_MENU_ENABLED", nullable = false)
	private boolean userMenuEnabled;

	public UserMenu() {
	}

	public UserMenuPK getId() {
		return this.id;
	}

	public void setId(UserMenuPK id) {
		this.id = id;
	}

	public boolean isUserMenuEnabled() {
		return userMenuEnabled;
	}

	public void setUserMenuEnabled(boolean userMenuEnabled) {
		this.userMenuEnabled = userMenuEnabled;
	}
	
	

}