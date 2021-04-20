package ntut.csie.sslab.account.users.command.adapter.repository;

import ntut.csie.sslab.account.users.command.entity.Role;
import ntut.csie.sslab.account.users.command.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

	public static List<User> transformToDomain(List<UserData> datas) {
		List<User> users = new ArrayList<>();
		datas.forEach(x -> users.add(transformToDomain(x)));
		return users;
	}

	public static User transformToDomain(UserData data) {
		User user = new User(data.getId(), data.getUsername(), data.getEmail(), data.getPassword(), data.getNickname(), Role.valueOf(data.getRole()));

		if(data.isActivated()) {
			user.activate();
		}else {
			user.deactivate("");
		}

		user.clearDomainEvents();
		return user;
	}
	
	public static UserData transformToData(User user) {
		UserData data = new UserData();
		data.setId(user.getId());
		data.setUsername(user.getUsername());
		data.setEmail(user.getEmail());
		data.setPassword(user.getPassword());
		data.setNickname(user.getNickname());
		data.setRole(user.getRole().toString());
		data.setActivated(user.isActivated());
		return data;
	}
}
