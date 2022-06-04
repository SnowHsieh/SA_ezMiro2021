package ntut.csie.islab.account.users.entity.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class UserRegistered extends DomainEvent {

    private final String userId;
    private final String username;
    private final String password;
    private final String email;

    public UserRegistered(String userId, String username, String password, String email) {
        super(DateProvider.now());
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String password() {
        return password;
    }

    public String email() {
        return email;
    }
}
