package ntut.csie.islab.team.entity;

import ntut.csie.islab.team.entity.event.TeamCreatedDomainEvent;
import ntut.csie.sslab.ddd.model.AggregateRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Team extends AggregateRoot<UUID> {
    List<Member> memberList;
    String boardId;
    String teamName;

    public Team(String teamName) {
        super(UUID.randomUUID());
        this.teamName = teamName;
        memberList = new ArrayList<>();
        addDomainEvent(new TeamCreatedDomainEvent(this.getId().toString(), boardId));
    }

    public Team(String teamName, String teamId) {
        super(UUID.fromString(teamId));
        this.teamName = teamName;
        memberList = new ArrayList<>();
        addDomainEvent(new TeamCreatedDomainEvent(this.getId().toString(), boardId));
    }

    public List<Member> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<Member> memberList) {
        this.memberList = memberList;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void addMember(String admin, String role) {
        this.memberList.add(new Member(admin, role));
    }
}
