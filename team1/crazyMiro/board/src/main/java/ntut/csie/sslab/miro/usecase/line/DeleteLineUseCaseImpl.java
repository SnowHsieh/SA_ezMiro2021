package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineUseCase;

public class deleteLineUseCaseImpl implements DeleteLineUseCase {
    public deleteLineUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
    }
}
