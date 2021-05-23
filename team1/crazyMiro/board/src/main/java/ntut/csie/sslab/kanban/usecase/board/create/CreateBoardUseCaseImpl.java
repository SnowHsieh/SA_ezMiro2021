package ntut.csie.sslab.kanban.usecase.board.create;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import ntut.csie.sslab.kanban.entity.model.board.Board;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;

public class CreateBoardUseCaseImpl implements CreateBoardUseCase{

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public CreateBoardUseCaseImpl(BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateBoardInput input, CqrsCommandOutput output) {
        try{
            if(boardRepository.findById(input.getBoardId()).isPresent())
                return;
            Board board = new Board(input.getBoardId(), input.getBoardName());

            boardRepository.save(board);
            domainEventBus.postAll(board);

            output.setId(board.getId())
                    .setExitCode(ExitCode.SUCCESS);
        }catch (Exception e){
            output.setExitCode(ExitCode.FAILURE)
                    .setMessage(e.getMessage());
        }
    }

    @Override
    public CreateBoardInput newInput() {
        return new CreateBoardInputImpl();
    }

    private static class CreateBoardInputImpl implements CreateBoardInput {
        private String boardName;
        private String boardId;

        @Override
        public String getBoardName() {
            return boardName;
        }

        @Override
        public void setBoardName(String boardName) {
            this.boardName = boardName;
        }

        @Override
        public String getBoardId() {
            return boardId;
        }

        @Override
        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }
    }
}
