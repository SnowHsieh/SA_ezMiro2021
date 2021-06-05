package ntut.csie.islab.miro.usecase.figure.line.unattachtextfigure;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class UnattachTextfigureUseCase {
    private DomainEventBus domainEventBus;
    private LineRepository lineRepository;

    public UnattachTextfigureUseCase(DomainEventBus domainEventBus, LineRepository lineRepository) {
        this.domainEventBus = domainEventBus;
        this.lineRepository = lineRepository;
    }

    public UnattachTextfigureInput newInput() {
        return new UnattachTextfigureInput();
    }

    public void execute(UnattachTextfigureInput input, CqrsCommandPresenter output) {
        Figure line = lineRepository.findById(input.getFigureId()).orElse(null);

        if (null == line) {
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Attach line to figure failed: line not found, line id = " + input.getFigureId());
            return;
        }
        line.unattachTextFigure(input.getAttachEndPointKind());

        lineRepository.save((Line) line);
        domainEventBus.postAll(line);
        output.setId(line.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("UnattachTextfigure success");
    }
}
