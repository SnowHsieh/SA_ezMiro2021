package ntut.csie.sslab.miro.usecase.eventhandler;

import ntut.csie.sslab.ddd.adapter.presenter.cqrs.CqrsCommandPresenter;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.miro.entity.model.board.event.BoardEvents;
import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import ntut.csie.sslab.miro.entity.model.figure.Coordinate;
import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorInput;
import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorUseCase;
import ntut.csie.sslab.miro.usecase.cursor.create.CreateCursorUseCaseImpl;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import ntut.csie.sslab.miro.usecase.cursor.remove.RemoveCursorInput;
import ntut.csie.sslab.miro.usecase.cursor.remove.RemoveCursorUseCase;
import ntut.csie.sslab.miro.usecase.cursor.remove.RemoveCursorUseCaseImpl;

public class NotifyCursor {
    private CursorRepository cursorRepository;
    private DomainEventBus domainEventBus;

    public NotifyCursor(CursorRepository cursorRepository, DomainEventBus domainEventBus) {
        this.cursorRepository = cursorRepository;
        this.domainEventBus = domainEventBus;
    }

    public void whenBoardEntered(BoardEvents.BoardEntered boardEntered) {
        CreateCursorUseCase createCursorUseCase = new CreateCursorUseCaseImpl(cursorRepository, domainEventBus);
        CreateCursorInput input = createCursorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setBoardId(boardEntered.getBoardId());
        input.setUserId(boardEntered.getUserId());
        input.setCoordinate(new Coordinate(0, 0));

        createCursorUseCase.execute(input, output);
    }

    public void whenBoardLeft(BoardEvents.BoardLeft boardLeft) {
        Cursor cursor = cursorRepository.findByUserId(boardLeft.getUserId()).get();
        RemoveCursorUseCase removeCursorUseCase = new RemoveCursorUseCaseImpl(cursorRepository, domainEventBus);
        RemoveCursorInput input = removeCursorUseCase.newInput();
        CqrsCommandPresenter output = CqrsCommandPresenter.newInstance();
        input.setCursorId(cursor.getId());

        removeCursorUseCase.execute(input, output);
    }
}