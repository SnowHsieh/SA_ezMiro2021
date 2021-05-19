package ntut.csie.team5.usecase.eventhandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.Subscribe;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.team5.adapter.presenter.board.getcontent.BoardContentViewModel;
import ntut.csie.team5.adapter.presenter.board.getcontent.GetBoardContentPresenter;
import ntut.csie.team5.adapter.websocket.BoardSessionBroadcaster;
import ntut.csie.team5.entity.model.figure.Figure;
import ntut.csie.team5.entity.model.figure.event.FigureCreated;
import ntut.csie.team5.entity.model.figure.event.FigureDeleted;
import ntut.csie.team5.entity.model.figure.note.event.NoteColorChanged;
import ntut.csie.team5.entity.model.figure.note.event.NoteMoved;
import ntut.csie.team5.entity.model.figure.note.event.NoteResized;
import ntut.csie.team5.entity.model.figure.note.event.NoteTextEdited;
import ntut.csie.team5.entity.model.websocket.MessageType;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentInput;
import ntut.csie.team5.usecase.board.getcontent.GetBoardContentUseCase;
import ntut.csie.team5.usecase.figure.FigureDto;
import ntut.csie.team5.usecase.websocket.WebSocketBroadcaster;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class NotifyBoardSessionBroadcaster {

    private final DomainEventBus domainEventBus;
    private final GetBoardContentUseCase getBoardContentUseCase;
    private final WebSocketBroadcaster webSocketBroadcaster;

    public NotifyBoardSessionBroadcaster(DomainEventBus domainEventBus, GetBoardContentUseCase getBoardContentUseCase,
                                         WebSocketBroadcaster webSocketBroadcaster) {
        this.domainEventBus = domainEventBus;
        this.getBoardContentUseCase = getBoardContentUseCase;
        this.webSocketBroadcaster = webSocketBroadcaster;
    }

    @Subscribe
    public void whenFigureCreated(FigureCreated figureCreated) {
        sendMessage(figureCreated.boardId());
    }

    @Subscribe
    public void whenFigureDeleted(FigureDeleted figureDeleted) {
        sendMessage(figureDeleted.boardId());
    }

    @Subscribe
    public void whenNoteMoved(NoteMoved noteMoved) {
        sendMessage(noteMoved.boardId());
    }

    @Subscribe
    public void whenNoteColorChanged(NoteColorChanged noteColorChanged) {
        sendMessage(noteColorChanged.boardId());
    }

    @Subscribe
    public void whenNoteResized(NoteResized noteResized) {
        sendMessage(noteResized.boardId());
    }

    @Subscribe
    public void whenNoteTextEdited(NoteTextEdited noteTextEdited) {
        sendMessage(noteTextEdited.boardId());
    }

    private BoardContentViewModel getBoardContent(String boardId) {
        GetBoardContentInput getBoardContentInput = (GetBoardContentInput) getBoardContentUseCase;
        GetBoardContentPresenter getBoardContentOutput = new GetBoardContentPresenter();

        getBoardContentInput.setBoardId(boardId);

        getBoardContentUseCase.execute(getBoardContentInput, getBoardContentOutput);

        return getBoardContentOutput.buildViewModel();
    }

    private JSONObject createMessageJSON(List<FigureDto> figureDtos) throws JSONException, JsonProcessingException {
        JSONObject messageJSON = new JSONObject();
        messageJSON.put("type", MessageType.BOARD_CONTENT);
        JSONArray figureDtosJSONArray = new JSONArray();
        ObjectMapper objectMapper = new ObjectMapper();
        for (FigureDto figureDto: figureDtos) {
            figureDtosJSONArray.put(new JSONObject(objectMapper.writeValueAsString(figureDto)));
        }
        messageJSON.put("figures", figureDtosJSONArray);
        return messageJSON;
    }

    private void sendMessage(String boardId) {
        try {
            BoardContentViewModel boardContentViewModel = getBoardContent(boardId);
            JSONObject messageJSON = createMessageJSON(boardContentViewModel.getFigureDtos());
            webSocketBroadcaster.sendMessageToAllUser(boardId, messageJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
