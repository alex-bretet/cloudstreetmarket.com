package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@Entity
@DiscriminatorValue(CommentAction.DISCR)
@XStreamAlias("comment_event")
public class CommentAction extends SocialEventAction{

	private static final long serialVersionUID = -3584000527863203381L;

	public static final String DISCR = "comment";
	
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
