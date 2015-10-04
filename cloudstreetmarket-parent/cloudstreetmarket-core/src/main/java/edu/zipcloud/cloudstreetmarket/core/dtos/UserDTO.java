/***
 *  Cloudstreetmarket.com is a Spring MVC showcase application developed 
 *  with the book Spring MVC Cookbook [PACKT] (2015). 
 * 	Copyright (C) 2015  Alex Bretet
 *  
 *  This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 **/
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
