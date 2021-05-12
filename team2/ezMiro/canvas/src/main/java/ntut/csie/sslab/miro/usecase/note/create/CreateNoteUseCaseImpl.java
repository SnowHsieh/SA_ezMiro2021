package ntut.csie.sslab.miro.usecase.note.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.note.Coordinate;
import ntut.csie.sslab.miro.entity.model.note.NoteBuilder;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;

public class CreateNoteUseCaseImpl implements CreateNoteUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public CreateNoteUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateNoteInput input, CqrsCommandOutput output) {
        Figure note = NoteBuilder.newInstance()
                .boardId(input.getBoardId())
                .description("")
                .color("#6FB7B7")
                .coordinate(input.getCoordinate())
                .width(100)
                .height(100)
                .build();

        figureRepository.save(note);
        domainEventBus.postAll(note);

        output.setId(note.getId());
    }

    @Override
    public CreateNoteInput newInput() {
        return new CreateNoteInputImpl();
    }

    private static class CreateNoteInputImpl implements CreateNoteInput {
        private String boardId;
        private Coordinate coordinate;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public void setCoordinate(Coordinate coordinate) { this.coordinate = coordinate; }

        @Override
        public Coordinate getCoordinate() { return coordinate; }
    }
}
