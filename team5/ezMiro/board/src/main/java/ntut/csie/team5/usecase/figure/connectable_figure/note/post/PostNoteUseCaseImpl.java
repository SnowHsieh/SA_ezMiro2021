package ntut.csie.team5.usecase.figure.connectable_figure.note.post;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.entity.model.figure.note.NoteBuilder;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;

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
        return new PostNoteInputImpl();
    }

    @Override
    public void execute(PostNoteInput input, CqrsCommandOutput output) {
        Note note = NoteBuilder.newInstance()
                .boardId(input.getBoardId())
                .leftTopPosition(input.getLeftTopPosition())
                .height(input.getHeight())
                .width(input.getWidth())
                .color(input.getColor())
                .figureType(input.getFigureType())
                .build();

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class PostNoteInputImpl implements PostNoteInput {

        private String boardId;
        private Point leftTopPosition;
        private int height;
        private int width;
        private String color;
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
        public int getHeight() {
            return height;
        }

        @Override
        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public void setWidth(int width) {
            this.width = width;
        }

        @Override
        public String getColor() {
            return color;
        }

        @Override
        public void setColor(String color) {
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
