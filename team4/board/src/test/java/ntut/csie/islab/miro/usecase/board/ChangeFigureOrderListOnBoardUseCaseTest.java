package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.adapter.gateway.eventbus.google.NotifyBoardAdapter;
import ntut.csie.islab.miro.adapter.repository.board.BoardRepository;
import ntut.csie.islab.miro.adapter.repository.textFigure.TextFigureRepository;
import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.CommittedFigure;
import ntut.csie.islab.miro.entity.model.textFigure.TextFigure;
import ntut.csie.islab.miro.usecase.eventHandler.NotifyBoard;
import ntut.csie.sslab.ddd.adapter.gateway.GoogleEventBus;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ChangeFigureOrderListOnBoardUseCaseTest {
    public DomainEventBus domainEventBus;
    public BoardRepository boardRepository;
    private Board board;

    @BeforeEach
    public void setUp(){
        domainEventBus = new GoogleEventBus();
        boardRepository = new BoardRepository();
        board = new Board(UUID.randomUUID(),"modifyFigureUseCaseBoard");
        boardRepository.save(board);
    }


    @Test
    public void test_modifyFigureUseCase(){

        assertEquals(0,board.getCommittedFigures().size());
        ChangeFigureOrderListOnBoardUseCase changeFigureOrderListOnBoardUseCase = new ChangeFigureOrderListOnBoardUseCase(boardRepository, domainEventBus);
        ChangeFigureOrderListOnBoardInput input = changeFigureOrderListOnBoardUseCase.newInput();

        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        List<UUID> figureOrderList = new ArrayList<>();
        UUID figureId_0 = UUID.randomUUID();
        UUID figureId_1 = UUID.randomUUID();

        figureOrderList.add(figureId_0);
        board.commitFigure(figureId_0);
        figureOrderList.add(figureId_1);
        board.commitFigure(figureId_1);


        input.setBoardId(board.getBoardId());
        input.setCommittedFigureListOrder(figureOrderList);
        changeFigureOrderListOnBoardUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());

        List<CommittedFigure> resultFigureOrderList = board.getCommittedFigures();
        assertEquals(2,resultFigureOrderList.size());
        assertEquals(figureId_0,resultFigureOrderList.get(0).getFigureId());
        assertEquals(figureId_1,resultFigureOrderList.get(1).getFigureId());


    }
}
