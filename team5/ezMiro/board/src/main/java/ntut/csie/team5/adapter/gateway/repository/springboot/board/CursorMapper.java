package ntut.csie.team5.adapter.gateway.repository.springboot.board;

import ntut.csie.team5.entity.model.board.Cursor;

import java.util.ArrayList;
import java.util.List;

public class CursorMapper {

    public static CursorData transformToData(Cursor cursor) {
        CursorData cursorData = new CursorData(
                cursor.userId(),
                cursor.positionX(),
                cursor.positionY()
        );
        return cursorData;
    }

    public static List<CursorData> transformToData(List<Cursor> cursors) {
        List<CursorData> cursorDatas = new ArrayList<>();
        cursors.forEach(cursor -> {
            cursorDatas.add(transformToData(cursor));
        });
        return cursorDatas;
    }
}
