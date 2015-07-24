package edu.zipcloud.cloudstreetmarket.core.enums;

import java.io.Serializable;
import static edu.zipcloud.cloudstreetmarket.core.i18n.I18nKeys.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserActivityType implements Serializable{
	REGISTER(I18N_ACTION_REGISTERS), BUY(I18N_ACTION_BUYS), SELL(I18N_ACTION_SELLS);
	
	private String presentTense;
	
	private UserActivityType(String present){
		this.presentTense = present;
	}
	
	public String getPresentTense(){
		return presentTense;
	}
	
	public String getType(){
		return this.name();
	}
}
