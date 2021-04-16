package ntut.csie.sslab.account.users.command.entity.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

import java.util.Date;

public class UserRegisteredFail extends DomainEvent {

    private final String username;
    private final String password;
    private final String email;
    private final String nickname;
    private final String systemRole;

    public UserRegisteredFail(String username, String password, String email, String nickname, String systemRole) {
        super(DateProvider.now());
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.systemRole = systemRole;
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

    public String nickname() {
        return nickname;
    }

    public String systemRole() {
        return systemRole;
    }
}
