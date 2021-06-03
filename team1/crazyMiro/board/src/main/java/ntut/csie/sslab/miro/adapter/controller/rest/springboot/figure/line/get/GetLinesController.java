package ntut.csie.sslab.miro.adapter.controller.rest.springboot.figure.line.get;

import ntut.csie.sslab.miro.entity.model.figure.Line;
import ntut.csie.sslab.miro.usecase.board.BoardRepository;
import ntut.csie.sslab.miro.usecase.figure.line.ConvertLineToDto;
import ntut.csie.sslab.miro.usecase.figure.line.LineDto;
import ntut.csie.sslab.miro.usecase.figure.line.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class GetLinesController {
    private LineRepository lineRepository;
    private BoardRepository boardRepository;

    @Autowired
    public void setLineQueryRepository(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @GetMapping(path = "${MIRO_PREFIX}/board/line/getall", produces = "application/json")
    public List<LineDto> getLines (@RequestParam("boardId") String boardId) {

        List<Line> lines = lineRepository.getLineByBoardId(boardId);
        return ConvertLineToDto.transform(lines);
    }
}
