package ntut.csie.sslab.account.users.command.usecase.user.unregister;

import ntut.csie.sslab.account.users.command.entity.User;
import ntut.csie.sslab.account.users.command.usecase.Encrypt;
import ntut.csie.sslab.account.users.command.usecase.UserRepository;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class UnregisterUseCaseImpl implements UnregisterUseCase {
	private UserRepository userRepository;
	private DomainEventBus domainEventBus;
	private Encrypt encrypt;

	public UnregisterUseCaseImpl(UserRepository userRepository, DomainEventBus domainEventBus, Encrypt encrypt) {
		this.userRepository = userRepository;
		this.domainEventBus = domainEventBus;
		this.encrypt = encrypt;
	}

	@Override
	public void execute(UnregisterInput input, CqrsCommandOutput output) {
		User user = userRepository.findById(input.getUserId()).orElse(null);

		if(encrypt.checkPassword(input.getPassword(), user.getPassword())) {
			user.deactivate("");
			output.setId(user.getId());
			output.setExitCode(ExitCode.SUCCESS);
			userRepository.save(user);
			domainEventBus.postAll(user);
		} else {
			output.setExitCode(ExitCode.FAILURE)
					.setMessage("Wrong password.");
		}

	}

	@Override
	public UnregisterInput newInput() {return new UnregisterInputImpl();}

	private static class UnregisterInputImpl implements UnregisterInput {

		private String userId;
		private String password;

		@Override
		public String getUserId() {
			return userId;
		}

		@Override
		public void setUserId(String userId) {
			this.userId = userId;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public void setPassword(String password) {
			this.password = password;
		}
	}
}
