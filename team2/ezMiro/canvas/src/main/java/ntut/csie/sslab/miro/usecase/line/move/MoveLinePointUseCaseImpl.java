package ntut.csie.sslab.miro.usecase.line.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class MoveLinePointUseCaseImpl implements MoveLinePointUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public MoveLinePointUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(MoveLinePointInput input, CqrsCommandOutput output) {
        Line line = figureRepository.findLineById(input.getLineId()).orElse(null);
        if (line == null){
            output.setId(input.getLineId())
                    .setMessage("Move line point failed: line not found, line id = " + input.getLineId());
            return;
        }

        line.movePoint(input.getLinePoint(), input.getPointDelta());

        figureRepository.save(line);
        domainEventBus.postAll(line);

        output.setId(line.getId());
    }

    @Override
    public MoveLinePointInput newInput() {
        return new MoveLinePointInputImpl();
    }

    private class MoveLinePointInputImpl implements MoveLinePointInput {
        String lineId;
        Coordinate pointDelta;
        LinePoint linePoint;
        @Override
        public String getLineId() {
            return this.lineId;
        }

        @Override
        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        @Override
        public Coordinate getPointDelta() {
            return pointDelta;
        }

        @Override
        public void setPointDelta(Coordinate pointDelta) {
            this.pointDelta = pointDelta;
        }

        @Override
        public LinePoint getLinePoint() {
            return linePoint;
        }

        @Override
        public void setLinePoint(LinePoint linePoint) {
            this.linePoint = linePoint;
        }
    }
}