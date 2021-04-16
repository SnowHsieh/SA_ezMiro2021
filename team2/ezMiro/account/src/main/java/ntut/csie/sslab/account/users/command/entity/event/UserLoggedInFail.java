package ntut.csie.sslab.account.users.command.entity.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

import java.util.Date;

public class UserLoggedInFail extends DomainEvent {

    private final String userId;
    private final String username;

    public UserLoggedInFail(String userId, String username) {
        super(DateProvider.now());
        this.userId = userId;
        this.username = username;
    }


    public String userId() {
        return userId;
    }

    public String username() {
        return username;
    }
}
