package ntut.csie.islab.miro.usecase.figure.line.attachtextfigure;

import ntut.csie.islab.miro.entity.model.figure.Figure;
import ntut.csie.islab.miro.entity.model.figure.line.Line;
import ntut.csie.islab.miro.usecase.figure.line.LineRepository;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

public class AttachTextfigureUseCase {
    private DomainEventBus domainEventBus;
    private LineRepository lineRepository;

    public AttachTextfigureUseCase(DomainEventBus domainEventBus, LineRepository lineRepository) {
        this.domainEventBus = domainEventBus;
        this.lineRepository = lineRepository;
    }

    public AttachTextfigureInput newInput() {
        return new AttachTextfigureInput();
    }

    public void execute(AttachTextfigureInput input, CqrsCommandPresenter output) {
        Figure line = lineRepository.findById(input.getFigureId()).orElse(null);

        if (null == line) {
            output.setId(input.getFigureId().toString())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Delete line failed: line not found, line id = " + input.getFigureId());
            return;
        }
        line.attachTextFigure(input.getTextFigureId());
        System.out.println("getAttachedTextFigureIdList size:" + line.getAttachedTextFigureIdList().size());
        lineRepository.save((Line) line);
        domainEventBus.postAll(line);
        output.setId(line.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("AttachTextfigure success");
    }
}
