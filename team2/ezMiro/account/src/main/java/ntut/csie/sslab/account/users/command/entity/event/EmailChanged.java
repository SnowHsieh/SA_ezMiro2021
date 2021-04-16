package ntut.csie.sslab.account.users.command.entity.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

import java.util.Date;

public class EmailChanged extends DomainEvent {

    private final String userId;
    private final String username;
    private final String oldEmail;
    private final String newEmail;

    public EmailChanged(String userId, String username, String oldEmail, String newEmail) {
        super(DateProvider.now());
        this.userId = userId;
        this.username = username;
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
    }

    public String userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String oldEmail() {
        return oldEmail;
    }

    public String newEmail() {
        return newEmail;
    }
}
