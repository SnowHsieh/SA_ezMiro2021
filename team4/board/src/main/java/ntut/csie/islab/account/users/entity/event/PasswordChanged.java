package ntut.csie.islab.account.users.entity.event;

import ntut.csie.sslab.ddd.model.DomainEvent;
import ntut.csie.sslab.ddd.model.common.DateProvider;

public class PasswordChanged extends DomainEvent {
    private final String oldPassword;
    private final String newPassword;
    public PasswordChanged(String id, String username, String oldPassword, String newPassword) {
        super(DateProvider.now());
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
