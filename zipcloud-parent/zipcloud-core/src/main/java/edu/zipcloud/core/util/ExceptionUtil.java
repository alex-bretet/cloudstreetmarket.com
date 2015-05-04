package edu.zipcloud.core.util;

public class ExceptionUtil {
	
	public static String getRootMessage(Throwable throwable){
		if(throwable==null){
			return null;
		}
    	String error = throwable.getMessage();
    	Throwable nested = throwable;
    	while(nested.getCause()!=null){
    		nested = nested.getCause();
    		error = nested.getMessage();
    	}
    	return error;
	}
	
}
