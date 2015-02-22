package edu.zipcloud.cloudstreetmarket.api.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorInfo {

    public final String message;
    public final String date;
    
    public ErrorInfo(Exception ex) {
    	this.message = ex.getMessage();
    	
    	//Mind that SimpleDateFormat is not thread safe
    	this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
}