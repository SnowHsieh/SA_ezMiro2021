package ntut.csie.islab.team.usecase.getboard;

import com.google.gson.Gson;
import ntut.csie.islab.miro.usecase.board.BoardRepository;
import ntut.csie.islab.team.TeamRepository;
import ntut.csie.islab.team.entity.Team;
import ntut.csie.islab.team.usecase.createteam.CreateTeamUseCaseImpl;
import ntut.csie.islab.team.usecase.createteam.CreateTeamUseCaseInput;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.Output;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GetBoardListByUserUseCaseImpl implements GetBoardListByUserUseCase {
    private TeamRepository teamRepository;
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public GetBoardListByUserUseCaseImpl(TeamRepository teamRepository, BoardRepository boardRepository, DomainEventBus domainEventBus) {
        this.teamRepository = teamRepository;
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public GetBoardListByUserUseCaseInput newInput() {
        return new GetBoardListByUserUseCaseImpl.GetBoardListByUserUseCaseInputImpl();
    }

    @Override
    public void execute(GetBoardListByUserUseCaseInput input, CqrsCommandOutput output) {
        String userName = input.getUserName();
        List<Team> teams = teamRepository.findAll();
        List<String> boardIdList = new ArrayList<>();

        for(Team team: teams){
            if(team.getMemberList().stream().filter(s->s.getUserName().equals(userName)).findFirst().isPresent()){
                boardIdList.add(team.getBoardId());
            }
        }

        String json = new Gson().toJson(boardIdList);
        output.setId(userName);
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage(json);
    }

    private static class GetBoardListByUserUseCaseInputImpl implements GetBoardListByUserUseCaseInput {

        private String username;

        @Override
        public String getUserName() {
            return username;
        }

        @Override
        public void setUserName(String username) {
            this.username = username;
        }
    }
}
