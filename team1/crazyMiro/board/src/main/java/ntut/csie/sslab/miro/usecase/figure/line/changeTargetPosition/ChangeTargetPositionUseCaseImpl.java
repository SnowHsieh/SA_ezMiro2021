package ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionInput;
import ntut.csie.sslab.miro.usecase.figure.line.changeTargetPosition.ChangeTargetPositionUseCase;

public class ChangeTargetPositionUseCaseImpl implements ChangeTargetPositionUseCase {
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public ChangeTargetPositionUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public ChangeTargetPositionInput newInput() {
        return new ChangeTargetPositionInputImpl();
    }

    @Override
    public void execute(ChangeTargetPositionInput input, CqrsCommandOutput output) {
        try{
            Line line = lineRepository.findById(input.getFigureId()).get();
            line.setTargetPosition(input.getTargetPosition());

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

    private class ChangeTargetPositionInputImpl implements ChangeTargetPositionInput {
        private String figureId;
        private Coordinate targetPosition;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public Coordinate getTargetPosition() {
            return targetPosition;
        }

        @Override
        public void setTargetPosition(Coordinate targetPosition) {
            this.targetPosition = targetPosition;
        }
    }
}
