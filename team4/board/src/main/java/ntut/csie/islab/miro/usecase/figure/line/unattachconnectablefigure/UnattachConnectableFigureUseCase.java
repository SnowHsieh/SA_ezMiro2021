package ntut.csie.islab.miro.usecase.figure.line.unattachconnectablefigure;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class UnattachConnectableFigureUseCase {
    private DomainEventBus domainEventBus;
    private LineRepository lineRepository;

    public UnattachConnectableFigureUseCase(DomainEventBus domainEventBus, LineRepository lineRepository) {
        this.domainEventBus = domainEventBus;
        this.lineRepository = lineRepository;
    }

    public UnattachConnectableFigureInput newInput() {
        return new UnattachConnectableFigureInput();
    }

    public void execute(UnattachConnectableFigureInput input, CqrsCommandPresenter output) {
        Figure line = lineRepository.findById(input.getFigureId()).orElse(null);

        if (null == line) {
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Attach line to figure failed: line not found, line id = " + input.getFigureId());
            return;
        }
        line.unattachConnectableFigure(input.getAttachEndPointKind());

        lineRepository.save((Line) line);
        domainEventBus.postAll(line);
        output.setId(line.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("UnattachConnectableFigure success");
    }
}
