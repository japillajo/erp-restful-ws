package com.japc.rest.ws.erp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the [menu] database table.
 * 
 */
@Entity
@Table(name = "[LOG]")
public class Log implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LOG_ID", nullable = false)
	private String logId;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	@Column(name = "LOG_FECHA_HORA", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date logFechaHora;

	@Column(name = "LOG_TYPE", nullable = false, length = 20)
	private String logType;
	
	@Column(name = "LOG_CLASS", nullable = false, length = 100)
	private String logClass;

	@Column(name = "LOG_METHOD", nullable = false, length = 50)
	private String logMethod;

	@Column(name = "LOG_STACK_TRACE", nullable = true, columnDefinition="TEXT")
	private String logStackTrace;

	@Column(name = "LOG_METHOD_RETURN", nullable = true, columnDefinition="TEXT")
	private String logMethodReturn;

	protected Log() {
	}

	public Log(String logId, User user, Date logFechaHora, String logType, String logClass, String logMethod,
			String logStackTrace, String logMethodReturn) {
		super();
		this.logId = logId;
		this.user = user;
		this.logFechaHora = logFechaHora;
		this.logType = logType;
		this.logClass = logClass;
		this.logMethod = logMethod;
		this.logStackTrace = logStackTrace;
		this.logMethodReturn = logMethodReturn;
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getLogFechaHora() {
		return logFechaHora;
	}

	public void setLogFechaHora(Date logFechaHora) {
		this.logFechaHora = logFechaHora;
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

	public String getLogMethod() {
		return logMethod;
	}

	public void setLogMethod(String logMethod) {
		this.logMethod = logMethod;
	}

	public String getLogStackTrace() {
		return logStackTrace;
	}

	public void setLogStackTrace(String logStackTrace) {
		this.logStackTrace = logStackTrace;
	}

	public String getLogMethodReturn() {
		return logMethodReturn;
	}

	public void setLogMethodReturn(String logMethodReturn) {
		this.logMethodReturn = logMethodReturn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logId == null) ? 0 : logId.hashCode());
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
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		return true;
	}

}