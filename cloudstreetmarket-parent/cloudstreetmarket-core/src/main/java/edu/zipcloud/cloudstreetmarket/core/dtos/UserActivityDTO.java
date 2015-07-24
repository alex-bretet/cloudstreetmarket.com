package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;

@XStreamAlias("activity")
public class UserActivityDTO {
	
	private String userName;
	private String urlProfilePicture;
	private UserActivityType userActivity;
	private String valueShortId;
	private int amount;
	private BigDecimal price;
	private String date;
	private String currency;
	
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
	
	public UserActivityDTO(String userName, String urlProfilePicture, UserActivityType userActivity, Date date) {
		this.userName = userName;
		this.urlProfilePicture = urlProfilePicture;
		this.userActivity = userActivity;
		this.date = dateFormatter.format(date);
	}
	
	public UserActivityDTO(String userName, String urlProfilePicture, UserActivityType userActivity,
			String valueShortId, int amount, BigDecimal price, SupportedCurrency currency, Date date) {
		this.userName = userName;
		this.urlProfilePicture = urlProfilePicture;
		this.userActivity = userActivity;
		this.valueShortId = valueShortId;
		this.amount = amount;
		this.price = price;
		this.currency = currency.name();
		this.date = dateFormatter.format(date);
	}
	
	public UserActivityDTO(String userName, String urlProfilePicture, UserActivityType userActivity,
			String valueShortId, int amount, BigDecimal price, SupportedCurrency currency, LocalDateTime date) {
		this.userName = userName;
		this.urlProfilePicture = urlProfilePicture;
		this.userActivity = userActivity;
		this.valueShortId = valueShortId;
		this.amount = amount;
		this.price = price;
		this.currency = currency.name();
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
	public UserActivityType getUserAction() {
		return userActivity;
	}
	public void setUserAction(UserActivityType userAction) {
		this.userActivity = userAction;
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
