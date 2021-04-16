package ntut.csie.sslab.miro.usecase.board.create;

import ntut.csie.sslab.ddd.usecase.Input;

 public interface CreateBoardInput extends Input {
	 String getName();
	
	 void setName(String name);
	
	 String getUserId();

	 void setUserId(String userId);

	 String getTeamId();

	 void setTeamId(String teamId);
}
