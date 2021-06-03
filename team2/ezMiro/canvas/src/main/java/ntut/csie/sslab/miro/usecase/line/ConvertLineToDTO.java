package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.miro.entity.model.figure.line.Line;
import java.util.ArrayList;
import java.util.List;

public class ConvertLineToDTO {
    public static LineDTO transform(Line line) {
        LineDTO dto = new LineDTO();
        dto.setLineId(line.getId());
        dto.setStartConnectableFigureId(line.getStartConnectableFigureId());
        dto.setEndConnectableFigureId(line.getEndConnectableFigureId());
        dto.setStartOffset(line.getStartOffset());
        dto.setEndOffset(line.getEndOffset());
        dto.setStartArrowStyle(line.getStartArrowStyle());
        dto.setEndArrowStyle(line.getEndArrowStyle());
        return dto;
    }

    public static List<LineDTO> transform(List<Line> lines) {
        List<LineDTO> lineDTOs = new ArrayList<>();
        for(Line line : lines) {
            lineDTOs.add(transform(line));
        }
        return lineDTOs;
    }
}