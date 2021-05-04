package ntut.csie.team5.usecase.figure.connectable_figure.note.resize;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;

public class ResizeNoteUseCaseImpl implements ResizeNoteUseCase {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public ResizeNoteUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public ResizeNoteInput newInput() {
        return new ResizeNoteInputImpl();
    }

    @Override
    public void execute(ResizeNoteInput input, CqrsCommandOutput output) {
        Figure figure = figureRepository.findById(input.getFigureId()).orElse(null);

        if(null == figure)
        {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Resize note failed: note not found, note id = " + input.getFigureId());
            return;
        }

        Note note = (Note)figure;
        note.changeSize(input.getHeight(), input.getWidth());
        figureRepository.save(note);

        output.setId(figure.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class ResizeNoteInputImpl implements ResizeNoteInput {

        private String figureId;
        private int height;
        private int width;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
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
    }
}
