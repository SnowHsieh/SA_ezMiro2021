package ntut.csie.team5.usecase.figure.connectable_figure.note.edit_text;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.note.Note;
import ntut.csie.team5.usecase.figure.connectable_figure.note.FigureRepository;

public class EditNoteTextUseCaseImpl implements EditNoteTextUseCase {

    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public EditNoteTextUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public EditNoteTextInput newInput() {
        return new EditNoteTextInputImpl();
    }

    @Override
    public void execute(EditNoteTextInput input, CqrsCommandOutput output) {
        Figure figure = figureRepository.findById(input.getFigureId()).orElse(null);

        if(null == figure)
        {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Edit note text failed: note not found, note id = " + input.getFigureId());
            return;
        }

        Note note = (Note)figure;
        note.editText(input.getText());
        figureRepository.save(note);

        output.setId(figure.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class EditNoteTextInputImpl implements EditNoteTextInput {
        String figureId;
        String text;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public String getText() {
            return text;
        }

        @Override
        public void setText(String text) {
            this.text = text;
        }
    }
}
