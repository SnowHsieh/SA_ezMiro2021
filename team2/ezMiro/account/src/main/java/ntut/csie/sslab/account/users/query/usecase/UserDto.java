package ntut.csie.sslab.account.users.query.usecase;

public class UserDto {
	private String id;
	private String username;
	private String email;
	private String nickname;
	private String role;

	public UserDto() {
	}

	public UserDto(String id, String username, String email, String nickname, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.nickname = nickname;
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}
