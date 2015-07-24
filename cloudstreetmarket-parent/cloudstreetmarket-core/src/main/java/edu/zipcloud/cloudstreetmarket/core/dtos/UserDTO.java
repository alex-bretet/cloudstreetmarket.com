package edu.zipcloud.cloudstreetmarket.core.dtos;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.entities.User;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;

@XStreamAlias("activity")
public class UserDTO {
	
	private String id;
	private String fullname;
	private String profileImg;
	private String password;
	private String email;
	
	private SupportedCurrency currency;
	private BigDecimal balance;
	private String language;
	private Set<ActionDTO> actions;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getProfileImg() {
		return profileImg;
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
	public Set<ActionDTO> getActions(Set<Action> actions) {
		return this.actions;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public void setActions(Set<Action> actions) {
		this.actions = actions.stream()
				.map(ActionDTO::new)
				.collect(Collectors.toSet());
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
	public Set<ActionDTO> getActions() {
		return actions;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public UserDTO (User user){
		this.setFullname(user.getFullname());
		this.setProfileImg(user.getProfileImg());
		this.setId(user.getId());
		this.setCurrency(user.getCurrency());
		this.setActions(user.getActions());
		this.setBalance(user.getBalance());
		this.setEmail(user.getEmail());
		this.setPassword(user.getPassword());
		this.setLanguage(user.getLanguage().name());
	}
}
