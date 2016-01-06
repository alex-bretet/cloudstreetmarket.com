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

	@Column(name="full_name")
	private String fullName;
	
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

	private BigDecimal balance;
	
	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	@XStreamOmitField
	@OrderBy("id desc")
	private Set<Action> actions = new LinkedHashSet<Action>();

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
	
	public User(String id, String password, String email, String fullName, boolean enabled, boolean accountNonExpired,
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
		this.fullName = fullName;
		this.language = language;
	}

	public User(User user, Set<Authority> authorities) {
		this(user.getId(), user.getPassword(), user.getEmail(), user.getFullName(), user.isEnabled(), user.isAccountNonExpired(), user.isAccountNonLocked(), true, authorities, user.getCurrency(), user.getBalance(), user.getLanguage());
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	
	public Locale getLocale(){
		return new Locale.Builder().setLanguage((language ==  null ? SupportedLanguage.EN : language).name().toLowerCase()).build();
	}

	@Override
	public String toString() {
		return "User [fullName=" + fullName + ", email=" + email
				+ ", password=" + password + ", enabled=" + enabled
				+ ", profileImg=" + profileImg + ", accountNonExpired="
				+ accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", currency=" + currency + ", balance=" +balance +", authorities=" + authorities
				+ ", lastUpdate=" + lastUpdate + ", id=" + id + "]";
	}
}