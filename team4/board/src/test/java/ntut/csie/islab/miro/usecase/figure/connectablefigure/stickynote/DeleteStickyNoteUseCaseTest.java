package ntut.csie.islab.miro.usecase.figure.connectablefigure.stickynote;

import ntut.csie.islab.miro.entity.model.board.CommittedFigure;
import ntut.csie.islab.miro.entity.model.board.FigureTypeEnum;
import ntut.csie.islab.miro.usecase.AbstractSpringBootJpaTest;
import ntut.csie.islab.miro.entity.model.Position;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.ShapeKindEnum;
import ntut.csie.islab.miro.entity.model.figure.connectablefigure.Style;
import ntut.csie.islab.miro.usecase.figure.connectablefigure.stickynote.delete.DeleteStickyNoteInput;
import ntut.csie.islab.miro.usecase.figure.connectablefigure.stickynote.delete.DeleteStickyNoteUseCase;
import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class DeleteStickyNoteUseCaseTest extends AbstractSpringBootJpaTest {

    private CqrsCommandPresenter preGeneratedStickyNote;
    private CqrsCommandPresenter preGeneratedStickyNoteAnother;


    @BeforeEach
    @Override
    public void setUp(){
        super.setUp();
        this.preGeneratedFactory();
        board = boardRepository.findById(board.getBoardId()).get();
        board.commitFigure(UUID.fromString(preGeneratedStickyNote.getId()), FigureTypeEnum.STICKYNOTE );
        board.commitFigure(UUID.fromString(preGeneratedStickyNoteAnother.getId()),FigureTypeEnum.STICKYNOTE);

        boardRepository.save(board);
        domainEventBus.register(notifyBoardAdapter);
    }



    private void preGeneratedFactory(){
        preGeneratedStickyNote = generateCreateStickyNoteUseCaseOutput(
                board.getBoardId(),
                new Position(1.0,1.0),
                "Content1",
                new Style(12, ShapeKindEnum.CIRCLE, 87.87,100, "#948700"));
        preGeneratedStickyNoteAnother = generateCreateStickyNoteUseCaseOutput(
                board.getBoardId(),
                new Position(1.0,1.0),
                "Content2",
                new Style(12, ShapeKindEnum.CIRCLE, 87.87,100, "#948700"));
    }

    @Test
    public void test_delete_sticky_note(){

        UUID preGeneratedStickyNoteId =  UUID.fromString(preGeneratedStickyNote.getId());
        assertNotNull(stickyNoteRepository.findById(preGeneratedStickyNoteId).get());
        //check stickynote created and committed to board.
        assertEquals(2,board.getCommittedFigures().size());
        for(CommittedFigure c : board.getCommittedFigures()){
            System.out.println("bef del:" + c.getFigureId());
        }
        System.out.println(boardRepository.findById(board.getBoardId()).get().getCommittedFigures().size());

        DeleteStickyNoteUseCase deleteStickyNoteUseCase = new DeleteStickyNoteUseCase(stickyNoteRepository, domainEventBus);
        DeleteStickyNoteInput input = deleteStickyNoteUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();

        input.setBoardId(boardId);
        input.setFigureId(preGeneratedStickyNoteId);
        assertNotNull(stickyNoteRepository.findById(input.getFigureId()).get());
        deleteStickyNoteUseCase.execute(input, output);
        assertNotNull(output.getId());
        assertEquals(ExitCode.SUCCESS,output.getExitCode());

        //get revised committedfigure on board which is in db
        board = boardRepository.findById(board.getBoardId()).get();
        assertEquals(1,board.getCommittedFigures().size());
    }

}