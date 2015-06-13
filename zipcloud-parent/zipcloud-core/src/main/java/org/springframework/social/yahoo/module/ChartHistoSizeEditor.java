package org.springframework.social.yahoo.module;

import java.beans.PropertyEditorSupport;

public class ChartHistoSizeEditor extends PropertyEditorSupport {
    public void setAsText(String text) {
		try{
			 setValue(ChartHistoSize.valueOf(text));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The provided value for the chart size parameter is invalid!");
		}
    }
}
