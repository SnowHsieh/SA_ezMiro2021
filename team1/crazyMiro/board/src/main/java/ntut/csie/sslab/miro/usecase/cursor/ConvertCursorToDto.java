package ntut.csie.sslab.miro.usecase.cursor;

import ntut.csie.sslab.miro.entity.model.cursor.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ConvertCursorToDto {

    public static List<CursorDto> transform(List<Cursor> cursors) {
        List<CursorDto> cursorDtos = new ArrayList<>();
        for(Cursor each: cursors) {
            cursorDtos.add(new CursorDto(each.getBoardId(),
                    each.getUserId(),
                    each.getCursorId(),
                    each.getPosition()));
        }
        return cursorDtos;
    }
}
