package ntut.csie.islab.team.usecase.getboard;

import ntut.csie.sslab.ddd.usecase.Input;

public interface GetBoardListByUserUseCaseInput extends Input {
    String getUserName();

    void setUserName(String userName);
}
