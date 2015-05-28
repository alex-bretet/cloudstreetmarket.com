package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.util.Date;

import org.springframework.hateoas.ResourceSupport;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@XStreamAlias("action")
public class ActionDTO extends ResourceSupport {
	
	private String userName;
	private Date date;
	private UserActivityType type;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public UserActivityType getType() {
		return type;
	}
	
	public void setType(UserActivityType type) {
		this.type = type;
	}

	public ActionDTO (Action action){
		this.setDate(action.getDate());
		this.setType(action.getType());
		this.setUserName(action.getUser().getUsername());
	}
}
