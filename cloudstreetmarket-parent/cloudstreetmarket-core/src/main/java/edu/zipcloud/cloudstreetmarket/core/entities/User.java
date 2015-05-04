package edu.zipcloud.cloudstreetmarket.core.entities;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.zipcloud.cloudstreetmarket.core.enums.SupportedCurrency;

@Entity
@Table(name="users")
public class User implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1990856213905768044L;

	@Id
	@Column(nullable = false)
	private String username;
	
	private String fullname;
	
	private String email;
	
	private String password;
	
	private boolean enabled = true;
	
	private String profileImg;
	
	@Column(name="not_expired")
	private boolean accountNonExpired;
	
	@Column(name="not_locked")
	private boolean accountNonLocked;

	@Enumerated(EnumType.STRING)
	private SupportedCurrency currency;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	@OrderBy("id desc")
	private Set<Action> actions = new LinkedHashSet<Action>();

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private Set<Authority> authorities = new LinkedHashSet<Authority>();

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<SocialUser> socialUsers = new LinkedHashSet<SocialUser>();
	
	public User(){
		
	}
	
	public User(String username, String password, String email, boolean enabled, boolean accountNonExpired,
			boolean accountNonLocked, boolean credentialNotExpired, Set<Authority> auth) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.enabled = enabled;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.authorities = auth;
	}

	public User(User user, Set<Authority> authorities) {
		this(user.getUsername(), user.getPassword(), user.getEmail(), user.isEnabled(), user.isAccountNonExpired(), user.isAccountNonLocked(), true, authorities);
	}

	public User(String userId) {
		this.username = userId;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		this.profileImg = !profileImg.equals("img/anon.png") ? profileImg : null;
	}

	public Set<Action> getActions() {
		return actions;
	}

	public void setActions(Set<Action> actions) {
		this.actions = actions;
	}

	public void setUsername(String userName) {
		this.username = userName;
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
	public String getUsername() {
		return username;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (enabled != other.enabled)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}