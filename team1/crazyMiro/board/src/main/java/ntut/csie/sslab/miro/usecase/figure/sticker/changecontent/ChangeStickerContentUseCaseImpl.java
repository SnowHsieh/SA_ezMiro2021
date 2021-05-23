package ntut.csie.sslab.miro.usecase.figure.sticker.changecontent;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.figure.Sticker;
import ntut.csie.sslab.miro.usecase.figure.FigureRepository;

public class ChangeStickerContentUseCaseImpl implements ChangeStickerContentUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public ChangeStickerContentUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(ChangeStickerContentInput input, CqrsCommandOutput output) {
        try{
            Sticker sticker = (Sticker)figureRepository.findById(input.getFigureId()).get();
            if(sticker.getContent().equals(input.getContent()))
                return;

            sticker.changeContent(input.getContent());
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
    public ChangeStickerContentInput newInput() {
        return new ChangeStickerContentInputImpl();
    }

    private class ChangeStickerContentInputImpl implements ChangeStickerContentInput {
        private String figureId;
        private String content;

        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        public String getContent() {
            return content;
        }

        @Override
        public void setContent(String content) {
            this.content = content;
        }
    }
}
