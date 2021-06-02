package ntut.csie.team5.usecase.figure.line.move_endpoint;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.figure.line.LineRepository;

public class MoveLineEndpointUseCaseImpl implements MoveLineEndpointUseCase {

    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public MoveLineEndpointUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public MoveLineEndpointInput newInput() {
        return new MoveLineEndpointInputImpl();
    }

    @Override
    public void execute(MoveLineEndpointInput input, CqrsCommandOutput output) {
        Line line = lineRepository.findById(input.getFigureId()).orElse(null);

        if(null == line)
        {
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.FAILURE)
                    .setMessage("Move line endpoint failed: line not found, line id = " + input.getFigureId());
            return;
        }

        line.moveEndpoint(input.getEndpointId(), input.getPositionX(), input.getPositionY());
        lineRepository.save(line);
        domainEventBus.postAll(line);

        output.setId(line.getId());
        output.setExitCode(ExitCode.SUCCESS);
    }

    private class MoveLineEndpointInputImpl implements MoveLineEndpointInput {

        private String figureId;
        private String endpointId;
        private int positionX;
        private int positionY;

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

        @Override
        public int getPositionX() {
            return positionX;
        }

        @Override
        public void setPositionX(int positionX) {
            this.positionX = positionX;
        }

        @Override
        public int getPositionY() {
            return positionY;
        }

        @Override
        public void setPositionY(int positionY) {
            this.positionY = positionY;
        }
    }
}
