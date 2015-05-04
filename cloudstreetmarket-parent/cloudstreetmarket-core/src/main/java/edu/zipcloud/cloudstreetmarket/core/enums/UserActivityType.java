package edu.zipcloud.cloudstreetmarket.core.enums;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserActivityType implements Serializable{
	REGISTER("registers"), BUY("buys"), SELL("sells");
	
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
