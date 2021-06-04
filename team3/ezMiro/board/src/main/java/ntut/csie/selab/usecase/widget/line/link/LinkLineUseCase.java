package ntut.csie.selab.usecase.widget.line.link;

import ntut.csie.selab.entity.model.widget.Line;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.LineRepository;
import ntut.csie.selab.usecase.widget.StickyNoteRepository;

import java.util.Optional;

public class LinkLineUseCase {
    private LineRepository lineRepository;
    private StickyNoteRepository stickyNoteRepository;
    private DomainEventBus domainEventBus;

    public LinkLineUseCase(LineRepository lineRepository, StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(LinkLineInput input, LinkLineOutput output) {
        Optional<Widget> line = lineRepository.findById(input.getLineId());

        if (line.isPresent()) {
            Optional<Widget> stickyNote = stickyNoteRepository.findById(input.getTargetId());
            if(!stickyNote.isPresent()) {
                throw new RuntimeException("stickyNote not found, stickyNote id = " + input.getTargetId());
            }

            Line selectedLine = (Line) line.get();
            selectedLine.clearDomainEvents();
            selectedLine.link(input.getEndPoint(), stickyNote.get());
            lineRepository.save(selectedLine);
            domainEventBus.postAll(selectedLine);
            output.setLineId(selectedLine.getId());
            output.setBoardId(selectedLine.getBoardId());
            output.setEndPoint(input.getEndPoint());
            if(input.getEndPoint().equals("head")) {
                output.setTargetId(selectedLine.getHeadWidget().getId());
            } else {
                output.setTargetId(selectedLine.getTailWidget().getId());
            }
        } else {
            throw new RuntimeException("line not found, line id = " + input.getLineId());
        }
    }
}
