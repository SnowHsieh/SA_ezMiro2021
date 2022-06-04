package ntut.csie.islab.account.users.usecase.register;

import ntut.csie.sslab.ddd.usecase.cqrs.Command;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;

public interface RegisterUseCase extends Command<RegisterInput, CqrsCommandOutput> {
}
