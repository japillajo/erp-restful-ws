package com.japc.rest.ws.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the [menu] database table.
 * 
 */
@Entity
@Table(name = "[MENU]")
public class Menu implements Serializable, Comparable<Menu> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MENU_ID", nullable = false)
	private Integer menuId;

	@ManyToOne
	@JoinColumn(name = "MODULE_ID")
	private Module module;

	@Column(name = "MENU_NAME", nullable = false, length = 30)
	private String menuName;

	@Column(name = "MENU_URL", nullable = false, length = 50)
	private String menuUrl;

	@Column(name = "MENU_ICON", nullable = true, length = 50)
	private String menuIcon;

	@Column(name = "MENU_DETAIL", nullable = true, length = 100)
	private String menuDetail;

	@Column(name = "MENU_STATE", nullable = false)
	private boolean menuState;

	protected Menu() {
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	@JsonIgnore
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuDetail() {
		return menuDetail;
	}

	public void setMenuDetail(String menuDetail) {
		this.menuDetail = menuDetail;
	}

	public boolean isMenuState() {
		return menuState;
	}

	public void setMenuState(boolean menuState) {
		this.menuState = menuState;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuId == null) ? 0 : menuId.hashCode());
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
		Menu other = (Menu) obj;
		if (menuId == null) {
			if (other.menuId != null)
				return false;
		} else if (!menuId.equals(other.menuId))
			return false;
		return true;
	}

	@Override
	public int compareTo(Menu m) {
		return this.getMenuId().compareTo(m.getMenuId());
	}

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", module=" + module + ", menuName=" + menuName + ", menuUrl=" + menuUrl
				+ ", menuIcon=" + menuIcon + ", menuDetail=" + menuDetail + ", menuState=" + menuState + "]";
	}
}