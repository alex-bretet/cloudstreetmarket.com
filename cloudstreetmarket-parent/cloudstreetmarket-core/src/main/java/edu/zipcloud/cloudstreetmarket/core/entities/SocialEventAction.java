package edu.zipcloud.cloudstreetmarket.core.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;

@Entity
public abstract class SocialEventAction extends Action{

	private static final long serialVersionUID = 4714279362939453340L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("targetActionId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("targetActionId")
	private Action targetAction;
	
	public Action getTargetAction() {
		return targetAction;
	}

	public void setTargetAction(Action targetAction) {
		this.targetAction = targetAction;
	}
}
