package ntut.csie.team5.usecase.figure.connectable_figure.note.post;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.FigureType;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.entity.model.figure.note.NoteBuilder;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;

public class PostNoteUseCaseImpl implements PostNoteUseCase {

    private NoteRepository noteRepository;
    private DomainEventBus domainEventBus;

    public PostNoteUseCaseImpl(NoteRepository noteRepository, DomainEventBus domainEventBus) {
        this.noteRepository = noteRepository;
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
                .leftTopPositionX(input.getLeftTopPositionX())
                .leftTopPositionY(input.getLeftTopPositionY())
                .height(input.getHeight())
                .width(input.getWidth())
                .color(input.getColor())
                .figureType(input.getFigureType())
                .build();

        noteRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class PostNoteInputImpl implements PostNoteInput {

        private String boardId;
//        private Point leftTopPosition;
        private int leftTopPositionX;
        private int leftTopPositionY;
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
        public int getLeftTopPositionX() {
            return leftTopPositionX;
        }

        @Override
        public void setLeftTopPositionX(int leftTopPositionX) {
            this.leftTopPositionX = leftTopPositionX;
        }

        @Override
        public int getLeftTopPositionY() {
            return leftTopPositionY;
        }

        @Override
        public void setLeftTopPositionY(int leftTopPositionY) {
            this.leftTopPositionY = leftTopPositionY;
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
