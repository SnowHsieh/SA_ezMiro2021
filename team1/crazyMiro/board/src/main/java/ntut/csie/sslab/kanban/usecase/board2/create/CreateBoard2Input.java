package ntut.csie.sslab.kanban.usecase.board2.create;

import ntut.csie.sslab.ddd.usecase.Input;

 public interface CreateBoard2Input extends Input {
	 String getName();
	
	 void setName(String name);
	
	 String getUserId();

	 void setUserId(String userId);

	 String getTeamId();

	 void setTeamId(String teamId);
}
