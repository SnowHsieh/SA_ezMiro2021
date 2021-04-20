package ntut.csie.sslab.account.users.command.entity.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class PasswordChanged extends DomainEvent {

    private final String userId;
    private final String username;
    private final String oldPassword;
    private final String newPassword;

    public PasswordChanged(String userId, String username, String oldPassword, String newPassword) {
        super(DateProvider.now());
        this.userId = userId;
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String userId() {
        return userId;
    }

    public String username() {
        return username;
    }

    public String oldPassword() {
        return oldPassword;
    }

    public String newPassword() {
        return newPassword;
    }
}
