package ntut.csie.sslab.miro.usecase.line.delete;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class DeleteLineUseCaseImpl implements DeleteLineUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public DeleteLineUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(DeleteLineInput input, CqrsCommandOutput output) {
        Line line = figureRepository.findLineById(input.getLineId()).orElse(null);
        if (line == null){
            output.setId(input.getLineId())
                    .setMessage("Delete line failed: line not found, line id = " + input.getLineId());
            return;
        }
        line.markAsRemoved(input.getBoardId());
        figureRepository.deleteById(input.getLineId());
        domainEventBus.postAll(line);

        output.setId(line.getId());
    }

    @Override
    public DeleteLineInput newInput() {
        return new DeleteLineInputImpl();
    }

    private class DeleteLineInputImpl implements DeleteLineInput {
        private String lineId;
        private String boardId;

        @Override
        public String getLineId() {
            return lineId;
        }

        @Override
        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }
    }
}