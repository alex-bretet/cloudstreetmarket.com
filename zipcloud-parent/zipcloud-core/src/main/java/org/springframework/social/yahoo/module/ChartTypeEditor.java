package org.springframework.social.yahoo.module;

import java.beans.PropertyEditorSupport;

public class ChartTypeEditor extends PropertyEditorSupport {
    public void setAsText(String text) {
		try{
			 setValue(ChartType.valueOf(text));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The provided value for the chart type parameter is invalid!");
		}
    }
}
