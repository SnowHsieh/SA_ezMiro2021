package ntut.csie.sslab.miro.entity.model.board;

import ntut.csie.sslab.ddd.model.ValueObject;

public class BoardChannel extends ValueObject {
    private final String channel;

    public BoardChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return channel;
    }
}