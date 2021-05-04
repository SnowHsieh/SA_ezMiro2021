package ntut.csie.sslab.kanban.usecase.figure.sticker.create;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.figure.Coordinate;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.entity.model.figure.Sticker;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;

import java.util.UUID;

public class CreateStickerUseCaseImpl implements CreateStickerUseCase {
    private FigureRepository figureRepository;
    private DomainEventBus domainEventBus;

    public CreateStickerUseCaseImpl(FigureRepository figureRepository, DomainEventBus domainEventBus) {
        this.figureRepository = figureRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateStickerInput input, CqrsCommandOutput output) {
        try{
            String stickerId = UUID.randomUUID().toString();
            int order = figureRepository.getStickersByBoardId(input.getBoardId()).size();
            Figure sticker = new Sticker(input.getBoardId(),
                    stickerId,
                    input.getContent(),
                    input.getSize(),
                    input.getColor(),
                    input.getPosition(),
                    order);

            figureRepository.save(sticker);
            domainEventBus.postAll(sticker);

            output.setId(sticker.getId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch (Exception e) {
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }

    }

    @Override
    public CreateStickerInput newInput() {
        return new CreateStickerInputImpl();
    }

    private class CreateStickerInputImpl implements CreateStickerInput {
        private String boardId;
        private String content;
        private int size;
        private String color;
        private Coordinate position;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardIdId) {
            this.boardId = boardIdId;
        }

        @Override
        public String getContent() {
            return content;
        }

        @Override
        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public int getSize() {
            return size;
        }

        @Override
        public void setSize(int size) {
            this.size = size;
        }

        @Override
        public String getColor() {
            return color;
        }

        @Override
        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public Coordinate getPosition() {
            return position;
        }

        @Override
        public void setPosition(Coordinate position) {
            this.position = position;
        }

    }
}
