package ntut.csie.sslab.account.users.command.entity.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class Unregistered extends DomainEvent {

    private final String managerUserId;
    private final String userId;

    public Unregistered(String managerUserId, String userId) {
        super(DateProvider.now());
        this.managerUserId = managerUserId;
        this.userId = userId;
    }

    public String managerUserId() {
        return managerUserId;
    }

    public String userId() {
        return userId;
    }

}
