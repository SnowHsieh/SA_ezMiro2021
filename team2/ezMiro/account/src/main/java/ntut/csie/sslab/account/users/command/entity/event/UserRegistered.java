package ntut.csie.sslab.account.users.command.entity.event;

import ntut.csie.sslab.ddd.model.DateProvider;
import ntut.csie.sslab.ddd.model.DomainEvent;

import java.util.Date;

public class UserRegistered extends DomainEvent {

    private final String userId;
    private final String username;
    private final String password;
    private final String email;
    private final String nickname;
    private final String role;

    public UserRegistered(String userId, String username, String password, String email, String nickname, String role) {
        super(DateProvider.now());
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
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

    public String nickname() {
        return nickname;
    }

    public String role() {
        return role;
    }
}
