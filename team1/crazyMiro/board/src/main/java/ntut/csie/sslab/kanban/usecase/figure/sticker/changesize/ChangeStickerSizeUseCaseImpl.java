package ntut.csie.sslab.kanban.usecase.figure.sticker.changesize;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.figure.Sticker;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;

public class ChangeStickerSizeUseCaseImpl implements ChangeStickerSizeUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public ChangeStickerSizeUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeStickerSizeInput input, CqrsCommandOutput output) {
        try{
            Sticker sticker = (Sticker)figureRepository.findById(input.getFigureId()).get();
            if(sticker.getWidth() == input.getWidth() && sticker.getLength() == input.getLength())
                return;

            sticker.changeSize(input.getWidth(), input.getLength());
            figureRepository.save(sticker);
            domainEventBus.postAll(sticker);
            output.setId(input.getFigureId());
            output.setExitCode(ExitCode.SUCCESS);
        } catch (Exception e){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }
    }

    @Override
    public ChangeStickerSizeInput newInput() {
        return new ChangeStickerSizeInputImpl();
    }

    private class ChangeStickerSizeInputImpl implements ChangeStickerSizeInput {
        private String figureId;
        private int width;
        private int length;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public int getWidth() {
            return width;
        }

        @Override
        public void setWidth(int size) {
            this.width = size;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }
}
