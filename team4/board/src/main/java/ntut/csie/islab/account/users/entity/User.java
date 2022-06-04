package ntut.csie.islab.account.users.entity;

import ntut.csie.islab.account.users.entity.event.PasswordChanged;
import ntut.csie.islab.account.users.entity.event.UserLoggedIn;
import ntut.csie.islab.account.users.entity.event.UserRegistered;
import ntut.csie.sslab.ddd.model.AggregateRoot;

public class User extends AggregateRoot<String> {
	private String email;
	private String password;
	private String username;

	public User(String id, String username, String email, String password) {
		super(id);
		this.username = username;
		this.password = password;
		this.email = email;
		addDomainEvent(new UserRegistered(id, username, password, email));
	}

	public void loginSuccess() {
		addDomainEvent(new UserLoggedIn(id, username));
	}

	public void changePassword(String newPassword) {
		String oldPassword = password;
		setPassword(newPassword);
		addDomainEvent(new PasswordChanged(id, username, oldPassword, newPassword));
	}

	public void changeEmail(String newEmail) {
		String oldEmail = email;
		setEmail(newEmail);
//		addDomainEvent(new EmailChanged(id, username, oldEmail, newEmail));
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
}
