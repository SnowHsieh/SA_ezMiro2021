package ntut.csie.selab.usecase.widget.line.delete;

import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.widget.LineRepository;

import java.util.Optional;

public class DeleteLineUseCase {
    LineRepository lineRepository;
    DomainEventBus domainEventBus;

    public DeleteLineUseCase(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    public void execute(DeleteLineInput input, DeleteLineOutput output) {
        Optional<Widget> line = lineRepository.findById(input.getLineId());

        if (line.isPresent()) {
            Widget selectedLine = line.get();
            selectedLine.clearDomainEvents();
            selectedLine.delete();
            lineRepository.delete(selectedLine);
            domainEventBus.postAll(selectedLine);
            output.setBoardId(selectedLine.getBoardId());
            output.setLineId(selectedLine.getId());
        } else {
            throw new RuntimeException("line not found, line id = " + input.getLineId());
        }
    }
}
