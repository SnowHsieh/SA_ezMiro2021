package ntut.csie.sslab.miro.usecase.line;

import ntut.csie.sslab.miro.entity.model.figure.Figure;
import ntut.csie.sslab.miro.entity.model.line.Line;
import ntut.csie.sslab.miro.usecase.figure.FigureDto;

import java.util.ArrayList;
import java.util.List;

public class ConvertLineToDto {
    public static List<LineDto> transform(List<Line> lines) {
        List<LineDto> lineDtos = new ArrayList<>();
        for (Line each : lines) {
            lineDtos.add(new LineDto(each.getLineId(),
                    each.getSourceId(),
                    each.getTargetId(),
                    each.getSourcePosition(),
                    each.getTargetPosition()));
        }
        return lineDtos;
    }
}
