package ntut.csie.sslab.miro.usecase.eventhandler;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.entity.model.figure.connectablefigure.note.event.NoteEvents;
import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import ntut.csie.sslab.miro.usecase.note.FigureRepository;
import java.util.List;
import java.util.stream.Collectors;

public class NotifyFigure {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public NotifyFigure(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    public void whenNoteDeleted(NoteEvents.NoteDeleted noteDeleted) {
        List<Line> lines = figureRepository.findAllLines();
        lines = lines.stream().filter(x -> x.getStartConnectableFigureId().equals(noteDeleted.getNoteId()) ||
                x.getEndConnectableFigureId().equals(noteDeleted.getNoteId())).collect(Collectors.toList());
        lines.forEach(line-> {
            line.markAsRemoved(noteDeleted.getBoardId());
            figureRepository.deleteById(line.getId());
            domainEventBus.postAll(line);
        });
    }
}