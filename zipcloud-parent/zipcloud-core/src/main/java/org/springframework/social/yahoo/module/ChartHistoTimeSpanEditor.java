package org.springframework.social.yahoo.module;

import java.beans.PropertyEditorSupport;

public class ChartHistoTimeSpanEditor extends PropertyEditorSupport {
    public void setAsText(String text) {
		try{
			 setValue(ChartHistoTimeSpan.valueOf(text));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The provided value for the chart time span parameter is invalid!");
		}
    }
}
