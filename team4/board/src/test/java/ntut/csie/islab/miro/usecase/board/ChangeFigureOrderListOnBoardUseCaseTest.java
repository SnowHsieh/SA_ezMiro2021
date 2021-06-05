package ntut.csie.islab.miro.usecase.board;

import ntut.csie.islab.miro.entity.model.board.Board;
import ntut.csie.islab.miro.entity.model.board.CommittedFigure;
import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.usecase.board.changefigureorder.ChangeFigureOrderListOnBoardInput;
import ntut.csie.islab.miro.usecase.board.changefigureorder.ChangeFigureOrderListOnBoardUseCase;
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

public class ChangeFigureOrderListOnBoardUseCaseTest extends AbstractSpringBootJpaTest {

    @BeforeEach
    @Override
    public void setUp(){
        super.setUp();
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
        board.commitFigure(figureId_0, FigureTypeEnum.STICKYNOTE);
        figureOrderList.add(figureId_1);
        board.commitFigure(figureId_1,FigureTypeEnum.STICKYNOTE);


        input.setBoardId(board.getBoardId());
        input.setCommittedFigureListOrder(figureOrderList);
        changeFigureOrderListOnBoardUseCase.execute(input, output);

        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());

        List<CommittedFigure> resultFigureOrderList = board.getCommittedFigures();
        assertEquals(2,resultFigureOrderList.size());
        assertEquals(figureId_0,resultFigureOrderList.get(0).getFigureId());
        assertEquals(figureId_1,resultFigureOrderList.get(1).getFigureId());

        // todo : test_modifyFigureUseCase not test board saved in repository

    }
}
