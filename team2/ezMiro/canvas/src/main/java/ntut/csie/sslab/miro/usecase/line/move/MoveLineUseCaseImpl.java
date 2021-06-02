package ntut.csie.sslab.miro.usecase.line.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class MoveLineUseCaseImpl implements MoveLineUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public MoveLineUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(MoveLineInput input, CqrsCommandOutput output) {
        Line line = figureRepository.findLineById(input.getLineId()).orElse(null);
        if (line == null){
            output.setId(input.getLineId())
                    .setMessage("Move line failed: line not found, line id = " + input.getLineId());
            return;
        }

        line.move(input.getDelta());

        figureRepository.save(line);
        domainEventBus.postAll(line);

        output.setId(line.getId());
    }

    @Override
    public MoveLineInput newInput() {
        return new MoveLineInputImpl();
    }

    private class MoveLineInputImpl implements MoveLineInput {
        private String lineId;
        private Coordinate delta;

        public String getLineId() {
            return lineId;
        }

        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        public Coordinate getDelta() {
            return delta;
        }

        public void setDelta(Coordinate delta) {
            this.delta = delta;
        }
    }
}