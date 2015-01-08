package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import edu.zipcloud.cloudstreetmarket.core.enums.Action;

public class UserActivityDTO {
	
	private String userName;
	private String urlProfilePicture;
	private Action userAction;
	private String valueShortId;
	private int amount;
	private BigDecimal price;
	private Date date;
	
	public UserActivityDTO(String userName, String urlProfilePicture, Action userAction,
			String valueShortId, int amount, BigDecimal price, Date date) {
		this.userName = userName;
		this.urlProfilePicture = urlProfilePicture;
		this.userAction = userAction;
		this.valueShortId = valueShortId;
		this.amount = amount;
		this.price = price;
		this.date = date;
	}
	
	public UserActivityDTO(String userName, String urlProfilePicture, Action userAction,
			String valueShortId, int amount, BigDecimal price, LocalDateTime date) {
		this.userName = userName;
		this.urlProfilePicture = urlProfilePicture;
		this.userAction = userAction;
		this.valueShortId = valueShortId;
		this.amount = amount;
		this.price = price;
		this.date = Date.from(date.toInstant(ZoneOffset.UTC));
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

}
