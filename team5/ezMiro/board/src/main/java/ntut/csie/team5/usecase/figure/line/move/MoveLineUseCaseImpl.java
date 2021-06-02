package ntut.csie.team5.usecase.figure.line.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.figure.line.LineRepository;

public class MoveLineUseCaseImpl implements MoveLineUseCase {

    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public MoveLineUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public MoveLineInput newInput() {
        return new MoveLineInputImpl();
    }

    @Override
    public void execute(MoveLineInput input, CqrsCommandOutput output) {
        Line line = lineRepository.findById(input.getFigureId()).orElse(null);

        if(null == line)
        {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Move line failed: line not found, line id = " + input.getFigureId());
            return;
        }

        line.moveLine(input.getOffsetX(), input.getOffsetY());
        lineRepository.save(line);
        domainEventBus.postAll(line);

        output.setId(line.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class MoveLineInputImpl implements MoveLineInput {

        private String figureId;
        private int offsetX;
        private int offsetY;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public int getOffsetX() {
            return offsetX;
        }

        @Override
        public void setOffsetX(int offsetX) {
            this.offsetX = offsetX;
        }

        @Override
        public int getOffsetY() {
            return offsetY;
        }

        @Override
        public void setOffsetY(int offsetY) {
            this.offsetY = offsetY;
        }
    }
}
