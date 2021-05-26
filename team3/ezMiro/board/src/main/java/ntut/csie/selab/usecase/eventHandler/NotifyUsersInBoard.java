package ntut.csie.selab.usecase.eventHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import ntut.csie.selab.entity.model.board.event.WidgetCreationCommitted;
import ntut.csie.selab.entity.model.board.event.WidgetDeletionCommitted;
import ntut.csie.selab.entity.model.widget.Widget;
import ntut.csie.selab.entity.model.widget.event.TextOfWidgetEdited;
import ntut.csie.selab.entity.model.widget.event.WidgetCreated;
import ntut.csie.selab.entity.model.widget.event.WidgetDeleted;
import ntut.csie.selab.model.DomainEventBus;
import ntut.csie.selab.usecase.board.BoardRepository;
import ntut.csie.selab.usecase.websocket.WebSocket;
import ntut.csie.selab.usecase.widget.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.Optional;

public class NotifyUsersInBoard {

    private BoardRepository boardRepository;
    private WidgetRepository widgetRepository;
    private DomainEventBus domainEventBus;
    private WebSocket webSocket;

    public NotifyUsersInBoard(BoardRepository boardRepository, WidgetRepository widgetRepository, DomainEventBus domainEventBus, WebSocket webSocket) {
        this.boardRepository = boardRepository;
        this.widgetRepository = widgetRepository;
        this.domainEventBus = domainEventBus;
        this.webSocket = webSocket;
    }

    @Subscribe
    public void whenWidgetCreated(WidgetCreated widgetCreated) {
        Optional<Widget> widget = widgetRepository.findById(widgetCreated.getWidgetId());

        if (widget.isPresent()) {
            Widget selectedWidget = widget.get();
            WidgetDtoMapper widgetDtoMapper = new WidgetDtoMapper();
            WidgetDto widgetDto = widgetDtoMapper.domainToDto(selectedWidget);

            JSONObject message = new JSONObject();

            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray widgetsInfo = new JSONArray();

            try {
                widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(widgetDto)));
                message.put("widgets", widgetsInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            domainEventBus.post(new WidgetCreationCommitted(new Date(), widgetCreated.getBoardId(), widgetCreated.getWidgetId()));
            webSocket.sendMessageForAllUsersIn(widgetCreated.getBoardId(), message);
        } else {
            throw new RuntimeException("Widget not found, widget id = " + widgetCreated.getWidgetId());
        }
    }

    @Subscribe
    public void whenWidgetDeleted(WidgetDeleted widgetDeleted) {
        Optional<Widget> widget = widgetRepository.findById(widgetDeleted.getWidgetId());

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

            message.put("domainEvent", "whenWidgetDeleted");
            message.put("widgets", widgetsInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        domainEventBus.post(new WidgetDeletionCommitted(new Date()));
        webSocket.sendMessageForAllUsersIn(widgetDeleted.getBoardId(), message);
    }

    @Subscribe
    public void whenTextOfWidgetEdited(TextOfWidgetEdited textOfWidgetEdited) {
        Optional<Widget> widget = widgetRepository.findById(textOfWidgetEdited.getWidgetId());

        if (widget.isPresent()) {
            Widget selectedWidget = widget.get();
            WidgetDtoMapper widgetDtoMapper = new WidgetDtoMapper();
            WidgetDto widgetDto = widgetDtoMapper.domainToDto(selectedWidget);

            JSONObject message = new JSONObject();

            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray widgetsInfo = new JSONArray();

            try {
                widgetsInfo.put(new JSONObject(objectMapper.writeValueAsString(widgetDto)));
                message.put("widgets", widgetsInfo);
                message.put("domainEvent", "whenTextOfWidgetEdited");
            } catch (Exception e) {
                e.printStackTrace();
            }
            webSocket.sendMessageForAllUsersIn(textOfWidgetEdited.getBoardId(), message);
        } else {
            throw new RuntimeException("Widget not found, widget id = " + textOfWidgetEdited.getWidgetId());
        }
    }
}


