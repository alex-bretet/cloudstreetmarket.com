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
public class LikeAction extends SocialEventAction{

	private static final long serialVersionUID = 8864286913794338818L;
	
	public static final String DISCR = "like";
	
	public LikeAction(){
	}
	
	public LikeAction(User user, Action targetAction) {
		setUser(user);
		setType(UserActivityType.LIKE);
		setDate(new Date());
		setTargetAction(targetAction);
	}
}
