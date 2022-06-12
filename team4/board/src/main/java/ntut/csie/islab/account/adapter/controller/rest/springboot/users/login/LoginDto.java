package ntut.csie.islab.account.adapter.controller.rest.springboot.users.login;

public class LoginDto {
    String loginMsg="";
    String boardListJson="[]";

    public String getLoginMsg() {
        return loginMsg;
    }

    public void setLoginMsg(String loginMsg) {
        this.loginMsg = loginMsg;
    }

    public String getBoardListJson() {
        return boardListJson;
    }

    public void setBoardListJson(String boardListJson) {
        this.boardListJson = boardListJson;
    }
}
