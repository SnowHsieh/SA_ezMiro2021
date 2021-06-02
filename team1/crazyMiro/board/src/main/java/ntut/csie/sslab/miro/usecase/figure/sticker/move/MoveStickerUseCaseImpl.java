package ntut.csie.sslab.miro.usecase.figure.sticker.move;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.Coordinate;
import ntut.csie.sslab.miro.entity.model.figure.Sticker;
import ntut.csie.sslab.miro.usecase.figure.FigureRepository;

public class MoveStickerUseCaseImpl implements MoveStickerUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public MoveStickerUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }


    @Override
    public void execute(MoveStickerInput input, CqrsCommandOutput output) {
        try{
            Sticker sticker = (Sticker)figureRepository.findById(input.getFigureId()).get();
            if(sticker.getPosition().equals(input.getPosition()))
                return;


            sticker.move(input.getPosition(), input.getUserId());
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
    public MoveStickerInput newInput() {
        return new MoveStickerInputImpl();
    }

    private class MoveStickerInputImpl implements MoveStickerInput {
        private String figureId;
        private Coordinate position;
        private String userId;

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }

        @Override
        public Coordinate getPosition() {
            return position;
        }

        @Override
        public void setPosition(Coordinate position) {
            this.position = position;
        }

        @Override
        public void setUserId(String userId){this.userId = userId; }

        @Override
        public String getUserId() {
            return userId;
        }
    }
}