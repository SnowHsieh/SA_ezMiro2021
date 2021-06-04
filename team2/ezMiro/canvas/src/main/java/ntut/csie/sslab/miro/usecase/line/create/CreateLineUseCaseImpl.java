package ntut.csie.sslab.miro.usecase.line.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.line.ArrowStyle;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.entity.model.figure.line.LineBuilder;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class CreateLineUseCaseImpl implements CreateLineUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public CreateLineUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateLineInput input, CqrsCommandOutput output) {
        Line line = LineBuilder.newInstance()
                .boardId(input.getBoardId())
                .startConnectableFigureId(input.getStartConnectableFigureId())
                .endConnectableFigureId(input.getEndConnectableFigureId())
                .startOffset(input.getStartOffset())
                .endOffset(input.getEndOffset())
                .startArrowStyle(ArrowStyle.NONE)
                .endArrowStyle(ArrowStyle.STANDARD)
                .build();

        figureRepository.save(line);
        domainEventBus.postAll(line);

        output.setId(line.getId());
    }

    @Override
    public CreateLineInput newInput() {
        return new CreateLineInputImpl();
    }

    private class CreateLineInputImpl implements CreateLineInput {
        private String boardId;
        private String startConnectableFigureId;
        private String endConnectableFigureId;
        private Coordinate startOffset;
        private Coordinate endOffset;
        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getStartConnectableFigureId() {
            return startConnectableFigureId;
        }

        @Override
        public void setStartConnectableFigureId(String startConnectableFigureId) {
            this.startConnectableFigureId = startConnectableFigureId;
        }

        @Override
        public String getEndConnectableFigureId() {
            return endConnectableFigureId;
        }

        @Override
        public void setEndConnectableFigureId(String endConnectableFigureId) {
            this.endConnectableFigureId = endConnectableFigureId;
        }

        @Override
        public Coordinate getStartOffset() {
            return startOffset;
        }

        @Override
        public void setStartOffset(Coordinate startOffset) {
            this.startOffset = startOffset;
        }

        @Override
        public Coordinate getEndOffset() {
            return endOffset;
        }

        @Override
        public void setEndOffset(Coordinate endOffset) {
            this.endOffset = endOffset;
        }
    }
}