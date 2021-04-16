package ntut.csie.sslab.account.users.command.entity;

import ntut.csie.sslab.account.users.command.entity.event.*;
import ntut.csie.sslab.ddd.model.AggregateRoot;

public class User extends AggregateRoot<String> {
	private String email;
	private String password;
	private String nickname;
	private String username;
	private Role role;
	private boolean activated;

	public User(String id, String username, String email, String password, String nickname, Role role) {
		super(id);
		this.username = username;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.role = role;
		this.activated = true;
		addDomainEvent(new UserRegistered(id, username, password, email, nickname, role.toString()));
	}

	public boolean isActivated() {
		return activated;
	}

	public void activate() {
		this.activated = true;
	}

	public void deactivate(String managerUserId) {
		this.activated = false;
		addDomainEvent(new Unregistered(managerUserId, id));
	}

	public void loginSuccess() {
		addDomainEvent(new UserLoggedIn(id, username));
	}

	public void loginFail() {
		addDomainEvent(new UserLoggedInFail(id, username));
	}

	public void changePassword(String newPassword) {
		String oldPassword = password;
		setPassword(newPassword);
		addDomainEvent(new PasswordChanged(id, username, oldPassword, newPassword));
	}

	public void changeEmail(String newEmail) {
		String oldEmail = email;
		setEmail(newEmail);
		addDomainEvent(new EmailChanged(id, username, oldEmail, newEmail));
	}

	public void changeNickname(String newNickname) {
		String oldNickname = nickname;
		setNickname(newNickname);
		addDomainEvent(new NicknameChanged(id, username, oldNickname, newNickname));
	}

	public String getUsername() {
		return username;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
