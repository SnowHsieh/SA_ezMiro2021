package ntut.csie.sslab.miro.adapter.controller.rest.springboot.cursor.get;

import ntut.csie.sslab.miro.entity.model.cursor.Cursor;
import ntut.csie.sslab.miro.usecase.cursor.ConvertCursorToDto;
import ntut.csie.sslab.miro.usecase.cursor.CursorDto;
import ntut.csie.sslab.miro.usecase.cursor.CursorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
