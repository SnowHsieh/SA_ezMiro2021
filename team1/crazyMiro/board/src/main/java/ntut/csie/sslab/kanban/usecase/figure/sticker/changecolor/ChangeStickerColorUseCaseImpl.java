package ntut.csie.sslab.kanban.usecase.figure.sticker.changecolor;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.figure.Sticker;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;

public class ChangeStickerColorUseCaseImpl implements ChangeStickerColorUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public ChangeStickerColorUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeStickerColorInput input, CqrsCommandOutput output) {
        try{
            Sticker sticker = (Sticker)figureRepository.findById(input.getFigureId()).get();
            if(sticker.getColor().equals(input.getColor()))
                return;

            sticker.changeColor(input.getColor());
            figureRepository.save(sticker);
            domainEventBus.postAll(sticker);
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.SUCCESS);
        } catch (Exception e){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }
    }

    @Override
    public ChangeStickerColorInput newInput() {
        return new ChangeStickerColorInputImpl();
    }

    private class ChangeStickerColorInputImpl implements ChangeStickerColorInput {
        private String figureId;
        private String color;

        public String getFigureId() {
            return figureId;
        }

        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}
