package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.core.util.ImageUtil;

@XStreamAlias("activity")
public class UserDTO {
	
	private String id;
	private String headline;
	private String profileImg;
	private String password;
	private String email;
	
	private SupportedCurrency currency;
	private BigDecimal balance;
	private String language;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public String getBigProfileImg() {
		return ImageUtil.renameToBig(profileImg);
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	public SupportedCurrency getCurrency() {
		return currency;
	}
	public void setCurrency(SupportedCurrency currency) {
		this.currency = currency;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public UserDTO (User user){
		this.setHeadline(user.getHeadline());
		this.setProfileImg(user.getProfileImg());
		this.setId(user.getId());
		this.setCurrency(user.getCurrency());
		this.setBalance(user.getBalance());
		this.setEmail(user.getEmail());
		this.setPassword(user.getPassword());
		this.setLanguage(user.getLanguage().name());
	}
}
