package ntut.csie.islab.team.entity;

public class Member {
    String userName;
    String role;

    public Member(String userName, String role) {
        this.userName = userName;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
