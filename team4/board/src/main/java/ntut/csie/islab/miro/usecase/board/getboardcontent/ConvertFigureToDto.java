package ntut.csie.islab.miro.usecase.board.getboardcontent;

import ntut.csie.islab.miro.entity.model.textfigure.TextFigure;
import ntut.csie.islab.miro.usecase.textfigure.TextFigureDto;

import java.util.ArrayList;
import java.util.List;

public class ConvertFigureToDto {

    public static TextFigureDto transform(TextFigure textFigure) {
        TextFigureDto dto = new TextFigureDto();

        dto.setFigureId(textFigure.getFigureId());
        dto.setBoardId(textFigure.getBoardId());
        dto.setContent(textFigure.getContent());
        dto.setPosition(textFigure.getPosition());
        dto.setStyle(textFigure.getStyle());
        return dto;
    }

    public static List<TextFigureDto> transform(List<TextFigure> textFigureList) {
        List<TextFigureDto> textFigureDtos = new ArrayList<>();
        for(TextFigure textFigure : textFigureList) {
            textFigureDtos.add(transform(textFigure));
        }

        return textFigureDtos;
    }
}
