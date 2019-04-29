package com.japc.rest.ws.erp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the [menu] database table.
 * 
 */
@Entity
@Table(name = "[LOG]")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LogPK id;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "LOG_TYPE", nullable = false, length = 20)
	private String logType;

	@Column(name = "LOG_CLASS", nullable = false, length = 100)
	private String logClass;

	@Column(name = "LOG_RETURN", nullable = true, columnDefinition = "TEXT")
	private String logReturn;

	public Log(LogPK id, User user, String logType, String logClass, String logReturn) {
		super();
		this.id = id;
		this.user = user;
		this.logType = logType;
		this.logClass = logClass;
		this.logReturn = logReturn;
	}

	protected Log() {
	}


	public LogPK getId() {
		return id;
	}

	public void setId(LogPK id) {
		this.id = id;
	}

	public String getLogReturn() {
		return logReturn;
	}

	public void setLogReturn(String logReturn) {
		this.logReturn = logReturn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogClass() {
		return logClass;
	}

	public void setLogClass(String logClass) {
		this.logClass = logClass;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Log other = (Log) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}