package ntut.csie.sslab.account.users.command.usecase.user.register;

import ntut.csie.sslab.account.users.command.entity.User;
import ntut.csie.sslab.account.users.command.usecase.Encrypt;
import ntut.csie.sslab.account.users.command.usecase.UserRepository;

import ntut.csie.sslab.account.users.command.entity.UserBuilder;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class RegisterUseCaseImpl implements RegisterUseCase {
	private UserRepository userRepository;
	private DomainEventBus domainEventBus;
	private Encrypt encrypt;

	public RegisterUseCaseImpl(UserRepository userRepository, DomainEventBus domainEventBus, Encrypt encrypt) {
		this.userRepository = userRepository;
		this.domainEventBus = domainEventBus;
		this.encrypt = encrypt;
	}

	@Override
	public void execute(RegisterInput input, CqrsCommandOutput output) {
		boolean usernameExist = isUsernameExist(input.getUsername());
		boolean emailExist = isEmailExist(input.getEmail());

		if(isThirdPartyAndUserAlreadyExist(input)) {
			String userId = userRepository.getUserByUsername(input.getUsername()).get().getId();
			output.setId(userId);
			output.setExitCode(ExitCode.SUCCESS);
			return;
		}

		if(isThirdPartyAndUsernameOrEmailExist(input)) {
			output.setExitCode(ExitCode.FAILURE)
					.setMessage("Third party registration failed, internal error: username or email already exists");
			return;
		}

		if(NotThirdPartyAndUsernameOrEmailExist(input)) {
			String errorMessage = "";
			if(usernameExist) {
				errorMessage = "Username:"+ input.getUsername() + " already exists.";
			}else if(emailExist){
				errorMessage = "Email:"+ input.getEmail() + " already exists.";
			}
			output.setExitCode(ExitCode.FAILURE)
					.setMessage(errorMessage);
			return;
		}

		User user = UserBuilder.newInstance()
				.username(input.getUsername())
				.email(input.getEmail())
				.password(encrypt.encrypt(input.getPassword()))
				.nickname(input.getNickname())
				.role(input.getRole())
				.build();

		userRepository.save(user);
		domainEventBus.postAll(user);

		output.setId(user.getId());
		output.setExitCode(ExitCode.SUCCESS);
	}

	private boolean isThirdPartyAndUserAlreadyExist(RegisterInput input) {
		return input.isThirdParty() &&
				isUsernameExist(input.getUsername()) &&
				isEmailExist(input.getEmail());
	}

	private boolean isThirdPartyAndUsernameOrEmailExist(RegisterInput input) {
		return input.isThirdParty() &&
				(isUsernameExist(input.getUsername())
						!= isEmailExist(input.getEmail()));
	}

	private boolean NotThirdPartyAndUsernameOrEmailExist(RegisterInput input) {
		return !input.isThirdParty() &&
				(isUsernameExist(input.getUsername()) ||
						isEmailExist(input.getEmail()));
	}

	private boolean isUsernameExist(String username) {
		return userRepository.getUserByUsername(username).orElse(null) != null;
	}
	private boolean isEmailExist(String email) {
		return userRepository.getUserByEmail(email).orElse(null) != null;
	}

	@Override
	public RegisterInput newInput() {return new RegisterInputImpl();}

	private static class RegisterInputImpl implements RegisterInput {

		private String username;
		private String email;
		private String password;
		private String nickname;
		private String role;
		private boolean isThirdParty;

		@Override
		public String getUsername() {
			return username;
		}

		@Override
		public void setUsername(String username) {
			this.username = username;
		}

		@Override
		public String getEmail() {
			return email;
		}

		@Override
		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public void setPassword(String password) {
			this.password = password;
		}

		@Override
		public String getNickname() {
			return nickname;
		}

		@Override
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		@Override
		public String getRole() {
			return role;
		}

		@Override
		public void setRole(String role) {
			this.role = role;
		}

		public boolean isThirdParty() {
			return isThirdParty;
		}

		public void setThirdParty(boolean thirdParty) {
			isThirdParty = thirdParty;
		}
	}
}
