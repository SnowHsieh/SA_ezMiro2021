package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.line.Line;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineUseCase;
import ntut.csie.sslab.miro.usecase.line.delete.DeleteLineInput;

public class DeleteLineUseCaseImpl implements DeleteLineUseCase {
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;

    public DeleteLineUseCaseImpl(LineRepository lineRepository, DomainEventBus domainEventBus) {
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public DeleteLineInput newInput() {
        return new DeleteLineInputImpl();
    }

    @Override
    public void execute(DeleteLineInput input, CqrsCommandOutput output) {
        try{
            Line line = lineRepository.findById(input.getLineId()).get();
            line.deleteLine();

            lineRepository.deleteById(input.getLineId());
            domainEventBus.postAll(line);

            output.setId(input.getLineId())
                    .setExitCode(ExitCode.SUCCESS);
        }
        catch (Exception e){
            output.setExitCode(ExitCode.FAILURE)
            .setMessage(e.getMessage());
        }
    }

    private class DeleteLineInputImpl implements DeleteLineInput {
        private String lineId;


        @Override
        public String getLineId() {
            return lineId;
        }

        @Override
        public void setLineId(String lineId) {
            this.lineId = lineId;
        }
    }
}
