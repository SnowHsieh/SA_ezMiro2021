package ntut.csie.sslab.miro.usecase.line.connecttofigure;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.entity.model.figure.line.LinePoint;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class ConnectLineToFigureUseCaseImpl implements ConnectLineToFigureUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public ConnectLineToFigureUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ConnectLineToFigureInput input, CqrsCommandOutput output) {
        Line line = figureRepository.findLineById(input.getLineId()).get();
        if (line == null){
            output.setId(input.getLineId())
                    .setMessage("connect line to figure failed: line not found, line id = " + input.getLineId());
            return;
        }
        line.connectToFigure(input.getLinePoint(), input.getFigureId(), input.getOffset());

        figureRepository.save(line);
        domainEventBus.postAll(line);

        output.setId(line.getId());
    }

    @Override
    public ConnectLineToFigureInput newInput() {
        return new ConnectLineToFigureInputImpl();
    }

    private class ConnectLineToFigureInputImpl implements ConnectLineToFigureInput {
        private String lineId;
        private String figureId;
        private LinePoint linePoint;
        private Coordinate offset;
        @Override
        public String getLineId() {
            return lineId;
        }

        @Override
        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public LinePoint getLinePoint() {
            return linePoint;
        }

        @Override
        public void setLinePoint(LinePoint linePoint) {
            this.linePoint = linePoint;
        }

        @Override
        public Coordinate getOffset() {
            return offset;
        }

        @Override
        public void setOffset(Coordinate offset) {
            this.offset = offset;
        }
    }
}