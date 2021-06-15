package ntut.csie.team5.usecase.figure.line.disconnect;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.figure.line.LineRepository;

public class DisconnectLineUseCaseImpl implements DisconnectLineUseCase {


    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public DisconnectLineUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public DisconnectLineInput newInput() {
        return new DisconnectLineInputImpl();
    }

    @Override
    public void execute(DisconnectLineInput input, CqrsCommandOutput output) {
        Line line = lineRepository.findById(input.getFigureId()).orElse(null);

        if (null == line) {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Disconnect line failed: line not found, line id = " + input.getFigureId());
            return;
        }

        line.disconnectToFigure(input.getEndpointId());
        lineRepository.save(line);
        domainEventBus.postAll(line);

        output.setId(line.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class DisconnectLineInputImpl implements DisconnectLineInput {

        private String figureId;
        private String endpointId;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public String getEndpointId() {
            return endpointId;
        }

        @Override
        public void setEndpointId(String endpointId) {
            this.endpointId = endpointId;
        }
    }
}
