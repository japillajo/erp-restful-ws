package com.japc.rest.ws.erp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * The persistent class for the [MODULE] database table.
 * 
 */
@Entity
@Table(name = "[MODULE]")
public class Module implements Serializable, Comparable<Module> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MODULE_ID", nullable = false)
	private Integer moduleId;

	@Column(name = "MODULE_DETAIL", nullable = true, length = 100)
	private String moduleDetail;

	@Column(name = "MODULE_NAME", nullable = false, length = 30)
	private String moduleName;

	@Column(name = "MODULE_ICON", nullable = true, length = 50)
	private String moduleIcon;

	@OneToMany(mappedBy = "module", fetch = FetchType.EAGER, targetEntity = Menu.class)
	private List<Menu> menus;

	protected Module() {
	}

	public Integer getModuleId() {
		return this.moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleDetail() {
		return this.moduleDetail;
	}

	public void setModuleDetail(String moduleDetail) {
		this.moduleDetail = moduleDetail;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleIcon() {
		return moduleIcon;
	}

	public void setModuleIcon(String moduleIcon) {
		this.moduleIcon = moduleIcon;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((moduleId == null) ? 0 : moduleId.hashCode());
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
		Module other = (Module) obj;
		if (moduleId == null) {
			if (other.moduleId != null)
				return false;
		} else if (!moduleId.equals(other.moduleId))
			return false;
		return true;
	}

	@Override
	public int compareTo(Module m) {
		return this.getModuleId().compareTo(m.getModuleId());
	}

	@Override
	public String toString() {
		return "Module [moduleId=" + moduleId + ", moduleDetail=" + moduleDetail + ", moduleName=" + moduleName
				+ ", moduleIcon=" + moduleIcon + "]";
	}

}