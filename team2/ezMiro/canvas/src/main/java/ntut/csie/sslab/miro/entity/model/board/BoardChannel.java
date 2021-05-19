package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.Entity;
import java.util.ArrayList;
import java.util.List;

public class BoardChannel extends Entity {
    private String channel;
    private List<String> onlineUsers;

    public BoardChannel(String channelId, String channel) {
        super(channelId);
        this.channel = channel;
        this.onlineUsers = new ArrayList<>();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void addOnlineUser(String onlineUser) {
        onlineUsers.add(onlineUser);
    }

    public void removeOnlineUser(String onlineUser) {
        onlineUsers.remove(onlineUser);
    }
}