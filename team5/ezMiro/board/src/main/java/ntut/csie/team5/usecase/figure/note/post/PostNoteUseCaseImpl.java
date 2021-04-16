package ntut.csie.team5.usecase.figure.note.post;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
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
                .position(input.getPosition())
                .color(input.getColor())
                .build();

        figureRepository.save(note);

        output.setId(note.getNoteId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    public class PostNoteInputImpl implements PostNoteInput {

        private String boardId;
        private Point position;
        private Color color;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public Point getPosition() {
            return position;
        }

        public void setPosition(Point position) {
            this.position = position;
        }

        @Override
        public Color getColor() {
            return color;
        }

        @Override
        public void setColor(Color color) {
            this.color = color;
        }
    }
}
