package ntut.csie.islab.miro.usecase.figure.line.delete;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class DeleteLineUseCase {
    private DomainEventBus domainEventBus;
    private LineRepository lineRepository;

    public DeleteLineUseCase(DomainEventBus domainEventBus, LineRepository lineRepository) {
        this.domainEventBus = domainEventBus;
        this.lineRepository = lineRepository;
    }


    public DeleteLineInput newInput() {
        return new DeleteLineInput();
    }

    public void execute(DeleteLineInput input, CqrsCommandPresenter output) {
        Figure line = lineRepository.findById(input.getFigureId()).orElse(null);

        if (null == line) {
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Delete line failed: line not found, line id = " + input.getFigureId());
            return;
        }
        line.markAsRemoved(
                line.getBoardId(),
                line.getFigureId()
        );

        lineRepository.deleteById(line.getFigureId());
        domainEventBus.postAll(line);
        output.setId(line.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("Delete line success");
    }
}