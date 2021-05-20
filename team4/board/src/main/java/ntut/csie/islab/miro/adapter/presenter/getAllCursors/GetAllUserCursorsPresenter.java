package ntut.csie.islab.miro.adapter.presenter.getAllCursors;

import ntut.csie.islab.miro.entity.model.board.cursor.Cursor;
import ntut.csie.sslab.ddd.adapter.presenter.Presenter;
import ntut.csie.sslab.ddd.usecase.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GetAllUserCursorsPresenter extends Result implements Presenter<AllCursorsViewModel>, GetAllCursorsOutput {
    private UUID boardId;
    private List<Cursor> cursorDtos;

    public GetAllUserCursorsPresenter() {
        cursorDtos = new ArrayList<>();
    }



    @Override
    public UUID getBoardId() {
        return boardId;
    }
    @Override
    public GetAllCursorsOutput setBoardId(UUID boardId) {
        this.boardId = boardId;
        return this;
    }
    @Override
    public List<Cursor> getCursorDtos() {
        return cursorDtos;
    }
    @Override
    public GetAllCursorsOutput setCursorDtos(List<Cursor> cursorDtos) {
        this.cursorDtos = cursorDtos;
        return this;
    }

    @Override
    public AllCursorsViewModel buildViewModel() {
        AllCursorsViewModel allCursorsViewModel = new AllCursorsViewModel();
        allCursorsViewModel.setBoardId(boardId);
        allCursorsViewModel.setCursorDtos(cursorDtos);
        return allCursorsViewModel;
    }

}
