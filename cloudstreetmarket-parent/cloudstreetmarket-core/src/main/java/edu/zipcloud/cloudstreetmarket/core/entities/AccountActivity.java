package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@Entity
@DiscriminatorValue(AccountActivity.DISCR)
@XStreamAlias("activity")
public class AccountActivity extends Action{

	private static final long serialVersionUID = 2984429385316174465L;
	public static final String DISCR = "acc";

	public AccountActivity(){
		
	}
	public AccountActivity(User user, UserActivityType register, Date date) {
		setUser(user);
		setType(register);
		setDate(date);
	}
}
