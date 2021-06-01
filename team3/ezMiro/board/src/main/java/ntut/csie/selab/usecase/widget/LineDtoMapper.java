package ntut.csie.selab.usecase.widget;

import ntut.csie.selab.entity.model.widget.Line;

import java.util.ArrayList;
import java.util.List;

public class LineDtoMapper {
    public LineDto domainToDto(Line line) {
        return new LineDto(line.getId(),
                line.getCoordinate().getTopLeft().x,
                line.getCoordinate().getTopLeft().y,
                line.getCoordinate().getBottomRight().x,
                line.getCoordinate().getBottomRight().x,
                "Line");
    }

    public List<LineDto> domainToDto(List<Line> lines) {
        List<LineDto> lineDtos = new ArrayList<>();

        lines.forEach(line -> lineDtos.add(domainToDto(line)));

        return lineDtos;
    }
}
