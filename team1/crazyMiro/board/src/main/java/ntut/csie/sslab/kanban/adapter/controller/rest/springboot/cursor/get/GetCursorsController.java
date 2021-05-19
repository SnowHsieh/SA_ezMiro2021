package ntut.csie.sslab.kanban.adapter.controller.rest.springboot.cursor.get;

import ntut.csie.sslab.kanban.entity.model.board.CommittedFigure;
import ntut.csie.sslab.kanban.entity.model.cursor.Cursor;
import ntut.csie.sslab.kanban.entity.model.figure.Figure;
import ntut.csie.sslab.kanban.usecase.board.BoardRepository;
import ntut.csie.sslab.kanban.usecase.cursor.ConvertCursorToDto;
import ntut.csie.sslab.kanban.usecase.cursor.CursorDto;
import ntut.csie.sslab.kanban.usecase.cursor.CursorRepository;
import ntut.csie.sslab.kanban.usecase.figure.ConvertStickerToDto;
import ntut.csie.sslab.kanban.usecase.figure.FigureDto;
import ntut.csie.sslab.kanban.usecase.figure.FigureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class GetCursorsController {
    private CursorRepository cursorRepository;

    @Autowired
    public void setCursorQueryRepository(CursorRepository cursorRepository) {
        this.cursorRepository = cursorRepository;
    }

    @GetMapping(path = "${MIRO_PREFIX}/board/cursor/getall", produces = "application/json")
    public List<CursorDto> getCursors (@RequestParam("boardId") String boardId) {

        List<Cursor> cursors = cursorRepository.getCursorsByBoardId(boardId);

        return ConvertCursorToDto.transform(cursors);
    }
}
