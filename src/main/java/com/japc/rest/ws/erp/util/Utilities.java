package com.japc.rest.ws.erp.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;

import com.japc.rest.ws.erp.enumerator.LogTypeEnum;
import com.japc.rest.ws.erp.model.Log;
import com.japc.rest.ws.erp.model.LogPK;
import com.japc.rest.ws.erp.model.User;
import com.japc.rest.ws.erp.repository.LogJpaRepository;

public class Utilities {

	public static String formatHttpStatus(HttpStatus httpStatus) {
		return httpStatus.value() + " " + httpStatus.name();
	}

	public static String formatExceptionMessage(Object o, String method, Exception e) {
		return "Exception: " + e.getClass().getName() + ", Message: " + e.getMessage() + " in " + o.getClass().getName()
				+ ", method: " + method;
	}

	public static void printSaveLog(Logger logger, String uuid, User requestUser, LogTypeEnum logType, Exception e,
			String methodReturn, LogJpaRepository logJpaRepository) {
		Log newLog = null;
		LogPK logPk = new LogPK(uuid, new Date());

		switch (logType) {
		case INFO:
			logger.info(String.format("Successful process in : %s, Uuid: %s, Return: %s", logger.getName(), uuid,
					methodReturn));
			newLog = new Log(logPk, requestUser, logType.getType(), logger.getName(), methodReturn);
			break;
		case SEVERE:
			logger.severe(String.format("Exception occur : %s, Uuid: %s, Return: %s", logger.getName(), uuid, e.getMessage()));
			newLog = new Log(logPk, requestUser, logType.getType(), logger.getName(), getStackTrace(e));
			break;
		case WARNING:
			logger.warning(
					String.format("Warning in : %s, Uuid: %s, Return: %s", logger.getName(), uuid, methodReturn));
			newLog = new Log(logPk, requestUser, logType.getType(), logger.getName(), methodReturn);
			break;
		}

		logJpaRepository.save(newLog);
	}

	public static String getStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		return sw.toString();
	}

}
