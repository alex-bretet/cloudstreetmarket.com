package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@Entity
@DiscriminatorValue(LikeAction.DISCR)
@XStreamAlias("like_event")
public class LikeAction extends SocialAction{

	public static final String DISCR = "like";

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("targetActionId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("targetActionId")
	private Action targetAction;
	
	public LikeAction(){
	}
	
	public LikeAction(User user, Action targetAction) {
		setUser(user);
		setType(UserActivityType.LIKE);
		setDate(new Date());
		setTargetAction(targetAction);
	}

	public Action getTargetAction() {
		return targetAction;
	}

	public void setTargetAction(Action targetAction) {
		this.targetAction = targetAction;
	}
}
