package ntut.csie.sslab.account.users.command.adapter.repository;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name="accountUserData")
@Table(name = "user")
public class UserData  {

	@Id
	private String id;

	@Column(name="username")
	private String username;

	@Column(name="email")
	private String email;

	@Column(name="password")
	private String password;

	@Column(name="nickname")
	private String nickname;

	@Column(name="role")
	private String role;

	@Column(name="activated")
	private boolean activated;
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return true;
	}

	public boolean isAccountNonLocked() {
		return true;
	}

	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}
}
