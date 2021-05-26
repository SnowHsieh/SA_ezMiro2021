package ntut.csie.selab.adapter.gateway.repository.springboot.board;

import ntut.csie.selab.entity.model.board.Cursor;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class CursorDataMapper {
    public static CursorData domainToData(Cursor cursor) {
        return new CursorData(cursor.getUserId(), cursor.getPoint().x, cursor.getPoint().y);
    }

    public static Set<CursorData> domainToData(Set<Cursor> cursors) {
        Set<CursorData> cursorDatas = new HashSet<>();
        cursors.forEach(cursor -> cursorDatas.add(domainToData(cursor)));
        return cursorDatas;
    }

    public static Cursor dataToDomain(CursorData selectedCursor) {
        return new Cursor(selectedCursor.getBoard().getBoardId(), selectedCursor.getUserId(), new Point(selectedCursor.getPointX(), selectedCursor.getPointY()));
    }

    public static Set<Cursor> dataToDomain(Set<CursorData> cursorDatas) {
        Set<Cursor> cursors = new HashSet<>();
        cursorDatas.forEach(cursorData -> cursors.add(dataToDomain(cursorData)));
        return cursors;
    }

}
