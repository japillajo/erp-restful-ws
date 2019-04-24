package com.japc.rest.ws.erp.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.japc.rest.ws.erp.model.Log;
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

	public static void saveLogToDb(String uuid, User requestUser, String logType, Object o, String logMethod,
			Exception e, String logMethodReturn, LogJpaRepository logJpaRepository) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		Log newLog = new Log(uuid, requestUser, new Date(), logType, o.getClass().getName(), logMethod, sw.toString(),
				logMethodReturn);
		logJpaRepository.save(newLog);
	}

	public static String getStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		return sw.toString();
	}

}
