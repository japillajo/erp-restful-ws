package com.japc.rest.ws.erp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class LogPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "LOG_ID", nullable = false)
	private String logId;
	
	@Column(name = "LOG_FECHA_HORA", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date logFechaHora;

	public LogPK(String logId, Date logFechaHora) {
		super();
		this.logId = logId;
		this.logFechaHora = logFechaHora;
	}

	public LogPK() {
		super();
	}

	public String getLogId() {
		return logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	public Date getLogFechaHora() {
		return logFechaHora;
	}

	public void setLogFechaHora(Date logFechaHora) {
		this.logFechaHora = logFechaHora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((logFechaHora == null) ? 0 : logFechaHora.hashCode());
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
		LogPK other = (LogPK) obj;
		if (logFechaHora == null) {
			if (other.logFechaHora != null)
				return false;
		} else if (!logFechaHora.equals(other.logFechaHora))
			return false;
		if (logId == null) {
			if (other.logId != null)
				return false;
		} else if (!logId.equals(other.logId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LogPK [logId=" + logId + ", logFechaHora=" + logFechaHora + "]";
	}

}
