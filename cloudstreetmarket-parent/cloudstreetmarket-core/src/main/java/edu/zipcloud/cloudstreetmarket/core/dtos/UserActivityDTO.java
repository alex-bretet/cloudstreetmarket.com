package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.enums.Action;

@XStreamAlias("activity")
public class UserActivityDTO {
	
	private String userName;
	private String urlProfilePicture;
	private Action userAction;
	private String valueShortId;
	private int amount;
	private BigDecimal price;
	private String date;
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
	
	public UserActivityDTO(String userName, String urlProfilePicture, Action userAction,
			String valueShortId, int amount, BigDecimal price, Date date) {
		this.userName = userName;
		this.urlProfilePicture = urlProfilePicture;
		this.userAction = userAction;
		this.valueShortId = valueShortId;
		this.amount = amount;
		this.price = price;
		this.date = dateFormatter.format(date);
	}
	
	public UserActivityDTO(String userName, String urlProfilePicture, Action userAction,
			String valueShortId, int amount, BigDecimal price, LocalDateTime date) {
		this.userName = userName;
		this.urlProfilePicture = urlProfilePicture;
		this.userAction = userAction;
		this.valueShortId = valueShortId;
		this.amount = amount;
		this.price = price;
		this.date = dateFormatter.format(date);
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUrlProfilePicture() {
		return urlProfilePicture;
	}
	public void setUrlProfilePicture(String urlProfilePicture) {
		this.urlProfilePicture = urlProfilePicture;
	}
	public Action getUserAction() {
		return userAction;
	}
	public void setUserAction(Action userAction) {
		this.userAction = userAction;
	}
	public String getValueShortId() {
		return valueShortId;
	}
	public void setValueShortId(String valueShortId) {
		this.valueShortId = valueShortId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
