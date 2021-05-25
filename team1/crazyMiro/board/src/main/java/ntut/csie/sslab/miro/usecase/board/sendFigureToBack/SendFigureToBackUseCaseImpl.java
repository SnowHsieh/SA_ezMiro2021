package ntut.csie.sslab.miro.usecase.board.sendFigureToBack;

import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.miro.entity.model.board.Board;
import ntut.csie.sslab.miro.entity.model.board.CommittedFigure;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;

import java.util.List;

public class SendFigureToBackUseCaseImpl implements SendFigureToBackUseCase {
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public SendFigureToBackUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public SendFigureToBackInput newInput() {
        return new SendFigureToBackInputImpl();
    }

    @Override
    public void execute(SendFigureToBackInput input, CqrsCommandOutput output) {
//        try{
            Board board = boardRepository.findById(input.getBoardId()).get();
            List<CommittedFigure> figures = board.getCommittedFigures();
            if (figures.get(0).getFigureId().equals(input.getFigureId())) {
                return;
            }

            board.sendFigureToBack(input.getFigureId());

            boardRepository.save(board);
            domainEventBus.postAll(board);
            output.setId(input.getFigureId())
                    .setExitCode(ExitCode.SUCCESS);
//        }
//        catch (Exception e){
//            output.setMessage(e.getMessage())
//                    .setExitCode(ExitCode.FAILURE);
//        }
    }


    private class SendFigureToBackInputImpl implements SendFigureToBackInput {
        private String boardId;
        private String figureId;

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        @Override
        public String getFigureId() {
            return figureId;
        }

        @Override
        public void setFigureId(String figureId) {
            this.figureId = figureId;
        }
    }
}
