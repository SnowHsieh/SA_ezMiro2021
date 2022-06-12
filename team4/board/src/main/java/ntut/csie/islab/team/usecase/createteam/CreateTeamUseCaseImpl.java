package ntut.csie.islab.team.usecase.createteam;

import ntut.csie.islab.team.TeamRepository;
import ntut.csie.islab.team.entity.Team;
import ntut.csie.sslab.ddd.model.DomainEventBus;
import ntut.csie.sslab.ddd.usecase.cqrs.CqrsCommandOutput;
import ntut.csie.sslab.ddd.usecase.cqrs.ExitCode;

import java.util.UUID;

public class CreateTeamUseCaseImpl implements CreateTeamUseCase {
    private TeamRepository teamRepository;
    private DomainEventBus domainEventBus;

    public CreateTeamUseCaseImpl(TeamRepository teamRepository, DomainEventBus domainEventBus) {
        this.teamRepository = teamRepository;
        this.domainEventBus = domainEventBus;
    }

    @Override
    public void execute(CreateTeamUseCaseInput input, CqrsCommandOutput output) {
        Team team = new Team(input.getTeamName(), UUID.randomUUID().toString());
        team.addMember(input.getAdmin(), "Admin");
        teamRepository.save(team);
        domainEventBus.postAll(team);

        output.setId(team.getId().toString());
        output.setExitCode(ExitCode.SUCCESS);
        output.setMessage("create team success");
    }

    @Override
    public CreateTeamUseCaseInput newInput() {
        return new CreateTeamUseCaseInputImpl();
    }

    private static class CreateTeamUseCaseInputImpl implements CreateTeamUseCaseInput {

        private String username;
        private String teamName;


        @Override
        public String getAdmin() {
            return username;
        }

        @Override
        public void setAdmin(String username) {
            this.username = username;
        }

        @Override
        public String getTeamName() {
            return teamName;
        }

        @Override
        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

    }
}