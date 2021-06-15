package ntut.csie.team5.usecase.figure.connectable_figure.note.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.connectable_figure.note.NoteRepository;

public class MoveNoteUseCaseImpl implements MoveNoteUseCase {

    private NoteRepository noteRepository;
    private DomainEventBus domainEventBus;

    public MoveNoteUseCaseImpl(NoteRepository noteRepository, DomainEventBus domainEventBus) {
        this.noteRepository = noteRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public MoveNoteInput newInput() {
        return new MoveNoteInputImpl();
    }

    @Override
    public void execute(MoveNoteInput input, CqrsCommandOutput output) {
        Note note = noteRepository.findById(input.getFigureId()).orElse(null);

        if(null == note)
        {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Move note failed: note not found, note id = " + input.getFigureId());
            return;
        }

        note.changePosition(input.getLeftTopPositionX(), input.getLeftTopPositionY());
        noteRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class MoveNoteInputImpl implements MoveNoteInput {

        private String figureId;
        private int leftTopPositionX;
        private int leftTopPositionY;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
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

        public void setLeftTopPositionY(int leftTopPositionY) {
            this.leftTopPositionY = leftTopPositionY;
        }
    }
}
