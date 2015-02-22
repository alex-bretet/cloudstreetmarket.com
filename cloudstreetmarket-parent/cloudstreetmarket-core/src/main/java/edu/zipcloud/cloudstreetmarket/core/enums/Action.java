package edu.zipcloud.cloudstreetmarket.core.enums;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Action implements Serializable{
	BUY("buys"), SELL("sells");
	
	private String presentTense;
	
	Action(String present){
		presentTense = present;
	}

	public String getPresentTense(){
		return presentTense;
	}
	
	public String getType(){
		return this.name();
	}
}
