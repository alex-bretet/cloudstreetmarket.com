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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import edu.zipcloud.cloudstreetmarket.core.entities.Action;
import edu.zipcloud.cloudstreetmarket.core.entities.CommentAction;
import edu.zipcloud.cloudstreetmarket.core.entities.LikeAction;
import edu.zipcloud.cloudstreetmarket.core.entities.SocialEventAction;
import edu.zipcloud.cloudstreetmarket.core.entities.Transaction;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.cloudstreetmarket.core.enums.UserActivityType;
import edu.zipcloud.cloudstreetmarket.core.util.TransactionUtil;

@XStreamAlias("activity")
public class UserActivityDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5661436583630134897L;
	
	/**
	 * Action specific
	 */
	private String userName;
	private String urlProfilePicture;
	private UserActivityType userActivity;
	private String date;
	private Long id;
	
	private long amountOfLikes = 0;
	private long amountOfComments = 0;
	private Map<String, Long> authorOfLikes = new HashMap<String, Long>();
	private List<String> authorOfComments = new ArrayList<String>();
	
	/**
	 * Transaction specific
	 */
	private String valueShortId;
	private int amount;
	private BigDecimal price;
	private String currency;

	/**
	 * Social-actions specific
	 */
	private Long targetActionId;
	private String comment;

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm aaa");
	
	public UserActivityDTO(String userName, String urlProfilePicture, UserActivityType userActivity, Date date, Long id) {
		this.userName = userName;
		this.urlProfilePicture = urlProfilePicture;
		this.userActivity = userActivity;
		this.date = dateFormatter.format(date);
		this.id = id;
	}
	
	public UserActivityDTO(String userName, String urlProfilePicture, UserActivityType userActivity,
			String valueShortId, int amount, BigDecimal price, SupportedCurrency currency, LocalDateTime date, Long id) {
		this.userName = userName;
		this.urlProfilePicture = urlProfilePicture;
		this.userActivity = userActivity;
		this.date = dateFormatter.format(date);
		this.id = id;
		this.valueShortId = valueShortId;
		this.amount = amount;
		this.price = price;
		this.currency = currency.name();
	}
	
	public UserActivityDTO(Action action) {
		this.userName = action.getUser().getId();
		this.urlProfilePicture = action.getUser().getProfileImg();
		this.userActivity = action.getType();
		this.date = dateFormatter.format(action.getDate() != null ? action.getDate() : new Date());
		this.id = action.getId();
	}
		
	public UserActivityDTO(Transaction transaction) {
		this((Action)transaction);
		this.price = TransactionUtil.getPrice(transaction.getQuote(), transaction.getType(), 1);
		this.valueShortId = transaction.getQuote().getStock().getId();
		this.amount = transaction.getQuantity();
		this.currency = transaction.getQuote().getCurrency();
	}
	
	public UserActivityDTO(LikeAction likeAction) {
		this.userName = likeAction.getUser().getId();
		this.urlProfilePicture = likeAction.getUser().getProfileImg();
		this.userActivity = likeAction.getType();
		this.date = dateFormatter.format(likeAction.getDate() != null ? likeAction.getDate() : new Date());
		this.id = likeAction.getId();
		this.targetActionId = likeAction.getTargetAction().getId();
	}
	
	public UserActivityDTO(CommentAction commentAction) {
		this((Action)commentAction);
		this.targetActionId = commentAction.getTargetAction().getId();
		this.comment = commentAction.getComment();
	}
	
	public void setSocialReport(Set<SocialEventAction> socialEventActions){
		this.authorOfLikes = socialEventActions.stream()
								.filter(a -> a.getType().equals(UserActivityType.LIKE))
								.collect(Collectors.toMap(a -> a.getUser().getId(), a -> a.getId()));
		
		this.amountOfLikes = authorOfLikes.size();
		//this.amountOfComments = comments.size();
		//this.authorOfComments = comments.stream().map(a -> a.getUser().getId()).collect(Collectors.toList());
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getTargetActionId() {
		return targetActionId;
	}
	public void setTargetActionId(Long targetActionId) {
		this.targetActionId = targetActionId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getAmountOfLikes() {
		return amountOfLikes;
	}
	public void setAmountOfLikes(Long amountOfLikes) {
		this.amountOfLikes = amountOfLikes;
	}
	public Long getAmountOfComments() {
		return amountOfComments;
	}
	public void setAmountOfComments(Long amountOfComments) {
		this.amountOfComments = amountOfComments;
	}
	public Map<String, Long> getAuthorOfLikes() {
		return authorOfLikes;
	}
	public void setAuthorOfLikes(Map<String, Long> authorOfLikes) {
		this.authorOfLikes = authorOfLikes;
	}
	public List<String> getAuthorOfComments() {
		return authorOfComments;
	}
	public void setAuthorOfComments(List<String> authorOfComments) {
		this.authorOfComments = authorOfComments;
	}
	public void setAmountOfLikes(long amountOfLikes) {
		this.amountOfLikes = amountOfLikes;
	}
	public void setAmountOfComments(long amountOfComments) {
		this.amountOfComments = amountOfComments;
	}
}
