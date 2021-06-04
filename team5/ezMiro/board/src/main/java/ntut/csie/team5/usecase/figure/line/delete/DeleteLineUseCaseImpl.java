package ntut.csie.team5.usecase.figure.line.delete;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.figure.line.LineRepository;
import ntut.csie.team5.usecase.figure.line.delete.DeleteLineInput;
import ntut.csie.team5.usecase.figure.line.delete.DeleteLineUseCase;

public class DeleteLineUseCaseImpl implements DeleteLineUseCase {

    private final LineRepository lineRepository;
    private final DomainEventBus domainEventBus;

    public DeleteLineUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public DeleteLineInput newInput() {
        return new DeleteLineInputImpl();
    }

    @Override
    public void execute(DeleteLineInput input, CqrsCommandOutput output) {
        Line line = lineRepository.findById(input.getFigureId()).orElse(null);

        if(null == line)
        {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Delete line failed: line not found, line id = " + input.getFigureId());
            return;
        }

        line.markAsRemoved();

        lineRepository.deleteById(line.getId());
        domainEventBus.postAll(line);

        output.setId(line.getId());
        output.setExitCode(ExitCode.SUCCESS);

    }

    private class DeleteLineInputImpl implements DeleteLineInput {

        private String figureId;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }
    }
}
