package edu.zipcloud.cloudstreetmarket.core.enums;

import java.beans.PropertyEditorSupport;

import edu.zipcloud.cloudstreetmarket.core.enums.MarketCode;

public class MarketCodeEditor extends PropertyEditorSupport {
    public void setAsText(String text) {
		try{
			 setValue(MarketCode.valueOf(text));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("The provided value for the market code variable is invalid!");
		}
    }
}
