package ntut.csie.sslab.account.users.query.usecase;

import ntut.csie.sslab.account.users.command.adapter.repository.UserData;
import ntut.csie.sslab.account.users.command.entity.User;

public class ConvertUserToDto {
	public static UserDto transform(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setNickname(user.getNickname());
		dto.setRole(user.getRole().toString());
		return dto;
	}

	public static UserDto transform(UserData userData) {
		UserDto dto = new UserDto();
		dto.setId(userData.getId());
		dto.setUsername(userData.getUsername());
		dto.setEmail(userData.getEmail());
		dto.setNickname(userData.getNickname());
		dto.setRole(userData.getRole());
		return dto;
	}
}
