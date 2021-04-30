package ntut.csie.team5.usecase.figure.connectable_figure.note.delete;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;

import java.awt.*;

public class DeleteNoteUseCaseImpl implements DeleteNoteUseCase {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public DeleteNoteUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public DeleteNoteInput newInput() {
        return new DeleteNoteUseCaseImpl.DeleteNoteInputImpl();
    }

    @Override
    public void execute(DeleteNoteInput input, CqrsCommandOutput output) {
        Figure note = figureRepository.findById(input.getNoteId()).orElse(null);

        if(null == note)
        {
            output.setId(input.getNoteId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Delete note failed: note not found, note id = " + input.getNoteId());
            return;
        }

        note.markAsRemoved();

        figureRepository.deleteById(note.getId());
        domainEventBus.postAll(note);

        output.setId(note.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class DeleteNoteInputImpl implements DeleteNoteInput {
        private String noteId;
        private String boardId;
        private Point position;
        private String color;

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
        public String getColor() {
            return color;
        }

        @Override
        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String getNoteId() {
            return noteId;
        }

        @Override
        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }
    }
}
