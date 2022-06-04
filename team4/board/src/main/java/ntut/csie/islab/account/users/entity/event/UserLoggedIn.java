package ntut.csie.islab.account.users.entity.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class UserLoggedIn extends DomainEvent {

    private final String userId;
    private final String username;

    public UserLoggedIn(String userId, String username) {
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
