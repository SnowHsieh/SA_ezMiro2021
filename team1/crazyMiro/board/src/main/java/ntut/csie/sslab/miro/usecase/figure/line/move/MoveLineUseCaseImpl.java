package ntut.csie.sslab.miro.usecase.figure.line.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;

public class MoveLineUseCaseImpl implements MoveLineUseCase {

    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public MoveLineUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(MoveLineInput input, CqrsCommandOutput output) {
        try{
            Line line = lineRepository.findById(input.getLineId()).get();

            if(line.getSourcePosition().equals(input.getSourcePosition()) &&
                line.getTargetPosition().equals(input.getTargetPosition()))
                return;

            line.setPosition(input.getSourcePosition(), input.getTargetPosition());

            lineRepository.save(line);
            domainEventBus.postAll(line);

            output.setId(input.getLineId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch(Exception e) {
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }
    }

    @Override
    public MoveLineInput newInput() {
        return new MoveLineInputImpl();
    }

    private class MoveLineInputImpl implements MoveLineInput {
        private String lineId;
        private Coordinate sourcePosition;
        private Coordinate targetPosition;

        @Override
        public String getLineId() {
            return lineId;
        }

        @Override
        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        @Override
        public Coordinate getSourcePosition() {
            return sourcePosition;
        }

        @Override
        public void setSourcePosition(Coordinate sourcePosition) {
            this.sourcePosition = sourcePosition;
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
