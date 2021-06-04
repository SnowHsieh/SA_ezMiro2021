package ntut.csie.sslab.miro.usecase.note.edit.size;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.Note;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class ChangeNoteSizeUseCaseImpl implements ChangeNoteSizeUseCase {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public ChangeNoteSizeUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeNoteSizeInput input, CqrsCommandOutput output) {
        Note note = figureRepository.findNoteById(input.getNoteId()).orElse(null);
        if (note == null){
           output.setId(input.getNoteId())
                 .setMessage("Change note size failed: note not found, note id = " + input.getNoteId());
           return;
        }

        note.changeSize(input.getHeight(), input.getWidth());

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public ChangeNoteSizeInput newInput() {
        return new ChangeNoteSizeInputImpl();
    }

    private class ChangeNoteSizeInputImpl implements ChangeNoteSizeInput {
        private String noteId;
        private double height;
        private double width;

        @Override
        public String getNoteId() {
            return noteId;
        }

        @Override
        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }

        @Override
        public double getHeight() {
            return height;
        }

        @Override
        public void setHeight(double height) {
            this.height = height;
        }

        @Override
        public double getWidth() {
            return width;
        }

        @Override
        public void setWidth(double width) {
            this.width = width;
        }
    }
}