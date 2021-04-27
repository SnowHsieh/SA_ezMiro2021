package ntut.csie.team5.usecase.figure.note.post;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.entity.model.figure.note.NoteBuilder;
import ntut.csie.team5.usecase.figure.note.FigureRepository;

import java.awt.*;

public class PostNoteUseCaseImpl implements PostNoteUseCase {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public PostNoteUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public PostNoteInput newInput() {
        return new PostNoteUseCaseImpl.PostNoteInputImpl();
    }

    @Override
    public void execute(PostNoteInput input, CqrsCommandOutput output) {
        Note note = NoteBuilder.newInstance()
                .boardId(input.getBoardId())
                .leftTopPosition(input.getLeftTopPosition())
                .rightBottomPosition(input.getRightBottomPosition())
                .color(input.getColor())
                .figureType(input.getFigureType())
                .build();

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    public class PostNoteInputImpl implements PostNoteInput {

        private String boardId;
        private Point leftTopPosition;
        private Point rightBottomPosition;
        private Color color;
        private FigureType figureType;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public Point getLeftTopPosition() {
            return leftTopPosition;
        }

        @Override
        public void setLeftTopPosition(Point leftTopPosition) {
            this.leftTopPosition = leftTopPosition;
        }

        @Override
        public Point getRightBottomPosition() {
            return rightBottomPosition;
        }

        @Override
        public void setRightBottomPosition(Point rightBottomPosition) {
            this.rightBottomPosition = rightBottomPosition;
        }

        @Override
        public Color getColor() {
            return color;
        }

        @Override
        public void setColor(Color color) {
            this.color = color;
        }

        @Override
        public FigureType getFigureType() {
            return figureType;
        }

        @Override
        public void setFigureType(FigureType figureType) {
            this.figureType = figureType;
        }
    }
}
