package ntut.csie.islab.team.usecase;

import ntut.csie.islab.account.users.usecase.register.RegisterInput;
import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface CreateTeamUseCase extends Command<CreateTeamUseCaseInput, CqrsCommandOutput> {
}
