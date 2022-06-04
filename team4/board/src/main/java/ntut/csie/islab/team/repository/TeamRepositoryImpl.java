package ntut.csie.islab.team.repository;

import ntut.csie.islab.account.users.entity.User;
import ntut.csie.islab.account.users.usecase.UserRepository;
import ntut.csie.islab.team.TeamRepository;
import ntut.csie.islab.team.entity.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamRepositoryImpl implements TeamRepository {
    private List<Team> teams;

//    private UserRepositoryPeer peer;

//    public UserRepositoryImpl(UserRepositoryPeer peer) {
//        this.peer = peer;
//    }

    public TeamRepositoryImpl() {
        teams = new ArrayList<>();
    }

    @Override
    public List<Team> findAll() {
        return teams;
    }

    @Override
    public Optional<Team> findById(String teamId) {
        return teams.stream().filter(s -> s.getId().toString().equals(teamId)).findFirst();
    }

    @Override
    public void save(Team team) {

        Optional<Team> optionalTeam = teams.stream().filter(s -> s.getId().toString().equals(team.getId().toString())).findFirst();
        if (optionalTeam.isPresent()) {
            teams.remove(optionalTeam.get());
        }
        teams.add(team);
    }

    @Override
    public void deleteById(String id) {
        Optional<Team> optionalTeam = teams.stream().filter(s -> s.getId().toString().equals(id)).findFirst();
        if (optionalTeam.isPresent()) {
            teams.remove(optionalTeam.get());
        }
    }


}
