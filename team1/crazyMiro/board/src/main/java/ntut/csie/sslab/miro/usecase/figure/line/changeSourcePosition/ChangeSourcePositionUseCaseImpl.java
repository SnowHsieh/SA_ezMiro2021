package ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition.ChangeSourcePositionInput;
import ntut.csie.sslab.miro.usecase.figure.line.changeSourcePosition.ChangeSourcePositionUseCase;

public class ChangeSourcePositionUseCaseImpl implements ChangeSourcePositionUseCase {
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public ChangeSourcePositionUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public ChangeSourcePositionInput newInput() {
        return new ChangeSourcePositionInputImpl();
    }

    @Override
    public void execute(ChangeSourcePositionInput input, CqrsCommandOutput output) {
        try{
            Line line = lineRepository.findById(input.getFigureId()).get();
            line.setSourcePosition(input.getSourcePosition());

            lineRepository.save(line);
            domainEventBus.postAll(line);

            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.SUCCESS);
        }
        catch (Exception e){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }
    }

    private class ChangeSourcePositionInputImpl implements ChangeSourcePositionInput {
        private String figureId;
        private Coordinate sourcePosition;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public Coordinate getSourcePosition() {
            return sourcePosition;
        }

        @Override
        public void setSourcePosition(Coordinate sourcePosition) {
            this.sourcePosition = sourcePosition;
        }
    }
}
