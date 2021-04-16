package ntut.csie.sslab.account.users.command.entity;


import ntut.csie.sslab.account.users.command.entity.event.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    String id;
    String username;
    String email;
    String password;
    String nickname;
    Role role;
    User user;

    @BeforeEach
    public void setUp() {
        id = "id";
        username = "username";
        email = "email";
        password = "password";
        nickname = "nickname";
        role = Role.Manager;
    }

    @Test
    public void should_publish_user_registered_domain_event_when_register_user() {
        user = new User(id, username, email, password, nickname, role);

        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(nickname, user.getNickname());
        assertEquals(role, user.getRole());

        assertEquals(1, user.getDomainEvents().size());
        assertEquals(UserRegistered.class, user.getDomainEvents().get(0).getClass());

        UserRegistered userRegistered = (UserRegistered) user.getDomainEvents().get(0);
        assertEquals(id, userRegistered.userId());
        assertEquals(username, userRegistered.username());
        assertEquals(email, userRegistered.email());
        assertEquals(password, userRegistered.password());
        assertEquals(nickname, userRegistered.nickname());
        assertEquals(role.toString(), userRegistered.role());
        user.clearDomainEvents();
    }

    @Test
    public void should_publish_user_logged_in_domain_event_when_user_login_success() {
        should_publish_user_registered_domain_event_when_register_user();

        user.loginSuccess();

        assertEquals(1, user.getDomainEvents().size());
        assertEquals(UserLoggedIn.class, user.getDomainEvents().get(0).getClass());

        UserLoggedIn loggedIn = (UserLoggedIn) user.getDomainEvents().get(0);
        assertEquals(id, loggedIn.userId());
        assertEquals(username, loggedIn.username());
    }

    @Test
    public void should_publish_user_logged_in_fail_domain_event_when_user_login_fail() {
        should_publish_user_registered_domain_event_when_register_user();

        user.loginFail();

        assertEquals(1, user.getDomainEvents().size());
        assertEquals(UserLoggedInFail.class, user.getDomainEvents().get(0).getClass());

        UserLoggedInFail loggedInFail = (UserLoggedInFail) user.getDomainEvents().get(0);
        assertEquals(id, loggedInFail.userId());
        assertEquals(username, loggedInFail.username());
    }

    @Test
    public void should_publish_password_changed_domain_event_when_user_change_password() {
        String newpassword = "newpassword";
        should_publish_user_registered_domain_event_when_register_user();

        user.changePassword(newpassword);

        assertEquals(1, user.getDomainEvents().size());
        assertEquals(PasswordChanged.class, user.getDomainEvents().get(0).getClass());

        PasswordChanged passwordChanged = (PasswordChanged) user.getDomainEvents().get(0);
        assertEquals(id, passwordChanged.userId());
        assertEquals(username, passwordChanged.username());
        assertEquals(password, passwordChanged.oldPassword());
        assertEquals(newpassword, passwordChanged.newPassword());
    }

    @Test
    public void should_publish_email_changed_domain_event_when_user_change_email() {
        String newEmail = "newEmail";
        should_publish_user_registered_domain_event_when_register_user();

        user.changeEmail(newEmail);

        assertEquals(1, user.getDomainEvents().size());
        assertEquals(EmailChanged.class, user.getDomainEvents().get(0).getClass());

        EmailChanged emailChanged = (EmailChanged) user.getDomainEvents().get(0);
        assertEquals(id, emailChanged.userId());
        assertEquals(username, emailChanged.username());
        assertEquals(email, emailChanged.oldEmail());
        assertEquals(newEmail, emailChanged.newEmail());
    }

    @Test
    public void should_publish_nickname_changed_domain_event_when_user_change_nickname() {
        String newNickname = "newNickname";
        should_publish_user_registered_domain_event_when_register_user();

        user.changeNickname(newNickname);

        assertEquals(1, user.getDomainEvents().size());
        assertEquals(NicknameChanged.class, user.getDomainEvents().get(0).getClass());

        NicknameChanged nicknameChanged = (NicknameChanged) user.getDomainEvents().get(0);
        assertEquals(id, nicknameChanged.userId());
        assertEquals(username, nicknameChanged.username());
        assertEquals(nickname, nicknameChanged.oldNickname());
        assertEquals(newNickname, nicknameChanged.newNickname());
    }


    @Test
    public void should_publish_unregister_domain_event_when_manager_unregister() {
        should_publish_user_registered_domain_event_when_register_user();
        String managerId = "managerId";

        user.deactivate("managerId");

        assertEquals(1, user.getDomainEvents().size());
        assertEquals(Unregistered.class, user.getDomainEvents().get(0).getClass());

        Unregistered unregistered = (Unregistered) user.getDomainEvents().get(0);
        assertEquals(id, unregistered.userId());
        assertEquals(managerId, unregistered.managerUserId());
    }


}
