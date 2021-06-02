package ntut.csie.team5.usecase.figure.line;

import ntut.csie.team5.entity.model.figure.line.Line;
import ntut.csie.team5.usecase.figure.FigureDto;

public class ConvertLineToDto {

    public static FigureDto transform(Line line) {
        LineDto lineDto = new LineDto();
        lineDto.setFigureId(line.getId());
        lineDto.setBoardId(line.getBoardId());
        lineDto.setEndpointA(ConvertEndpointToDto.transform(line.getEndpointA()));
        lineDto.setEndpointB(ConvertEndpointToDto.transform(line.getEndpointB()));
        lineDto.setFigureType(line.getFigureType().toString());
        return lineDto;
    }
}
