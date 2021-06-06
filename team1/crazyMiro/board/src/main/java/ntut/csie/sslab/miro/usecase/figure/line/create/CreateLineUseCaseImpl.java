package ntut.csie.sslab.miro.usecase.figure.line.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;

import java.util.UUID;

public class CreateLineUseCaseImpl implements CreateLineUseCase {

    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public CreateLineUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateLineInput input, CqrsCommandOutput output) {
        try{
            if(input.getSourcePosition() == null && input.getTargetPosition() == null){
                input.setSourcePosition(new Coordinate(-1, -1));
                input.setTargetPosition(new Coordinate(-1, -1));
            }
            Line line = new Line(input.getBoardId(),
                    UUID.randomUUID().toString(),
                    input.getSourceId(),
                    input.getTargetId(),
                    input.getSourcePosition(),
                    input.getTargetPosition());

            lineRepository.save(line);
            domainEventBus.postAll(line);

            output.setId(line.getId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch(Exception e){
            output.setExitCode(ExitCode.FAILURE).setMessage(e.getMessage());
        }
    }

    @Override
    public CreateLineInput newInput() {
        return new CreateLineInputImpl();
    }

    private class CreateLineInputImpl implements CreateLineInput {
        private String boardId;
        private String sourceId;
        private String targetId;
        private Coordinate sourcePosition;
        private Coordinate targetPosition;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getSourceId() {
            return sourceId;
        }

        @Override
        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        @Override
        public String getTargetId() {
            return targetId;
        }

        @Override
        public void setTargetId(String targetId) {
            this.targetId = targetId;
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
