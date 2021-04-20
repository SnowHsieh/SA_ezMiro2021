package ntut.csie.sslab.account.users.command.entity.event;

import ntut.csie.sslab.ddd.model.common.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

public class ManagerUnregisteredUser extends DomainEvent {

    private final String managerUserId;
    private final String unregisterUserId;
    private final String name;
    private final String email;
    private final String nickname;
    private final String systemRole;

    public ManagerUnregisteredUser(String managerUserId, String unregisterUserId, String name, String email, String nickname, String systemRole) {
        super(DateProvider.now());
        this.managerUserId = managerUserId;
        this.unregisterUserId = unregisterUserId;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.systemRole = systemRole;
    }

    public String managerId() {
        return managerUserId;
    }

    public String unregisterUserId() {
        return unregisterUserId;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public String nickname() {
        return nickname;
    }

    public String systemRole() {
        return systemRole;
    }

}
