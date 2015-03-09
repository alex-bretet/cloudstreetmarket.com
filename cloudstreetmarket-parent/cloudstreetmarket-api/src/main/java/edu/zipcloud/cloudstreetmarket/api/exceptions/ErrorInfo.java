package edu.zipcloud.cloudstreetmarket.api.exceptions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import edu.zipcloud.util.ExceptionUtil;

public class ErrorInfo {

    public final String error;
    public final String message;
    public final int status;
    public final String date;
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public ErrorInfo(Throwable throwable, String message, HttpStatus status) {
    	this.error = ExceptionUtil.getRootMessage(throwable);
    	this.message = message;
    	this.date = dateFormat.format(new Date());
    	this.status = status.value();
    }

	@Override
	public String toString() {
		return "ErrorInfo [status="+status+", error=" + error + ", date=" + date + "]";
	}

}