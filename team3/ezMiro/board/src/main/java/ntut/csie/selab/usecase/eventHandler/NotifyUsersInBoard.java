package ntut.csie.selab.usecase.eventHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import ntut.csie.selab.entity.model.board.Board;
import ntut.csie.selab.entity.model.board.Cursor;
import ntut.csie.selab.entity.model.board.event.BoardCursorMoved;
import ntut.csie.selab.entity.model.board.event.BoardEntered;
import ntut.csie.selab.entity.model.board.event.BoardLeft;
import ntut.csie.selab.entity.model.board.event.WidgetCreationNotifiedToAllUser;
import ntut.csie.selab.entity.model.widget.StickyNote;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.entity.model.widget.WidgetType;
import ntut.csie.selab.entity.model.widget.event.*;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.board.CommittedWidgetDto;
import ntut.csie.selab.usecase.board.CommittedWidgetDtoMapper;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

public class NotifyUsersInBoard {

    private BoardRepository boardRepository;
    private StickyNoteRepository stickyNoteRepository;
    private LineRepository lineRepository;
    private DomainEventBus domainEventBus;
    private WebSocket webSocket;

    public NotifyUsersInBoard(BoardRepository boardRepository, StickyNoteRepository stickyNoteRepository, DomainEventBus domainEventBus, WebSocket webSocket) {
        this.boardRepository = boardRepository;
        this.stickyNoteRepository = stickyNoteRepository;
        this.domainEventBus = domainEventBus;
        this.webSocket = webSocket;
    }

    public NotifyUsersInBoard(BoardRepository boardRepository, StickyNoteRepository stickyNoteRepository, LineRepository lineRepository, DomainEventBus domainEventBus, WebSocket webSocket) {
        this.boardRepository = boardRepository;
        this.stickyNoteRepository = stickyNoteRepository;
        this.lineRepository = lineRepository;
        this.domainEventBus = domainEventBus;
        this.webSocket = webSocket;
    }

    @Subscribe
    public void notifyWidgetCreationToAllUser(WidgetCreated widgetCreated) {
        Optional<Widget> widget;
        if (widgetCreated.getType().equals(WidgetType.STICKY_NOTE.getType())) {
            widget = stickyNoteRepository.findById(widgetCreated.getWidgetId());
        } else {
            widget = lineRepository.findById(widgetCreated.getWidgetId());
        }

        if (widget.isPresent()) {
            Widget selectedWidget = widget.get();

            JSONObject message = new JSONObject();
            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray widgetsInfo = new JSONArray();

            if (widgetCreated.getType().equals(WidgetType.STICKY_NOTE.getType())) {
                StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
                StickyNoteDto stickyNoteDto = stickyNoteDtoMapper.domainToDto(selectedWidget);
                try {
                    widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(stickyNoteDto)));
                    message.put("widgets", widgetsInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                LineDtoMapper lineDtoMapper = new LineDtoMapper();
                LineDto lineDto = lineDtoMapper.domainToDto(selectedWidget);
                try {
                    widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(lineDto)));
                    message.put("widgets", widgetsInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            domainEventBus.post(new WidgetCreationNotifiedToAllUser(new Date(), widgetCreated.getBoardId(), widgetCreated.getWidgetId()));
            webSocket.sendMessageForAllUsersIn(widgetCreated.getBoardId(), message);
        } else {
            throw new RuntimeException("Widget not found, widget id = " + widgetCreated.getWidgetId());
        }
    }

    @Subscribe
    public void notifyWidgetDeletionToAllUser(WidgetDeleted widgetDeleted) {
        Optional<Widget> widget;
        if (widgetDeleted.getType().equals(WidgetType.STICKY_NOTE.getType())) {
            widget = stickyNoteRepository.findById(widgetDeleted.getWidgetId());
        } else {
            widget = lineRepository.findById(widgetDeleted.getWidgetId());
        }

        if (widget.isPresent()) {
            throw new RuntimeException("Widget not deleted, widget id = " + widgetDeleted.getWidgetId());
        }

        WidgetDeletedDtoMapper widgetDeletedDtoMapper = new WidgetDeletedDtoMapper();
        WidgetDeletedDto widgetDeletedDto = widgetDeletedDtoMapper.domainToDto(widgetDeleted.getWidgetId());

        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject message = new JSONObject();
        JSONArray widgetsInfo = new JSONArray();

        try {
            widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(widgetDeletedDto)));
            message.put("domainEvent", "widgetDeletionNotifiedToAllUser");
            message.put("widgets", widgetsInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        domainEventBus.post(new WidgetDeletionNotifiedToAllUser(new Date()));
        webSocket.sendMessageForAllUsersIn(widgetDeleted.getBoardId(), message);
    }

    @Subscribe
    public void notifyWidgetMovementToAllUser(WidgetMoved widgetMoved) {
        Optional<Widget> widget;
        if (widgetMoved.getType().equals(WidgetType.STICKY_NOTE.getType())) {
            widget = stickyNoteRepository.findById(widgetMoved.getWidgetId());

        } else {
            widget = lineRepository.findById(widgetMoved.getWidgetId());
        }

        if (!widget.isPresent()) {
            throw new RuntimeException("Widget not found, widget id = " + widgetMoved.getWidgetId());
        }


        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject message = new JSONObject();
        JSONArray widgetsInfo = new JSONArray();

        if (widgetMoved.getType().equals(WidgetType.STICKY_NOTE.getType())) {
            try {
                StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
                StickyNoteDto stickyNoteDto = stickyNoteDtoMapper.domainToDto(widget.get());
                widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(stickyNoteDto)));
                message.put("widgets", widgetsInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                LineDtoMapper lineDtoMapper = new LineDtoMapper();
                LineDto lineDto = lineDtoMapper.domainToDto(widget.get());
                widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(lineDto)));
                message.put("widgets", widgetsInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        domainEventBus.post(new WidgetMovementNotifiedToAllUser(new Date()));
        webSocket.sendMessageForAllUsersIn(widgetMoved.getBoardId(), message);
    }

    @Subscribe
    public void notifyWidgetZOrderRearrangedToAllUser(WidgetZOrderChanged widgetZOrderChanged) {
        Optional<Board> board = boardRepository.findById(widgetZOrderChanged.getBoardId());
        if (board.isPresent()) {
            Board selectedBoard = board.get();
            CommittedWidgetDtoMapper mapper = new CommittedWidgetDtoMapper();
//            List<CommittedWidgetDto> committedWidgetDtos = mapper.domainToDto((selectedBoard.getCommittedWidgets()));
            CommittedWidgetDto committedWidgetDto = mapper.domainToDto(selectedBoard.getCommittedWidgetBy(widgetZOrderChanged.getWidgetId()).get());

            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject message = new JSONObject();
            JSONArray widgetsInfo = new JSONArray();

            try {
                widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(committedWidgetDto)));
                message.put("domainEvent", "notifyWidgetZOrderRearrangedToAllUser");
                message.put("widgets", widgetsInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            domainEventBus.post(new WidgetResizeNotifiedToAllUser(new Date()));
            webSocket.sendMessageForAllUsersIn(widgetZOrderChanged.getBoardId(), message);
        }
    }

    @Subscribe
    public void notifyWidgetResizedToAllUser(WidgetResized widgetResized) {
        Optional<Widget> widget = stickyNoteRepository.findById(widgetResized.getWidgetId());

        if (!widget.isPresent()) {
            throw new RuntimeException("Widget not found, widget id = " + widgetResized.getWidgetId());
        }

        StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
        StickyNoteDto stickyNoteDto = stickyNoteDtoMapper.domainToDto((StickyNote) widget.get());

        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject message = new JSONObject();
        JSONArray widgetsInfo = new JSONArray();

        try {
            widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(stickyNoteDto)));
            message.put("domainEvent", "notifyWidgetResizedToAllUser");
            message.put("widgets", widgetsInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        domainEventBus.post(new WidgetResizeNotifiedToAllUser(new Date()));
        webSocket.sendMessageForAllUsersIn(widgetResized.getBoardId(), message);
    }

    @Subscribe
    public void notifyTextOfWidgetModifiedToAllUser(TextOfWidgetEdited textOfWidgetEdited) {
        Optional<Widget> widget = stickyNoteRepository.findById(textOfWidgetEdited.getWidgetId());

        if (widget.isPresent()) {
            Widget selectedWidget = widget.get();
            StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
            StickyNoteDto stickyNoteDto = stickyNoteDtoMapper.domainToDto((StickyNote) selectedWidget);

            JSONObject message = new JSONObject();

            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray widgetsInfo = new JSONArray();

            try {
                widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(stickyNoteDto)));
                message.put("widgets", widgetsInfo);
                message.put("domainEvent", "notifyTextOfWidgetModifiedToAllUser");
            } catch (Exception e) {
                e.printStackTrace();
            }
            domainEventBus.post(new WidgetTextOfWidgetModifiedToAllUser(new Date()));
            webSocket.sendMessageForAllUsersIn(textOfWidgetEdited.getBoardId(), message);
        } else {
            throw new RuntimeException("Widget not found, widget id = " + textOfWidgetEdited.getWidgetId());
        }
    }

    @Subscribe
    public void whenBoardEntered(BoardEntered boardEntered) {
        String boardId = boardEntered.getBoardId();
        JSONObject message = new JSONObject();
        try {
            message.put("domainEvent", "boardEntered");
            message.put("cursor", convertCursorToMessage(boardEntered.getCursor()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        webSocket.sendMessageForAllUsersIn(boardId, message);
    }

    @Subscribe
    public void whenBoardCursorMoved(BoardCursorMoved boardCursorMoved) {
        Cursor cursor = boardCursorMoved.getCursor();
        JSONObject message = new JSONObject();
        try {
            message.put("domainEvent", "boardCursorMoved");
            message.put("cursor", convertCursorToMessage(cursor));
        } catch (Exception e) {
            e.printStackTrace();
        }
        webSocket.sendMessageForAllUsersIn(cursor.getBoardId(), message);
    }

    @Subscribe
    public void whenBoardLeft(BoardLeft boardLeft) {
        JSONObject message = new JSONObject();
        try {
            message.put("domainEvent", "boardLeft");
            JSONObject cursor = new JSONObject();
            cursor.put("userId", boardLeft.getUserId());
            message.put("cursor", cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        webSocket.sendMessageForAllUsersIn(boardLeft.getBoardId(), message);
    }

    @Subscribe
    public void notifyColorOfWidgetModifiedToAllUser(ColorOfWidgetChanged colorOfWidgetChanged) {
        Optional<Widget> widget = stickyNoteRepository.findById(colorOfWidgetChanged.getWidgetId());

        if (widget.isPresent()) {
            Widget selectedWidget = widget.get();
            StickyNoteDtoMapper stickyNoteDtoMapper = new StickyNoteDtoMapper();
            StickyNoteDto stickyNoteDto = stickyNoteDtoMapper.domainToDto((StickyNote) selectedWidget);

            JSONObject message = new JSONObject();

            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray widgetsInfo = new JSONArray();

            try {
                widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(stickyNoteDto)));
                message.put("widgets", widgetsInfo);
                message.put("domainEvent", "notifyColorOfWidgetModifiedToAllUser");
            } catch (Exception e) {
                e.printStackTrace();
            }
            webSocket.sendMessageForAllUsersIn(colorOfWidgetChanged.getBoardId(), message);
        } else {
            throw new RuntimeException("Widget not found, widget id = " + colorOfWidgetChanged.getWidgetId());
        }
    }

    private JSONObject convertCursorToMessage(Cursor cursor) {
        JSONObject parsedCursor = new JSONObject();
        try {
            parsedCursor.put("userId", cursor.getUserId());
            parsedCursor.put("x", cursor.getPoint().x);
            parsedCursor.put("y", cursor.getPoint().y);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return parsedCursor;
    }
}


