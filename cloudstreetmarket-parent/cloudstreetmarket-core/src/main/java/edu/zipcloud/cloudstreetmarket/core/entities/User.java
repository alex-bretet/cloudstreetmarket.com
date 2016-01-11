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
package edu.zipcloud.cloudstreetmarket.core.entities;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;
import edu.zipcloud.cloudstreetmarket.core.enums.SupportedLanguage;


@Entity
@Table(name="users")
public class User extends ProvidedId<String> implements UserDetails{

	private static final long serialVersionUID = 1990856213905768044L;

	@Size(max=140)
	private String headline;
	
	@NotNull
	@Size(min=4, max=30)
	private String email;
	
	@NotNull
	private String password;
	
	private boolean enabled = true;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private SupportedLanguage language;

	@Column(name="profile_img")
	private String profileImg;
	
	@Column(name="not_expired")
	private boolean accountNonExpired;
	
	@Column(name="not_locked")
	private boolean accountNonLocked;

	@NotNull
	@Enumerated(EnumType.STRING)
	private SupportedCurrency currency;

	@Column(precision = 10, scale = 5)
	private BigDecimal balance;
	
	@Column(name="balance_usd", precision = 10, scale = 5)
	private BigDecimal balanceUSD;
	
	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JsonIgnore
	@XStreamOmitField
	@OrderBy("id desc")
	private Set<Action> actions = new LinkedHashSet<Action>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_followers", joinColumns = { @JoinColumn(name = "followed_user_id") },
		inverseJoinColumns = { @JoinColumn(name = "follower_user_id") })
	@JsonIgnore
	@XStreamOmitField
	private Set<User> followers = new LinkedHashSet<User>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_following", joinColumns = { @JoinColumn(name = "follower_user_id") },
		inverseJoinColumns = { @JoinColumn(name = "followed_user_id") })
	@JsonIgnore
	@XStreamOmitField
	private Set<User> following = new LinkedHashSet<User>();
	
	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JsonIgnore
	@XStreamOmitField
	private Set<Authority> authorities = new LinkedHashSet<Authority>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	@XStreamOmitField
	private Set<SocialUser> socialUsers = new LinkedHashSet<SocialUser>();
	
	@Column(name="last_update", insertable=false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;

	public User(){
	
	}
	
	public User(String id){
		setId(id);
	}
	
	public User(String id, String password, String email, String headline, boolean enabled, boolean accountNonExpired,
			boolean accountNonLocked, boolean credentialNotExpired, Set<Authority> auth, SupportedCurrency currency, BigDecimal balance,
			 SupportedLanguage language) {
		setId(id);
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = auth;
		this.currency = currency;
		this.balance = balance;
		this.headline = headline;
		this.language = language;
	}

	public User(User user, Set<Authority> authorities) {
		this(user.getId(), user.getPassword(), user.getEmail(), user.getHeadline(), user.isEnabled(), user.isAccountNonExpired(), user.isAccountNonLocked(), true, authorities, user.getCurrency(), user.getBalance(), user.getLanguage());
	}

	@Override
	public String getUsername() {
		return getId();
	}

	@Override
	public String getPassword() {
		return password;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headLine) {
		this.headline = headLine;
	}

	public String getProfileImg() {
		return profileImg;
	}

	public void setProfileImg(String profileImg) {
		this.profileImg = !profileImg.equals("img/anon.png") ? profileImg : null;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SupportedCurrency getCurrency() {
		return currency;
	}

	public void setCurrency(SupportedCurrency currency) {
		this.currency = currency;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void addAuthority(Authority authority){
		authorities.add(authority);
	}
	
	public void addAction(Action action){
		actions.add(action);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public SupportedLanguage getLanguage() {
		return language;
	}

	public void setLanguage(SupportedLanguage language) {
		this.language = language;
	}

	public Set<User> getFollowers() {
		return followers;
	}

	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}

	public Set<User> getFollowing() {
		return following;
	}

	public void setFollowing(Set<User> following) {
		this.following = following;
	}

	public Set<SocialUser> getSocialUsers() {
		return socialUsers;
	}

	public void setSocialUsers(Set<SocialUser> socialUsers) {
		this.socialUsers = socialUsers;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Locale getLocale(){
		return new Locale.Builder().setLanguage((language ==  null ? SupportedLanguage.EN : language).name().toLowerCase()).build();
	}

	public BigDecimal getBalanceUSD() {
		return balanceUSD;
	}

	public void setBalanceUSD(BigDecimal balanceUSD) {
		this.balanceUSD = balanceUSD;
	}

	@Override
	public String toString() {
		return "User [headline=" + headline + ", email=" + email
				+ ", password=" + password + ", enabled=" + enabled
				+ ", profileImg=" + profileImg + ", accountNonExpired="
				+ accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", currency=" + currency + ", balance=" +balance +", authorities=" + authorities
				+ ", lastUpdate=" + lastUpdate + ", id=" + id + "]";
	}
}