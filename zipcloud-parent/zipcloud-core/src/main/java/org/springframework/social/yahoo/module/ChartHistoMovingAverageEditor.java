package org.springframework.social.yahoo.module;

import java.beans.PropertyEditorSupport;

public class ChartHistoMovingAverageEditor extends PropertyEditorSupport {
    public void setAsText(String text) {
		try{
			 setValue(ChartHistoMovingAverage.valueOf(text));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The provided value for the chart moving-average is invalid!");
		}
    }
}
