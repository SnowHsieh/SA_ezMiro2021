package ntut.csie.islab.miro.adapter.controller.rest.springboot.webSocket;


import ntut.csie.islab.miro.entity.model.textFigure.Position;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.util.Map;
@CrossOrigin
@ServerEndpoint(value = "/websocket/")

@Component
public class BoardSessionWebSocketAdapter {
    private String joinedUserId;
    private Map<String, Position> userCursorMap;


    @OnMessage
    public void onMessage(String message, Session session)  {
        String event = "";
        JSONObject info;
        try {
            JSONObject jsonObject = new JSONObject(message);
            jsonObject = jsonObject.getJSONObject("message");
            event = jsonObject.getString("event");
            info = jsonObject.getJSONObject("info");

            System.out.println(event);
           // websocketEventHandler(event, info);
        }catch (JSONException err){
            System.out.println(err);
        }

    }

    private void websocketEventHandler(String event, JSONObject info) {
        if(event == "mouse-moved"){
            handleCursorMoved(info);
        }
    }



    @OnOpen
    public void onOpen(Session session) throws IOException {
        joinedUserId = "user1";
        System.out.println("user1 in the socket");
        RemoteEndpoint.Async async = session.getAsyncRemote();
        async.sendText(joinedUserId);
    }

    @OnClose
    public void onClose(Session session) {

    }

    private void handleCursorMoved(JSONObject info) {
        Position newPosition = null;

        try {
            Long x = info.getJSONObject("position").getLong("x");
            Long y = info.getJSONObject("position").getLong("y");
            newPosition = new Position(x, y);
            userCursorMap.put(joinedUserId,newPosition);

        }catch (JSONException err){
            System.out.println(err);
            return;
        }

    }
}
