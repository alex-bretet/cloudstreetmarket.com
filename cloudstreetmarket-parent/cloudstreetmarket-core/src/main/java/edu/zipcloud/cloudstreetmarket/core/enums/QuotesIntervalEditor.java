package edu.zipcloud.cloudstreetmarket.core.enums;

import java.beans.PropertyEditorSupport;

public class QuotesIntervalEditor extends PropertyEditorSupport {
    public void setAsText(String text) {
		try{
			 setValue(QuotesInterval.valueOf(text));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The provided value for the quote-interval variable is invalid!");
		}
    }
}
