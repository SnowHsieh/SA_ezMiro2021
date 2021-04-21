package ntut.csie.sslab.account.users.command.entity.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class NicknameChanged extends DomainEvent {

    private final String userId;
    private final String username;
    private final String oldNickname;
    private final String newNickname;

    public NicknameChanged(String userId, String username, String oldNickname, String newNickname) {
        super(DateProvider.now());
        this.userId = userId;
        this.username = username;
        this.oldNickname = oldNickname;
        this.newNickname = newNickname;
    }

    public String userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String oldNickname() {
        return oldNickname;
    }

    public String newNickname() {
        return newNickname;
    }
}
