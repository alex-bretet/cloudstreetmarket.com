package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableSerializer;
import edu.zipcloud.cloudstreetmarket.core.converters.IdentifiableToIdConverter;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@Entity
@DiscriminatorValue(CommentAction.DISCR)
@XStreamAlias("comment_event")
public class CommentAction extends SocialAction{

	public static final String DISCR = "comment";

	@OneToOne(fetch = FetchType.EAGER)
	@JsonSerialize(using=IdentifiableSerializer.class)
	@JsonProperty("targetActionId")
	@XStreamConverter(value=IdentifiableToIdConverter.class, strings={"id"})
	@XStreamAlias("targetActionId")
	private Action targetAction;
	
	private String comment;
	
	public CommentAction(){
	}
	
	public CommentAction(User user, Action targetAction, String comment) {
		setUser(user);
		setType(UserActivityType.COMMENT);
		setTargetAction(targetAction);
		setDate(new Date());
		setComment(comment);
	}
	
	public Action getTargetAction() {
		return targetAction;
	}

	public void setTargetAction(Action targetAction) {
		this.targetAction = targetAction;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
